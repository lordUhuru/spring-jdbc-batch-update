package com.codelab.springbatchupdate.customerorder.csvreader;

import java.util.List;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CustomerOrderRecord {
    @CsvBindByName(column = "order_Id")
    private String orderId;

    @CsvBindByName(column = "customer_id")
    private String customerId;

    @CsvBindByName(column = "ordered_at")
    private String orderedAt;

    @CsvBindByName(column = "processed_at")
    private String processedAt;

    @CsvBindByName(column = "order_status")
    private String orderStatus;

    @CsvBindAndSplitByName(column = "product_ids", splitOn = ",", elementType = String.class)
    private List<String> productIds; 
}
