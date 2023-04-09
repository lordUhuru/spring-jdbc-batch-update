package com.codelab.springbatchupdate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = {
    "io.hypersistence.utils.spring.repository",
    "com.codelab.springbatchupdate"
})
public class JpaConfiguration {
    
}
