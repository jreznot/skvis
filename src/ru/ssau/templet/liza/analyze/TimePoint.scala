package ru.ssau.templet.liza.analyze

/**
 * @author Yuriy Artamonov 
 */
class TimePoint(val i: Int, var x: Double, var y: Double) {
}

object TimePoint {
  def apply(i: Int, x: Double, y: Double) = new TimePoint(i, x, y)
}