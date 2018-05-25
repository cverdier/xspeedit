package net.xspeedit.algo

import net.xspeedit.pack.Pack
import org.scalatest.{Matchers, WordSpec}

class PackBuilderSpec extends WordSpec with Matchers {

  "A PackBuilder's initialSplit" should {

    "split items on threshold and create packs with large items" in {
      val items = List(1, 9, 5, 4, 8, 6, 1, 4, 8, 8, 7, 2, 5, 8, 1)
      val (remainingItems, packs) = PackBuilder.initialSplit(items, 6)
      packs should contain theSameElementsAs Map(
        10 -> List(),
        9 -> List(pack(9)),
        8 -> List(pack(8), pack(8), pack(8), pack(8)),
        7 -> List(pack(7)),
        6 -> List(pack(6)),
        5 -> List(),
        4 -> List(),
        3 -> List(),
        2 -> List(),
        1 -> List()
      )
      remainingItems should contain theSameElementsAs Map(
        5 -> List(5, 5),
        4 -> List(4, 4),
        2 -> List(2),
        1 -> List(1, 1, 1)
      )
    }
  }

  "A PackBuilder's refineWeight" should {

    "fill available packs with items" in {
      val packs = packsWith(Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5)),
        7 -> List(pack(7), pack(7), pack(7)),
        6 -> List(pack(6), pack(6))
      ))
      val items = Map(
        3 -> List(3, 3, 3),
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )

      val (remainingItems, newPacks) = PackBuilder.refineWeight(items, packs, 3)

      newPacks should contain allElementsOf Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5), pack(7, 3), pack(7, 3), pack(7, 3)),
        6 -> List(pack(6), pack(6))
      )
      remainingItems should contain theSameElementsAs Map(
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )
    }

    "fill packs with less weight items when packs are available" in {
      val packs = packsWith(Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5)),
        7 -> List(pack(7), pack(7), pack(7)),
        6 -> List(pack(6), pack(6)),
        5 -> List(pack(5), pack(5), pack(5))
      ))
      val items = Map(
        3 -> List(3, 3, 3, 3, 3, 3),
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )

      val (remainingItems, newPacks) = PackBuilder.refineWeight(items, packs, 3)

      newPacks should contain allElementsOf Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5), pack(7, 3), pack(7, 3), pack(7, 3)),
        9 -> List(pack(6, 3), pack(6, 3)),
        8 -> List(pack(5, 3))
      )
      remainingItems should contain theSameElementsAs Map(
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )
    }

    "create additional packs when there are not enough available packs" in {
      val packs = packsWith(Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5)),
        7 -> List(pack(7), pack(7)),
        6 -> List(pack(6))
      ))
      val items = Map(
        3 -> List(3, 3, 3, 3, 3, 3, 3),
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )

      val (remainingItems, newPacks) = PackBuilder.refineWeight(items, packs, 3)

      newPacks should contain allElementsOf Map(
        10 -> List(pack(8, 2), pack(6, 3, 1), pack(5, 5), pack(7, 3), pack(7, 3)),
        9 -> List(pack(6, 3), pack(3, 3, 3)),
        3 -> List(pack(3))
      )
      remainingItems should contain theSameElementsAs Map(
        2 -> List(2),
        1 -> List(1, 1, 1, 1)
      )
    }
  }

  private def pack(items: Int*) = Pack(items.toList)

  private def packsWith(packs: Map[Int, List[Pack]]): Map[Int, List[Pack]] = {
    val initial = (1 to Pack.MaxCapacity).map(_ -> List()).toMap
    initial ++ packs
  }
}
