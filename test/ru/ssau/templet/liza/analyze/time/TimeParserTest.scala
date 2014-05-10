package ru.ssau.templet.liza.analyze.time

import org.junit.Test
import ru.ssau.templet.liza.analyze._

/**
 * @author Yuriy Artamonov 
 */
class TimeParserTest {

  @Test
  def testSampleRead() {
    val descriptor = QueueTimeParser.parse(
      experimentalDataFileNameFormat.parse("2013_11_10__01_40_01").getTime,
      getClass.getResourceAsStream("/ru/ssau/templet/liza/analyze/time/time-analyze-parser-test"))

    assert(descriptor != null)
  }
}