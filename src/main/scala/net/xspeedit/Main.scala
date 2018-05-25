package net.xspeedit

import net.xspeedit.input.{CommandLineInput, FileInput, GeneratorInput, SampleInput}
import net.xspeedit.output.ConsoleOutput

object Main {

  def main(args: Array[String]): Unit = {
    val command = args.toList match {
      case Nil =>
        Some(SampleInput.getInputs)
      case "-g" :: IntArgument(count) :: "-i" :: IntArgument(perInput) :: Nil =>
        Some(GeneratorInput.getInputs(count, perInput))
      case "-f" :: path :: Nil =>
        Some(FileInput.getInputs(path))
      case items :: Nil =>
        Some(CommandLineInput.getInputs(items))
      case _ =>
        usage()
        None
    }
    command.foreach { inputs =>
      val outputs = XSpeedIt.run(inputs)
      ConsoleOutput.writeOutputs(outputs)
    }
  }

  def usage(): Unit = {
    println(
      """Usage:
        |  java -jar xspeedit.jar
        |    run packing with the built-in sample
        |  java -jar xspeedit.jar [items]
        |    run packing on the provided [items], expected as a string of digits between 1 and 9
        |  java -jar xspeedit.jar -g [count] -i [items]
        |    run packing on 'count' generated samples, each containing 'items' items (both should integers > 0)
        |  java -jar xspeedit.jar -f [path]
        |    run packing on each line of the file found at 'path'
        |  java -jar xspeedit.jar --help
        |    display this usage information
      """.stripMargin
    )
  }

  object IntArgument {
    def unapply(arg: String): Option[Int] = try {
      val int = arg.toInt
      if (int <= 0) throw new IllegalArgumentException(s"Expected strictly positive integer for $arg")
      Some(int)
    } catch {
      case error: Exception => throw new IllegalArgumentException(s"Expected strictly positive integer for $arg : $error")
    }
  }
}

