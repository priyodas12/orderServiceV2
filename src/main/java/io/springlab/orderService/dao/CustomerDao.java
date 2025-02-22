package io.springlab.orderService.dao;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import io.springlab.orderService.model.Customer;

@Repository
public interface CustomerDao extends MongoRepository<Customer, BigInteger> {

}
