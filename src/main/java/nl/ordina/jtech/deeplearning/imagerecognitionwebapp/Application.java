package nl.ordina.jtech.deeplearning.imagerecognitionwebapp;

import nl.ordina.jtech.deeplearning.imagerecognitionwebapp.storage.StorageProperties;
import nl.ordina.jtech.deeplearning.imagerecognitionwebapp.storage.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, ImageClassifier imageClassifier) {
        return args -> {
            storageService.deleteAll();
            storageService.init();
        };

    }
}
