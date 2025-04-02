package fr.pereiraesteban.kata_batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {
  @Value("${kata.input}")
  private String inputFile;

  @Value("${kata.output}")
  private String outputFile;

  @Bean
  public FlatFileItemReader<Integer> reader() {
    return new FlatFileItemReaderBuilder<Integer>()
        .name("reader")
        .lineMapper(new KataLineMapper())
        .resource(new FileSystemResource(inputFile))
        .saveState(false)
        .build();
  }

  @Bean
  public FlatFileItemWriter<String> writer() {
    return new FlatFileItemWriterBuilder<String>()
        .name("writer")
        .resource(new FileSystemResource(outputFile))
        .lineAggregator(new PassThroughLineAggregator<>())
        .lineSeparator("\n")
        .shouldDeleteIfExists(true)
        .saveState(false)
        .build();
  }

  @Bean
  public Job performKata(JobRepository jobRepository, Step step) {
    return new JobBuilder("performKata", jobRepository)
        .start(step)
        .build();
  }

  @Bean
  public Step step(
      JobRepository jobRepository,
      DataSourceTransactionManager transactionManager,
      FlatFileItemReader<Integer> reader,
      FlatFileItemWriter<String> writer
  ) {
    return new StepBuilder("transform", jobRepository)
        .<Integer, String>chunk(1, transactionManager)
        .reader(reader)
        .writer(writer)
        .processor(new KataProcessor())
        .faultTolerant()
        .skip(FlatFileParseException.class)
        .build();
  }
}
