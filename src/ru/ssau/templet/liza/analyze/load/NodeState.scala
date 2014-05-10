package ru.ssau.templet.liza.analyze.load

/**
 * @author Yuriy Artamonov 
 */
object NodeState extends Enumeration {
  val Free, Down, JobExclusive = Value

  def parse(id: String): NodeState.Value = {
    id match {
      case "free" => Free
      case "job-exclusive" => JobExclusive
      case down if down.contains("down") || down.contains("offline") => Down
      case _ => Down
    }
  }
}