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
        Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> {
                logger.info("Creating ADMIN role...");
                Role role = new Role();
                role.setName("ADMIN");
                Role savedRole = roleRepository.save(role);
                logger.info("ADMIN role created with ID: {}", savedRole.getId());
                return savedRole;
            });

        Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> {
                logger.info("Creating USER role...");
                Role role = new Role();
                role.setName("USER");
                Role savedRole = roleRepository.save(role);
                logger.info("USER role created with ID: {}", savedRole.getId());
                return savedRole;
            });

        // Create admin user if it doesn't exist
        if (!userRepository.existsByEmail("admin@example.com")) {
            logger.info("Creating admin user...");
            User admin = new User();
            admin.setEmail("admin@example.com");
            String encodedPassword = passwordEncoder.encode("admin123");
            logger.info("Encoded password for admin: {}", encodedPassword);
            admin.setPassword(encodedPassword);
            admin.setName("관리자");
            admin.addRole(adminRole);
            admin.addRole(userRole);  // Admin also has USER role
            User savedAdmin = userRepository.save(admin);
            logger.info("Admin user created successfully with ID: {}", savedAdmin.getId());
            logger.info("Admin user roles: {}", savedAdmin.getRoles());
        } else {
            logger.info("Admin user already exists, updating password...");
            User admin = userRepository.findByEmail("admin@example.com")
                .orElseThrow(() -> new RuntimeException("Admin user not found"));
            String encodedPassword = passwordEncoder.encode("admin123");
            logger.info("New encoded password for admin: {}", encodedPassword);
            admin.setPassword(encodedPassword);
            User updatedAdmin = userRepository.save(admin);
            logger.info("Admin password updated successfully");
            logger.info("Admin user roles: {}", updatedAdmin.getRoles());
        }

        logger.info("Data initialization completed");
    }
} 