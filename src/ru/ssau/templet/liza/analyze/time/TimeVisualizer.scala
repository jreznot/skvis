package ru.ssau.templet.liza.analyze.time

/**
 * @author Yuriy Artamonov 
 */
trait TimeVisualizer {

  def show(timeseries: Seq[QueueDescriptor])
}