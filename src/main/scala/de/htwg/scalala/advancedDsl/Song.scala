package de.htwg.scalala.advancedDsl

import scala.collection.mutable.Map

case class Song(musicians: List[Musician], track: Track) {
  val musician = Map[String, Musician]()
}
