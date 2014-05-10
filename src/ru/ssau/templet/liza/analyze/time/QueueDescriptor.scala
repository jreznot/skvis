package ru.ssau.templet.liza.analyze.time

import ru.ssau.templet.liza.{TimeInterval, TimeStamp}

/**
 * @author Yuriy Artamonov 
 */
class QueueDescriptor(val timeStamp: TimeStamp, val timeMap: Map[String, TimeInterval]) {
}