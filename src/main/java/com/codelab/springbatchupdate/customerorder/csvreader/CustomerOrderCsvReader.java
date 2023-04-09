package com.codelab.springbatchupdate.customerorder.csvreader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CustomerOrderCsvReader {
    
    public List<CustomerOrderRecord> read(InputStream inputStream) throws IllegalStateException, UnsupportedEncodingException {
        return new CsvToBeanBuilder<CustomerOrderRecord>(new CSVReader(new InputStreamReader(inputStream, "UTF-8")))
        .withType(CustomerOrderRecord.class)
        .withQuoteChar('"')
        .withSeparator(',')
        .build().parse();
    }
}
