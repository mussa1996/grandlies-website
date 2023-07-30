//package com.grandlies.reviewservices.data;
//
//import com.grandlies.reviewservices.entity.User;
//import com.grandlies.reviewservices.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class DataInitializer {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void initData() {
//        logger.info("Initializing data...");
//
//            // Create the admin user with default credentials
//            User adminUser = new User();
//        adminUser.setEmail("admin@gmail.com");
//            adminUser.setName("admin");
//            adminUser.setPassword(passwordEncoder.encode("admin")); // Encode the password
//            adminUser.setRole("Admin");
//
//            userRepository.save(adminUser);
//
//        logger.info("Data initialization completed successfully.");
//    }
//}
//
//
//
