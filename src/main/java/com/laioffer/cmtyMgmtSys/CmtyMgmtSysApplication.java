package com.laioffer.cmtyMgmtSys;

import com.laioffer.cmtyMgmtSys.audition.SpringSecurityAuditorAware;
import com.laioffer.cmtyMgmtSys.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableWebSecurity
public class CmtyMgmtSysApplication {
	@Bean
	public AuditorAware<Long> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(CmtyMgmtSysApplication.class, args);
	}

}
