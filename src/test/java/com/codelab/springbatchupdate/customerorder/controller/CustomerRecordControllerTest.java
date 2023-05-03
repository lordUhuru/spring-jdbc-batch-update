package com.codelab.springbatchupdate.customerorder.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.codelab.springbatchupdate.customerorder.entity.CustomerOrder;
import com.codelab.springbatchupdate.customerorder.entity.OrderStatusEnum;
import com.codelab.springbatchupdate.customerorder.repository.CustomerOrderRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;
import java.nio.file.Files;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class CustomerRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2-alpine"))
    .withUrlParam("currentSchema", "dbuser");

    @DynamicPropertySource
    private static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testUpload() throws Exception {

        File file = ResourceUtils.getFile("src/test/resources/customer_records.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "customer_records.csv", MediaType.TEXT_PLAIN_VALUE, Files.readAllBytes(file.toPath()));
    
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/customers/orders/batch")
        .file(multipartFile)).andExpect(status().isOk());
    }

    @Test
    void testUploadUpdatesExistingRecord() throws Exception {
        var order = createCustomerOrder();
        File file = ResourceUtils.getFile("src/test/resources/customer_records.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "customer_records.csv", MediaType.TEXT_PLAIN_VALUE, Files.readAllBytes(file.toPath()));
    
        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/customers/orders/batch")
        .file(multipartFile)).andExpect(status().isOk());
        var updatedCustomerOrder = customerOrderRepository.findById(order.getId()).orElseThrow();
        assertEquals(OrderStatusEnum.PROCESSED, updatedCustomerOrder.getOrderStatus());
        assertTrue(
            Arrays.stream(updatedCustomerOrder.getProductIds())
            .allMatch(it -> List.of("a5ef50c3-227b-4de9-9631-8be0814aff60", "67ffa86a-4e87-4576-9510-3cf59af196a1")
            .contains(it))
            );
    }

    private CustomerOrder createCustomerOrder() {
        var now = OffsetDateTime.now(Clock.fixed(Instant.parse("2023-04-01T10:00:30Z"), ZoneId.of("UTC")));
        var customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(UUID.fromString("67778a55-238d-4a22-b9f8-026a2b954c70"));
        customerOrder.setId(UUID.fromString("2a1a1e28-a063-41ba-a539-412947c86626"));
        customerOrder.setOrderId(UUID.fromString("2a1a1e28-a063-41ba-a539-412947c86626"));
        customerOrder.setOrderStatus(OrderStatusEnum.PENDING);
        customerOrder.setOrderedAt(now);
        customerOrder.setProcessedAt(now.plusDays(2));
        customerOrder.setProductIds(new String[] {"e0f19516-9926-4b8c-81d0-11a5b5009d07", "ebea5c99-4f5c-437a-8b7e-cee28389a2d9"});
        return customerOrderRepository.save(customerOrder);
    }
}
