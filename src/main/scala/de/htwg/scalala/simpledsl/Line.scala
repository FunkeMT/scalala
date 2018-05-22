package de.htwg.scalala.simpledsl

import scala.util.parsing.input.Positional

case class Line(val instrument: Instrument, val notes: List[Note]) extends Positional

case class Instrument(name: String)

case class Note(name: Char)