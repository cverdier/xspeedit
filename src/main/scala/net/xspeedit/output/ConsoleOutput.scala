package net.xspeedit.output

import net.xspeedit.codec.PackEncoder

import scala.util.{Failure, Success, Try}

object ConsoleOutput {

  def writeOutputs(outputs: List[Try[Output]]): Unit = {
    outputs.foreach {

      case Success(output) =>
        val formatted = PackEncoder.writePacks(output.packs)
        println(s"Packed using ${output.packs.size} pack(s) : $formatted")

      case Failure(error) =>
        println(s"Failed to pack : $error")
    }
  }
}
