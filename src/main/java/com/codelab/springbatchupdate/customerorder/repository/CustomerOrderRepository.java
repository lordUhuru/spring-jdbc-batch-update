package com.codelab.springbatchupdate.customerorder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codelab.springbatchupdate.customerorder.entity.CustomerOrder;


@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
    
}
