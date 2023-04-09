package com.codelab.springbatchupdate.customerorder.csvreader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

public class CustomerOrderCsvReader {
    
    public List<CustomerOrderRecord> read(InputStream inputStream) throws IllegalStateException, UnsupportedEncodingException {
        return new CsvToBeanBuilder<CustomerOrderRecord>(new CSVReader(new InputStreamReader(inputStream, "UTF-8")))
        .withType(CustomerOrderRecord.class)
        .build().parse();
    }
}
