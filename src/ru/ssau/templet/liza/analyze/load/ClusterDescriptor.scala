package ru.ssau.templet.liza.analyze.load

import ru.ssau.templet.liza.TimeStamp

/**
 * @author Yuriy Artamonov 
 */
class ClusterDescriptor(val timeStamp: TimeStamp, val groups: List[GroupDescriptor], val nodes: Map[String, String]) {
}