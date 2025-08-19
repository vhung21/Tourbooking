package com.hungnv.tourbooking.web.rest;

import com.hungnv.tourbooking.domain.Customer;
import com.hungnv.tourbooking.repository.CustomerRepository;
import com.hungnv.tourbooking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerResource {
    @Autowired
    CustomerService customerService;

    @GetMapping("/me")
    public ResponseEntity<Customer> getMyProfile() {
        return customerService.getCurrentCustomerProfile()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer saved = customerService.saveCustomerProfile(customer);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        customer.setId(id);
        Customer updated = customerService.saveCustomerProfile(customer);
        return ResponseEntity.ok(updated);
    }
}
