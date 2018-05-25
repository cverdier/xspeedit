package net.xspeedit

import net.xspeedit.algo.PackBuilder
import net.xspeedit.input.Input
import net.xspeedit.output.Output

import scala.util.Try

object XSpeedIt {

  def run(inputs: List[Try[Input]]): List[Try[Output]] = {
    inputs.map(_.map { input =>
      val packs = PackBuilder.buildPacks(input.items)
      Output(packs)
    })
  }
}
