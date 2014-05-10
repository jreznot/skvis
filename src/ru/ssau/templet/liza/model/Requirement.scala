package ru.ssau.templet.liza.model

import ru.ssau.templet.liza.{Count, TimeStamp, TimeInterval}

/**
 * @author Yuriy Artamonov 
 */
class Requirement(val constraints: Map[Constraint, Count],
                  val creationTime : TimeStamp,
                  val executionTime: TimeInterval) {

  var waitingTime : Option[TimeInterval] = None
}