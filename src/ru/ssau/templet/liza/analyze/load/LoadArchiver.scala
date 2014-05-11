package ru.ssau.templet.liza.analyze.load

import scala.collection.mutable.ArrayBuffer

/**
 * @author Yuriy Artamonov 
 */
object LoadArchiver {
  def pack(history: IndexedSeq[ClusterDescriptor]) : IndexedSeq[ClusterDescriptor] = {
    val packed = ArrayBuffer[ClusterDescriptor]()

    var i = 0
    while (i < 2 && i < history.size) {
      packed += history(i)
      i = i +1
    }

    var prevEquals = false
    if (packed.size == 2) {
      prevEquals = packed(0) =~= packed(1)
    }

    while (i < history.size) {
      val cd = history(i)

      val equals = cd =~= packed.last
      if (prevEquals && equals) {
        packed.remove(packed.size - 1)
      }
      packed += cd

      prevEquals = equals

      i = i + 1
    }

    packed
  }
}