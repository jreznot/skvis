package ru.ssau.templet.liza.analyze.load

import java.io._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import java.text.SimpleDateFormat
import java.nio.charset.StandardCharsets
import ru.ssau.templet.liza.TimeStamp
import ru.ssau.templet.liza.analyze._

/**
 * @author Yuriy Artamonov 
 */
object LoadParser {

  def parse(timeStamp: TimeStamp, stream: InputStream): ClusterDescriptor = {
    val nodes = new ListBuffer[NodeDescriptor]

    var currentName: String = null
    var currentProperties: String = null
    var currentState: NodeState.Value = null

    val nodeNames = new mutable.LinkedHashMap[String, String]

    for (line <- scala.io.Source.fromInputStream(stream, StandardCharsets.UTF_8.name()).getLines()) {
      line match {
        case name if name.startsWith("n") =>
          if (currentName != null) {
            nodeNames += ((currentName, currentProperties))
            nodes += new NodeDescriptor(currentName, currentProperties, currentState)

            currentProperties = null
            currentState = null
          }
          currentName = name

        case state if state.contains("state = ") =>
          currentState = NodeState.parse(state.replace("state = ", "").trim)

        case properties if properties.contains("properties = ") =>
          currentProperties = properties.replace("properties = ", "").trim

        case _ =>
      }
    }

    val freeMap = new mutable.HashMap[String, Int]
    val downMap = new mutable.HashMap[String, Int]
    val jobExclusiveMap = new mutable.HashMap[String, Int]

    val groups = new mutable.HashSet[String]

    for (node <- nodes) {
      node.state match {
        case NodeState.Free => freeMap.put(node.properties, freeMap.getOrElse(node.properties, 0) + 1)
        case NodeState.Down => downMap.put(node.properties, downMap.getOrElse(node.properties, 0) + 1)
        case NodeState.JobExclusive => jobExclusiveMap.put(node.properties, jobExclusiveMap.getOrElse(node.properties, 0) + 1)
        case _ =>
      }

      if (!groups.contains(node.properties)) {
        groups += node.properties
      }
    }

    var groupDescriptors = new ListBuffer[GroupDescriptor]
    for (group <- groups) {
      groupDescriptors += new GroupDescriptor(group,
        freeMap.getOrElse(group, 0),
        downMap.getOrElse(group, 0),
        jobExclusiveMap.getOrElse(group, 0))
    }

    new ClusterDescriptor(timeStamp, groupDescriptors.toList, nodeNames.toMap)
  }

  def parse(file: File) : ClusterDescriptor = parse(
    new SimpleDateFormat(experimentalDataFileNameFormatString).parse(file.getName).getTime,
    new FileInputStream(file)
  )
}