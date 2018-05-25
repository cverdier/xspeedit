package net.xspeedit.codec

import net.xspeedit.codec.ItemDecoder.DecodeException
import org.scalatest.{Matchers, WordSpec}

class ItemDecoderSpec extends WordSpec with Matchers {

  "An ItemDecoder" should {

    "Successfully parse a list of items" in {
      val rawItems = "23456787318152671984167271"
      val parsedItems = ItemDecoder.parseList(rawItems)
      parsedItems should contain theSameElementsInOrderAs List(2, 3, 4, 5, 6, 7, 8, 7, 3, 1, 8, 1, 5, 2, 6, 7, 1, 9, 8, 4, 1, 6, 7, 2, 7, 1)
    }

    "Throw error when there is a 0" in {
      val badItems = "36901155"
      intercept[DecodeException] {
        ItemDecoder.parseList(badItems)
      }
    }

    "Throw error when there are invalid characters" in {
      val badItems = "121a44-B#99"
      intercept[DecodeException] {
        ItemDecoder.parseList(badItems)
      }
    }
  }
}
