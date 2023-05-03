package com.codelab.springbatchupdate.customerorder.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer_order")
@EqualsAndHashCode(of = "id")
public class CustomerOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(nullable = false, name = "order_id")
    private UUID orderId;

    @Column(nullable = false, name = "customer_id")
    private UUID customerId;

    @Column(nullable = false, name = "ordered_at")
    private OffsetDateTime orderedAt;

    @Column(nullable = false, name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Column(nullable = false, name = "processed_at")
    private OffsetDateTime processedAt;

    @Type(StringArrayType.class)
    @Column(name = "product_ids", columnDefinition = "text[]")
    private String[] productIds;

}
