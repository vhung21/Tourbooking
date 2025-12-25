package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.config.VnPayConfig;
import com.hungnv.tourbooking.config.VnPayUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class VnPayService {
    private final VnPayConfig config;

    public VnPayService(VnPayConfig config) {
        this.config = config;
    }

    public String createPaymentUrl(Long orderId, BigDecimal amount, String ipAddress) {
        // VNPay yêu cầu vnp_Amount = số tiền * 100
        long amountVnd = amount.longValue() * 100;

        // Lấy thời gian hiện tại ở VN (UTC+7)
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String vnp_CreateDate = now.format(formatter);
        String vnp_ExpireDate = now.plusMinutes(15).format(formatter); // +15 phút

        // Mã tham chiếu giao dịch
        String vnp_TxnRef = orderId + "-" + vnp_CreateDate;

        // Log thử cho chắc
        System.out.println("VNPay now         = " + now);
        System.out.println("VNPay createDate  = " + vnp_CreateDate);
        System.out.println("VNPay expireDate  = " + vnp_ExpireDate);

        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", config.getVersion());
        vnpParams.put("vnp_Command", config.getCommand());
        vnpParams.put("vnp_TmnCode", config.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(amountVnd));
        vnpParams.put("vnp_CurrCode", config.getCurrCode());
        vnpParams.put("vnp_TxnRef", vnp_TxnRef);
        vnpParams.put("vnp_OrderInfo", "Thanh toan tour #" + orderId);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", config.getLocale());
        vnpParams.put("vnp_ReturnUrl", config.getReturnUrl());
        vnpParams.put("vnp_IpAddr", ipAddress);
        vnpParams.put("vnp_CreateDate", vnp_CreateDate);
        vnpParams.put("vnp_ExpireDate", vnp_ExpireDate);

        String query = VnPayUtil.buildQueryAndHash(vnpParams, config.getSecretKey());
        return config.getPayUrl() + "?" + query;
    }
}
