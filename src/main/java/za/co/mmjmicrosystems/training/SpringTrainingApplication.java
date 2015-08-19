package za.co.mmjmicrosystems.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// The other annotations which could be here are @Configuration, @ComponentScan and @EnableAutoConfiguration

@SpringBootApplication
@EnableTransactionManagement
public class SpringTrainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTrainingApplication.class, args);
    }
    
}
