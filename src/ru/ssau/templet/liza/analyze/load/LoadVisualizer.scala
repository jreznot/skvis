package ru.ssau.templet.liza.analyze.load

/**
 * @author Yuriy Artamonov 
 */
trait LoadVisualizer {

  def show(timeseries: Seq[ClusterDescriptor])
}