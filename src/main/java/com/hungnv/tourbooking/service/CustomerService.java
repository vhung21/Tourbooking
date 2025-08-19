package com.hungnv.tourbooking.service;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.domain.User;
import com.hungnv.tourbooking.repository.CustomerRepository;
import com.hungnv.tourbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public Optional<Customer> getCurrentCustomerProfile() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userRepository.findOneByLogin(login);
        return userOpt.flatMap(customerRepository::findByUser);
    }

    public Customer saveCustomerProfile(Customer customer) {
        return customerRepository.save(customer);
    }
}
