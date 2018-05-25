package net.xspeedit.codec

import net.xspeedit.pack.Pack
import org.scalatest.{Matchers, WordSpec}

class PackEncoderSpec extends WordSpec with Matchers {

  "A PackEncoder" should {

    "write grouped pack items separated by '/'" in {
      val packs = List(Pack(List(1, 6, 3)), Pack(List(5, 5)), Pack(List(5, 2, 2)), Pack(List(9)), Pack(List(2, 1)))
      val result = PackEncoder.writePacks(packs)
      result should equal("163/55/522/9/21")
    }
  }
}
