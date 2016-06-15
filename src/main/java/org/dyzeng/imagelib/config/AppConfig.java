package org.dyzeng.imagelib.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.dyzeng.imagelib.data", "org.dyzeng.imagelib.service"})
public class AppConfig {
}
