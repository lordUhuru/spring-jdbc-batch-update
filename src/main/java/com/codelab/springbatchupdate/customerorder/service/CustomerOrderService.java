package com.codelab.springbatchupdate.customerorder.service;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.codelab.springbatchupdate.customerorder.csvreader.CustomerOrderRecord;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerOrderService {

    private final JdbcTemplate jdbcTemplate;

    private static String QUERY = """
        INSERT INTO customer_order (id, order_id, customer_id, ordered_at, order_status, processed_at, product_ids) 
        VALUES (?, ?, ?, ?, ?, ?, string_to_array(?,',')) 
        ON CONFLICT (order_id, customer_id) DO UPDATE SET product_ids = EXCLUDED.product_ids, order_status = EXCLUDED.order_status
            """;

    public int[][] handleBatch(List<CustomerOrderRecord> records) {
        return jdbcTemplate.batchUpdate(QUERY, records, 128, (ps, item) -> {
            ps.setObject(1, UUID.randomUUID());
            ps.setObject(2, item.getOrderId());
            ps.setObject(3, item.getCustomerId());
            ps.setObject(4, Timestamp.valueOf(item.getOrderedAt()).toLocalDateTime()); //at UTC
            ps.setObject(5, item.getOrderStatus());
            ps.setObject(6, Timestamp.valueOf(item.getProcessedAt()).toLocalDateTime()); //at UTC
            ps.setObject(7, String.join(",", item.getProductIds()), Types.VARCHAR); //convert the array to a list, separated by commas
        });
    }
}
