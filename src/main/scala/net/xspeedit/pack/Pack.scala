package net.xspeedit.pack

case class Pack(items: List[Int]) {
  def addItem(item: Int): Pack = copy(items :+ item)
}

object Pack {
  val MaxCapacity: Int = 10
}

