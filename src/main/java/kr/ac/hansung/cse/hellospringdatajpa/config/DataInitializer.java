package kr.ac.hansung.cse.hellospringdatajpa.config;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Role;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repository.RoleRepository;
import kr.ac.hansung.cse.hellospringdatajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data initialization...");

        // Create roles if they don't exist
        if (!roleRepository.existsByName("ADMIN")) {
            logger.info("Creating ADMIN role...");
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
            logger.info("ADMIN role created successfully");
        }

        if (!roleRepository.existsByName("USER")) {
            logger.info("Creating USER role...");
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
            logger.info("USER role created successfully");
        }

        // Create admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@example.com")) {
            logger.info("Creating admin user...");
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // 기본 비밀번호: admin123
            admin.setName("관리자");

            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN 역할을 찾을 수 없습니다."));
            admin.addRole(adminRole);

            userRepository.save(admin);
            logger.info("Admin user created successfully");
        } else {
            logger.info("Admin user already exists");
        }

        logger.info("Data initialization completed");
    }
} 