package net.xspeedit.codec

object ItemDecoder {

  // TODO: report all errors
  def parseList(itemList: String): List[Int] = {
    itemList.toList.map {
      case item @ ('1'|'2'|'3'|'4'|'5'|'6'|'7'|'8'|'9') => item.asDigit
      case other: Char => throw DecodeException(other)
    }
  }

  case class DecodeException(character: Char) extends Exception(s"Failed to parse item '$character'")
}
