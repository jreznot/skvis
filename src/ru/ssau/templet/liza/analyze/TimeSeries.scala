package ru.ssau.templet.liza.analyze

/**
 * @author Yuriy Artamonov 
 */
class TimeSeries(val values: Seq[Double]) {
  val mX = medianX(values)
  val dX = dispersionX(values)
  val n = values.length

  def apply(index: Int) = values(index)
}

object TimeSeries {
  def apply(x: Seq[Double]) = new TimeSeries(x)
}