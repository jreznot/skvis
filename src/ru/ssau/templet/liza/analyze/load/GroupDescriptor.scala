package ru.ssau.templet.liza.analyze.load

/**
 * @author Yuriy Artamonov 
 */
class GroupDescriptor(val properties: String, val freeCount: Int, val downCount: Int, val jobExclusiveCount: Int) {
  override def equals(obj: scala.Any): Boolean = {
    if (obj == null || !obj.isInstanceOf[GroupDescriptor]) {
      false
    } else {
      val that = obj.asInstanceOf[GroupDescriptor]

      if (this eq that) {
        true
      } else {
        this.properties == that.properties &&
          this.downCount == that.downCount &&
          this.freeCount == that.freeCount &&
          this.jobExclusiveCount == that.jobExclusiveCount
      }
    }
  }
}