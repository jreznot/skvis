package ru.ssau.templet.liza.analyze.load

import ru.ssau.templet.liza.TimeStamp
import scala.collection.mutable.ArrayBuffer

/**
 * @author Yuriy Artamonov 
 */
class ClusterDescriptor(val timeStamp: TimeStamp, val groups: List[GroupDescriptor], val nodes: Map[String, String]) {
  def equalsParams(that: ClusterDescriptor): Boolean = {
    if (this eq that) {
      true
    } else if (this.nodes.size != that.nodes.size || this.groups.size != that.groups.size) {
      false
    } else {
      val thisGroups = (new ArrayBuffer[GroupDescriptor] ++= this.groups).sortBy(f => f.properties)
      val thatGroups = (new ArrayBuffer[GroupDescriptor] ++= that.groups).sortBy(f => f.properties)

      if (!thisGroups.sameElements(thatGroups)) {
        false
      } else {
        var notFound = true
        val iterator: Iterator[(String, String)] = this.nodes.iterator

        while (iterator.hasNext && notFound) {
          val (x, y) = iterator.next()

          notFound = that.nodes(x) == y
        }

        notFound
      }
    }
  }

  def =~=(that: ClusterDescriptor) = equalsParams(that)

  override def toString: String = "Cluster snapshot " + timeStamp
}