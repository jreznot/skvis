package ru.ssau.templet.liza.analyze.load

import org.junit.Test
import ru.ssau.templet.liza.analyze._

/**
 * @author Yuriy Artamonov 
 */
class LoadParserTest {

  @Test
  def testSampleRead() {
    val descriptor = LoadParser.parse(
      experimentalDataFileNameFormat.parse("2013_11_10__01_40_01").getTime,
      getClass.getResourceAsStream("/ru/ssau/templet/liza/analyze/load/load-analyze-parser-test"))

    assert(descriptor != null)
  }
}