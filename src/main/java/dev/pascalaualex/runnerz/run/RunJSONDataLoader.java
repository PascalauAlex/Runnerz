package dev.pascalaualex.runnerz.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJSONDataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(RunJSONDataLoader.class);
    private final JdbcClientRunRepository runRepository;
    private final ObjectMapper objectMapper;


    // DI run repository and objectMapper
    public RunJSONDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper){
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception{
        if(runRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")){
                Runs allRuns = objectMapper.readValue(inputStream,Runs.class);
                logger.info("Reading {} runs from JSON data and saving to in-memory collection H2.", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            }catch (IOException e){
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }else {
            logger.info("Not loading Runs from JSON data because collection contains data.");
        }
    }
}
