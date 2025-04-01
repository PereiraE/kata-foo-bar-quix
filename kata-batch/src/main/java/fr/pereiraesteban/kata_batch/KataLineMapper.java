package fr.pereiraesteban.kata_batch;

import org.springframework.batch.item.file.LineMapper;

public class KataLineMapper implements LineMapper<Integer> {
  @Override
  public Integer mapLine(String line, int lineNumber) throws Exception {
    return Integer.parseInt(line);
  }
}
