package com.laioffer.cmtyMgmtSys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CmtyMgmtSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmtyMgmtSysApplication.class, args);
	}

}
