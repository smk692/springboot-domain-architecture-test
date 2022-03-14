package com.approve.son.documentapproval;

import com.approve.son.documentapproval.global.system.context.SonContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class DocumentApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentApprovalApplication.class, args);
	}

	@Bean
	public AuditorAware<Long> auditorAware() {
		return () -> Optional.of(SonContextHolder.getContext().getCurrentMemberId());
	}

}
