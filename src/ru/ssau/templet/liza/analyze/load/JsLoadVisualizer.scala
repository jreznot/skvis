package ru.ssau.templet.liza.analyze.load

import java.io.{PrintWriter, File}
import scala.collection.mutable
import java.util
import ru.ssau.templet.liza.utils.json.{JsonPoint, JsonRow}
import com.google.gson.Gson

/**
 * @author Yuriy Artamonov 
 */
class JsLoadVisualizer(val jsonFile: File) extends LoadVisualizer {

  override def show(timeseries: Seq[ClusterDescriptor]) {
    if (jsonFile.exists())
      jsonFile.delete()

    val nodeNames = new mutable.HashSet[String]
    val groupMap = new mutable.HashMap[String, String]
    var groupSizes = new mutable.HashMap[String, Int]

    timeseries.foreach(cd => {
      nodeNames ++= cd.nodes.keys
      groupMap ++= cd.nodes
    })

    nodeNames.foreach( name => {
      val groupName = groupMap(name)
      groupSizes += ((groupName, groupSizes.getOrElse(groupName, 0) + 1))
    })

    val rows = new mutable.HashMap[String, JsonRow]

    // add up and down rows
    groupSizes.keySet.foreach(groupName => {
      rows.put(groupName, new JsonRow(groupName))
      rows.put(groupName + "_down", new JsonRow("[~]" + groupName))
    })

    for (descriptor <- timeseries) {
      val existsGroups = new mutable.HashSet[String]()
      existsGroups ++= groupSizes.keySet

      for (definition <- descriptor.groups) {
        existsGroups -= definition.properties

        val row = rows.get(definition.properties).get

        row.getPoints.add(new JsonPoint(descriptor.timeStamp, definition.jobExclusiveCount))

        val downRow = rows.get(definition.properties + "_down").get

        downRow.getPoints.add(new JsonPoint(descriptor.timeStamp, -definition.downCount))
      }

      // down groups
      for (definitionProperties <- existsGroups) {
        val row = rows.get(definitionProperties).get

        row.getPoints.add(new JsonPoint(descriptor.timeStamp, 0))

        val downRow = rows.get(definitionProperties + "_down").get

        downRow.getPoints.add(new JsonPoint(descriptor.timeStamp, -groupSizes(definitionProperties)))
      }
    }

    rows.foreach(_._2.normalize())

    val sortedRows = rows.values.toBuffer.sortWith((a, b) => a.getName.compareTo(b.getName) >= 0)

    val jsonData = new Gson().toJson(new util.ArrayList[JsonRow](scala.collection.JavaConversions.seqAsJavaList(sortedRows)))

    val writer = new PrintWriter(jsonFile)
    writer.write(jsonData)
    writer.close()
  }
}