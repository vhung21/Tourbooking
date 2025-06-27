package com.hungnv.tourbooking.web.rest;

import static com.hungnv.tourbooking.security.SecurityUtils.AUTHORITIES_CLAIM;
import static com.hungnv.tourbooking.security.SecurityUtils.JWT_ALGORITHM;
import static com.hungnv.tourbooking.security.SecurityUtils.USER_ID_CLAIM;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.hungnv.tourbooking.domain.Authority;
import com.hungnv.tourbooking.domain.User;
import com.hungnv.tourbooking.dto.FacebookTokenDTO;
import com.hungnv.tourbooking.dto.GoogleTokenDTO;
import com.hungnv.tourbooking.repository.AuthorityRepository;
import com.hungnv.tourbooking.repository.UserRepository;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hungnv.tourbooking.security.AuthoritiesConstants;
import com.hungnv.tourbooking.security.DomainUserDetailsService.UserWithId;
import com.hungnv.tourbooking.web.rest.vm.LoginVM;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthenticateController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateController.class);

    private final JwtEncoder jwtEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Value("${google.clientId}")
    private String googleClientId;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthenticateController(JwtEncoder jwtEncoder, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/authenticate-google")
    public ResponseEntity<JWTToken> googleLogin(@RequestBody GoogleTokenDTO googleTokenDTO) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance()
            ).setAudience(Collections.singletonList(googleClientId)).build();

            GoogleIdToken idToken = verifier.verify(googleTokenDTO.getToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            User user = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setLogin(email);
                newUser.setEmail(email);
                newUser.setFirstName(name);
                newUser.setActivated(true);
                newUser.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                newUser.setLangKey("vi");
                newUser.setImageUrl((String) payload.get("picture"));

                Authority userAuthority = authorityRepository
                    .findById(AuthoritiesConstants.USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
                newUser.setAuthorities(Set.of(userAuthority));

                return userRepository.save(newUser);
            });

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserWithId(user.getLogin(), "N/A", user.getAuthorities(), user.getId()),
                null,
                user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = createToken(authentication, false);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error("Xác thực Google thất bại", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/authenticate-facebook")
    public ResponseEntity<JWTToken> facebookLogin(@RequestBody FacebookTokenDTO facebookTokenDTO) {
        try {
            String accessToken = facebookTokenDTO.getAccess_token();

            String url = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=" + accessToken;
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> userInfo = restTemplate.getForObject(url, Map.class);

            if (userInfo == null || userInfo.get("email") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");
            Map pictureData = (Map) ((Map) userInfo.get("picture")).get("data");
            String imageUrl = (String) pictureData.get("url");

            User user = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setLogin(email);
                newUser.setEmail(email);
                newUser.setFirstName(name);
                newUser.setActivated(true);
                newUser.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                newUser.setLangKey("vi");
                newUser.setImageUrl(imageUrl);

                Authority userAuthority = authorityRepository
                    .findById(AuthoritiesConstants.USER)
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
                newUser.setAuthorities(Set.of(userAuthority));

                return userRepository.save(newUser);
            });

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserWithId(user.getLogin(), "N/A", user.getAuthorities(), user.getId()),
                null,
                user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = createToken(authentication, false);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(jwt);
            return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);

        } catch (Exception e) {
            LOG.error("Xác thực Facebook thất bại", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * {@code GET /authenticate} : check if the user is authenticated.
     *
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)},
     * or with status {@code 401 (Unauthorized)} if not authenticated.
     */
    @GetMapping("/authenticate")
    public ResponseEntity<Void> isAuthenticated(Principal principal) {
        LOG.debug("REST request to check if the current user is authenticated");
        return ResponseEntity.status(principal == null ? HttpStatus.UNAUTHORIZED : HttpStatus.NO_CONTENT).build();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        // @formatter:off
        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(authentication.getName())
            .claim(AUTHORITIES_CLAIM, authorities);
        if (authentication.getPrincipal() instanceof UserWithId user) {
            builder.claim(USER_ID_CLAIM, user.getId());
        }

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, builder.build())).getTokenValue();
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
