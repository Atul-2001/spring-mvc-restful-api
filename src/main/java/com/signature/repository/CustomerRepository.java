package com.signature.repository;

import com.signature.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Transactional
  @Query(value = "UPDATE Customer c SET c.firstName = :#{#customer.firstName}, c.lastName = :#{#customer.lastName} WHERE c.id = :#{#customer.id}")
  Integer update(@Param("customer") Customer customer);
}