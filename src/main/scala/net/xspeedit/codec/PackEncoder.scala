package net.xspeedit.codec

import net.xspeedit.pack.Pack

object PackEncoder {

  def writePacks(packs: List[Pack]): String = {
    packs.map(_.items.mkString).mkString("/")
  }
}
