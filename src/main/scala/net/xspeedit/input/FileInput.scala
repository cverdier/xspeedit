package net.xspeedit.input

import java.nio.file.{Files, Paths}

import net.xspeedit.codec.ItemDecoder

import scala.collection.JavaConverters._
import scala.util.control.NonFatal
import scala.util.{Failure, Try}

object FileInput {

  def getInputs(path: String): List[Try[Input]] = try {
    Files.lines(Paths.get(path))
      .iterator().asScala
      .map(line => Try(
        Input(ItemDecoder.parseList(line))
      ))
      .toList
  } catch {
    case NonFatal(error) => List(Failure(error))
  }
}
