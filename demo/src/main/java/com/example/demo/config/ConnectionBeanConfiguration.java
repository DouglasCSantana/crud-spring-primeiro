//package com.example.demo.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//@RequiredArgsConstructor
//public class ConnectionBeanConfiguration {
//    private final ConnectionConfigurationProperties configurationProperties;
////    @Value("${spring.datasource.url}")
////    private String url;
////    @Value("${spring.datasource.username}")
////    private String username;
////    @Value("${spring.datasource.password}")
////    private String password;
//    @Bean
//    @Profile("mysql")
//    public Connection connectionMysql() {
//        return new Connection(configurationProperties.url(),
//                configurationProperties.username(),
//                configurationProperties.password());
//    }
//    @Bean(name = "connectionMongo")
////    @Profile("mongo")
////   @Primary
//    public Connection connectionMongo() {
//        return new Connection(configurationProperties.url(),
//                configurationProperties.username(),
//                configurationProperties.password());
//    }
//}
