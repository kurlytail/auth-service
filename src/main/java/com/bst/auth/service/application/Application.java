package com.bst.auth.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;

import com.bst.auth.service.configuration.WebSecurityConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@ComponentScan(basePackageClasses = { WebSecurityConfiguration.class })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
