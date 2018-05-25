package net.xspeedit.algo

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, PropSpec}

class PackBuilderPropertySpec extends PropSpec with GeneratorDrivenPropertyChecks with Matchers {

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 100, minSize = 0, sizeRange = 1000, workers = 4)

  val itemsGenerator: Gen[List[Int]] = Gen.containerOf[List,Int](Gen.oneOf(1, 2, 3, 4, 5, 6, 7, 8, 9))

  property("PackBuilder should preserve all items when building packs") {

    forAll(itemsGenerator) { items =>
      val packs = PackBuilder.buildPacks(items)
      val packItems = packs.flatMap(_.items)
      packItems should contain theSameElementsAs items
    }
  }
}
