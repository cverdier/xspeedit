package net.xspeedit.input

import net.xspeedit.codec.ItemDecoder

import scala.util.{Success, Try}

object SampleInput {

  def getInputs: List[Try[Input]] = {
    val items = ItemDecoder.parseList("163841689525773")
    List(Success(Input(items)))
  }
}
