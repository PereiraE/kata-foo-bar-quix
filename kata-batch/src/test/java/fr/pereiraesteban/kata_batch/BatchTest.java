package fr.pereiraesteban.kata_batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { BatchConfiguration.class })
class BatchTest {


  @Value("${kata.input}")
  private String inputFile;

  @Value("${kata.output}")
  private String outputFile;

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  void testBatch(@Autowired Job job) throws Exception {
    //Given
    jobLauncherTestUtils.setJob(job);
    Files.writeString(Path.of(inputFile), "1\n3\n", StandardOpenOption.CREATE);

    var expectedResults = List.of(
        "1   1",
        "3   FOOFOO"
    );

    //When
    var jobExecution = jobLauncherTestUtils.launchJob();

    //Then
    assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    var result  = Files.readAllLines(Path.of(outputFile));
    assertEquals(expectedResults, result);

    //Clean up
    Files.deleteIfExists(Path.of(inputFile));
    Files.deleteIfExists(Path.of(outputFile));
  }
}
