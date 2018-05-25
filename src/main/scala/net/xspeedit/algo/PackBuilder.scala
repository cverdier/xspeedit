package net.xspeedit.algo

import net.xspeedit.pack.Pack
import net.xspeedit.pack.Pack.MaxCapacity

object PackBuilder {

  type IndexedItems = Map[Int, List[Int]]
  type IndexedPacks = Map[Int, List[Pack]]

  def buildPacks(items: List[Int]): List[Pack] = {
    val threshold = math.ceil(MaxCapacity.toFloat / 2).toInt + 1
    val (_, refinedPacks) = decreasingWeightsFrom(threshold - 1)
      .foldLeft(initialSplit(items, threshold)) {
        case ((remainingItems, packs), weight) =>
          refineWeight(remainingItems, packs, weight)
      }
    refinedPacks.values.toList.flatten
  }

  private[algo] def initialSplit(items: List[Int], threshold: Int): (IndexedItems, IndexedPacks) = {
    val indexedItems = items.groupBy(identity)
    val initialIndexedPacks = (1 to MaxCapacity).map(_ -> List[Pack]()).toMap
    val packs = initialIndexedPacks ++ indexedItems.filterKeys(_ >= threshold).mapValues(_.map(item => Pack(List(item))))
    val remainingItems = indexedItems.filterKeys(_ < threshold)
    (remainingItems, packs)
  }

  private[algo] def refineWeight(items: IndexedItems, packs: IndexedPacks, weight: Int): (IndexedItems, IndexedPacks) = {
    val availablePacks = decreasingWeightsFrom(weight).flatMap(packs.getOrElse(_, List()))
    val untouchedPacks = packs.filterKeys(_ > MaxCapacity - weight)
    val itemsForWeight = items.getOrElse(weight, List())
    val remainingItems = items.filterKeys(_ != weight)

    val filledPacks = availablePacks.zip(itemsForWeight).map { case (pack, item) => pack.addItem(item) }
    val remainingPacks = availablePacks.drop(filledPacks.size)
    val newPacks = itemsForWeight.drop(filledPacks.size).grouped(MaxCapacity / weight).map(Pack.apply).toList

    val refinedPacks = (filledPacks ++ remainingPacks ++ newPacks).foldLeft(untouchedPacks) {
      (indexedPacks, pack) =>
        val capacity = pack.items.sum
        val capacityPacks = indexedPacks.getOrElse(capacity, List()) :+ pack
        indexedPacks.updated(capacity, capacityPacks)
    }
    (remainingItems, refinedPacks)
  }

  private def decreasingWeightsFrom(weight: Int) = {
    (1 to (MaxCapacity - weight)).reverse
  }
}
