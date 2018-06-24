package de.htwg.scalala.advancedDsl

import scala.collection.mutable.Map

case class Song(statements: List[Statement]) {
  val musician = Map[String, Musician]()
}
