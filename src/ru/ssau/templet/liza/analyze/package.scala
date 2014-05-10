package ru.ssau.templet.liza

import java.text.SimpleDateFormat

/**
 * @author Yuriy Artamonov 
 */
package object analyze {

  val experimentalDataFileNameFormatString = "yyyy_MM_dd__hh_mm_ss"

  val experimentalDataFileNameFormat = new SimpleDateFormat(experimentalDataFileNameFormatString)

  def medianX(x: Seq[Double]): Double = {
    x.sum / x.length
  }

  def dispersionX(x: Seq[Double]) : Double = {
    val mX = medianX(x)

    val quadX = for (xi <- x) yield xi*xi
    val mQuadX = medianX(quadX)

    mQuadX - mX*mX
  }
}
