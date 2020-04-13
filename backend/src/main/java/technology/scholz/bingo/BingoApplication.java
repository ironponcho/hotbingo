package technology.scholz.bingo;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import technology.scholz.bingo.repository.SpielRespository;

@SpringBootApplication
@EnableDynamoDBRepositories(
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SpielRespository.class}
                )
        })
public class BingoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BingoApplication.class, args);
    }

}
