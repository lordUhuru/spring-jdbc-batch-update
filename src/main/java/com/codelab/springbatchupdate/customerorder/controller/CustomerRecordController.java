package com.codelab.springbatchupdate.customerorder.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codelab.springbatchupdate.customerorder.csvreader.CustomerOrderCsvReader;
import com.codelab.springbatchupdate.customerorder.service.CustomerOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/customers/orders")
@RequiredArgsConstructor
public class CustomerRecordController {

    private final CustomerOrderService customerOrderService;

    private final CustomerOrderCsvReader reader;

    @PostMapping("/batch")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    public void upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, UnsupportedEncodingException, IOException {
        var records = reader.read(file.getInputStream());
        customerOrderService.handleBatch(records);
    }
}
