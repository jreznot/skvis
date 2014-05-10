package ru.ssau.templet.liza.analyze.time

import java.io.File
import org.junit.Test
import ru.ssau.templet.liza.analyze._
import scala.collection.JavaConverters._

/**
 * @author Yuriy Artamonov 
 */
class TimeVisualizerTest {

  @Test
  def testWriteSampleJson() {
    val files = new File("C:\\temp\\data").listFiles()
    if (files != null) {
      val timestamp = { file : File => experimentalDataFileNameFormat.parse(file.getName).getTime }
      files.sortWith((a, b) => timestamp(a) >= timestamp(b))

      val points = new java.util.concurrent.ConcurrentLinkedQueue[QueueDescriptor]
      files.toList.par.foreach({file =>
        points.add(QueueTimeParser.parse(file))
      })

      val visualizer = new JsTimeVisualizer(new File("C:\\temp\\time.json"))
      visualizer.show(points.asScala.toSeq)
    }
  }
}