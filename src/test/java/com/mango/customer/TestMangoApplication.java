package com.mango.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestMangoApplication {

  public static void main(String[] args) {
    SpringApplication.from(MangoApplication::main).with(TestMangoApplication.class).run(args);
  }

}
