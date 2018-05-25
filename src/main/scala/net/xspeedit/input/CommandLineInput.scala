package net.xspeedit.input

import net.xspeedit.codec.ItemDecoder

import scala.util.Try

object CommandLineInput {

  def getInputs(argument: String): List[Try[Input]] = {
    val items = Try(ItemDecoder.parseList(argument))
    List(items.map(Input))
  }
}
