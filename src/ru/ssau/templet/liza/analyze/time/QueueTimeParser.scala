package ru.ssau.templet.liza.analyze.time

import java.io.{FileInputStream, File, InputStream}
import java.util.{TimeZone, StringTokenizer}
import scala.collection.mutable
import scala.util.control.Breaks._
import java.text.{ParseException, SimpleDateFormat}
import ru.ssau.templet.liza.analyze._
import ru.ssau.templet.liza.{TimeInterval, TimeStamp}

/**
 * @author Yuriy Artamonov 
 */
object QueueTimeParser {

  def parse(timeStamp: TimeStamp, stream: InputStream) : QueueDescriptor = {
    val timeFormat = {
      val format = new SimpleDateFormat("hh:mm:ss")
      format.setTimeZone(TimeZone.getTimeZone("UTC"))
      format
    }

    var queueRead = false
    var queueMap = new mutable.HashMap[String, TimeInterval]

    breakable {
      for (line <- scala.io.Source.fromInputStream(stream, "UTF-8").getLines()) {
        line match {
          case hyphen if hyphen.startsWith("--") => queueRead = true
          case node if node.startsWith("n") => break()
          case task => if (queueRead) {
            val tokenizer = new StringTokenizer(task)

            (1 to 2).foreach(_ => tokenizer.nextElement())

            val queueName = tokenizer.nextToken()

            (1 to 7).foreach(_ => tokenizer.nextElement())

            val timeToken= tokenizer.nextToken()

            val timeInterval = try { timeFormat.parse(timeToken).getTime } catch { case e: ParseException => 0}

            queueMap += ((queueName, queueMap.getOrElse(queueName, 0L) + timeInterval / 1000))
          }
        }
      }
    }

    new QueueDescriptor(timeStamp, queueMap.toMap)
  }

  def parse(file: File) : QueueDescriptor = parse(
    new SimpleDateFormat(experimentalDataFileNameFormatString).parse(file.getName).getTime,
    new FileInputStream(file)
  )
}