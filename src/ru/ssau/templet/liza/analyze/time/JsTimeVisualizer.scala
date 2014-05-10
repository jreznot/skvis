package ru.ssau.templet.liza.analyze.time

import java.io.{PrintWriter, File}
import java.util
import scala.collection.mutable
import ru.ssau.templet.liza.utils.json.{JsonPoint, JsonRow}
import com.google.gson.Gson

/**
 * @author Yuriy Artamonov 
 */
class JsTimeVisualizer(val jsonFile: File) extends TimeVisualizer {

  override def show(timeseries: Seq[QueueDescriptor]) {
    if (jsonFile.exists())
      jsonFile.delete()

    val rows = new mutable.HashMap[String, JsonRow]
    val queueNames = new mutable.HashSet[String]

    for (descriptor <- timeseries) {
      queueNames ++= descriptor.timeMap.keys
    }

    queueNames.foreach(queueName => rows += ((queueName, new JsonRow(queueName))))

    for (descriptor <- timeseries) {
      descriptor.timeMap.foreach(pair => {
        rows(pair._1).getPoints.add(new JsonPoint(descriptor.timeStamp, pair._2))
      })
    }

    rows.foreach(_._2.normalize())

    val sortedRows = rows.values.toList.sortWith((a, b) => a.getName.compareTo(b.getName) >= 0)

    val jsonData = new Gson().toJson(new util.ArrayList[JsonRow](scala.collection.JavaConversions.seqAsJavaList(sortedRows)))

    val writer = new PrintWriter(jsonFile)
    writer.write(jsonData)
    writer.close()
  }
}