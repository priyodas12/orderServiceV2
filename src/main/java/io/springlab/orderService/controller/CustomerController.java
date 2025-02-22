package io.springlab.orderService.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.springlab.orderService.model.Customer;
import io.springlab.orderService.service.CustomerService;

@RequestMapping("/api/v2/customers")
@RestController
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/")
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    return ResponseEntity.status(201).body(customerService.createCustomer(customer));
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") String customerId) {
    return ResponseEntity.status(200).body(customerService.getCustomerById(BigInteger.valueOf(
        Long.parseLong(customerId))));
  }

  @GetMapping("/")
  public ResponseEntity<List<Customer>> getAllCustomer() {
    return ResponseEntity.status(200).body(customerService.getCustomers());
  }

}
