package technology.scholz.bingo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import technology.scholz.bingo.config.DefaultSpielConfigurations;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.repository.SpielRespository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BingoApplication.class)
@WebAppConfiguration
class SpielRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    SpielRespository repository;

    @Before
    public void setup() {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Spiel.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);
        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @Test
    public void insertSpiel() {
        final String spielName = "testspiel";
        Spiel spiel = new Spiel(new DefaultSpielConfigurations().defaultTwoDimensionalGame());
        repository.save(spiel);
        Optional<Spiel> result = repository.findById(spiel.getId());

        assertTrue(result.isPresent());
        assertEquals(spielName, result.get().getName());

    }

}