package de.htwg.scalala.actors

import akka.actor._
import de.htwg.scalala.music._

trait MusicMessage
case object Start extends MusicMessage
case class PlayNow(music:Music) extends MusicMessage

