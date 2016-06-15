package org.dyzeng.imagelib.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.dyzeng.imagelib.data"})
@EnableJpaRepositories(basePackages = {"org.dyzeng.imagelib.data"})
@EnableTransactionManagement
public class RepositoryConfig {
}
