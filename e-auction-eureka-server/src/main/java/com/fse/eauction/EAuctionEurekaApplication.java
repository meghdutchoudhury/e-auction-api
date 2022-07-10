package com.fse.eauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EAuctionEurekaApplication {
	public static void main(String[] args) {
	    SpringApplication.run(EAuctionEurekaApplication.class, args);
	  }
}
