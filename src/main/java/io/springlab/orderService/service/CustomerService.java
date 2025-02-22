package io.springlab.orderService.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import org.springframework.stereotype.Service;

import io.springlab.orderService.dao.CustomerDao;
import io.springlab.orderService.model.Customer;

@Service
public class CustomerService {

  private final CustomerDao customerDao;

  public CustomerService(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Customer createCustomer(Customer customer) {
    if (Optional.ofNullable(customer.getCustomerId()).isEmpty()) {
      customer.setCustomerId(
          BigInteger.valueOf(RandomGenerator.getDefault().nextLong(1, 1_000_000)));
    }
    return customerDao.save(customer);
  }

  public Customer getCustomerById(BigInteger customerId) {
    return customerDao.findById(customerId).orElse(null);
  }

  public List<Customer> getCustomers() {
    return customerDao.findAll().stream().toList();
  }
}
