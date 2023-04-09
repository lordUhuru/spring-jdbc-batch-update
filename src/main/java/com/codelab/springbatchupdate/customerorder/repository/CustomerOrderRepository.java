package com.codelab.springbatchupdate.customerorder.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.codelab.springbatchupdate.customerorder.entity.CustomerOrder;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

@Repository
public interface CustomerOrderRepository extends BaseJpaRepository<CustomerOrder, UUID> {
    
}
