Use Spring JdbcTemplate batch insert to do batch inserts into the Database.
#### How to Run
1. Start the application containers
 >docker-compose up
2. Go to Postman, Enter in the address bar: http://localhost:8080/v1/customers/orders/batch
3. Prepare a Post request, 
4. In Body of request, select form-data. add key: file, for value, select file in test/resources/customer_records.csv

Integration test is also available in CustomerRecordControllerTest.java