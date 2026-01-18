package com.rashmy.library.config;

import com.rashmy.library.entity.Member;
import com.rashmy.library.entity.Role;
import com.rashmy.library.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminSetup {

    @Bean
    public CommandLineRunner setupAdmin(MemberRepository memberRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            if (memberRepository.findByEmail("admin@tplan.com").isEmpty()) {
                Member admin = new Member();
                admin.setName("Admin");
                admin.setEmail("admin@tplan.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ROLE_ADMIN);

                memberRepository.save(admin);
            }
        };
    }
}
