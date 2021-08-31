package org.veritasopher.mypubservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.veritasopher.mypubservice.config.GlobalProperties;
import org.veritasopher.mypubservice.service.WorkspaceService;

@SpringBootApplication
@EnableConfigurationProperties(GlobalProperties.class)
public class MyPubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPubServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(WorkspaceService workspaceService) {
        return (args) -> {
//            workspaceService.deleteAll();
            workspaceService.init();
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
