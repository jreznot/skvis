package ru.ssau.templet.liza.analyze.load

import org.junit.Test
import java.io.File
import scala.collection.JavaConverters._

/**
 * @author Yuriy Artamonov 
 */
class LoadVisualizerTest {

  @Test
  def testWriteSampleJson() {
    val files = new File("C:\\temp\\data").listFiles()
    if (files != null) {
      val points = new java.util.concurrent.ConcurrentLinkedQueue[ClusterDescriptor]
      files.toList.par.foreach({ file =>
        points.add(LoadParser.parse(file))
      })

      val sortedPoints = points.asScala.toIndexedSeq.sortBy(f => f.timeStamp)
      val packedPoints = LoadArchiver.pack(sortedPoints)

      val visualizer = new JsLoadVisualizer(new File("C:\\temp\\data.json"))
      visualizer.show(packedPoints)
    }
  }
}