package ru.ssau.templet.liza.analyze.load

import org.junit.Test
import java.io.File
import ru.ssau.templet.liza.analyze._
import scala.collection.JavaConverters._

/**
 * @author Yuriy Artamonov 
 */
class LoadVisualizerTest {

  @Test
  def testWriteSampleJson() {
    val files = new File("C:\\temp\\data").listFiles()
    if (files != null) {
      val timestamp = { file : File => experimentalDataFileNameFormat.parse(file.getName).getTime }
      files.sortWith((a, b) => timestamp(a) >= timestamp(b))

      val points = new java.util.concurrent.ConcurrentLinkedQueue[ClusterDescriptor]
      files.toList.par.foreach({file =>
        points.add(LoadParser.parse(file))
      })

      val visualizer = new JsLoadVisualizer(new File("C:\\temp\\data.json"))
      visualizer.show(points.asScala.toSeq)
    }
  }
}