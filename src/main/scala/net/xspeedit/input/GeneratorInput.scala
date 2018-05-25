package net.xspeedit.input

import net.xspeedit.pack.Pack

import scala.util.{Random, Success, Try}

object GeneratorInput {

  def getInputs(inputs: Int, itemsPerInput: Int): List[Try[Input]] = {
    (1 to inputs).map { _ =>
      val items = (1 to itemsPerInput).map(_ => Random.nextInt(Pack.MaxCapacity - 1) + 1).toList
      Success(Input(items))
    }.toList
  }
}
