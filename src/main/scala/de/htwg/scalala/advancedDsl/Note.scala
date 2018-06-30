package de.htwg.scalala.advancedDsl

import de.htwg.scalala.music.Key

case class Note(name: String, accidental: String = "", duration: Int = 0, octave: String = "") extends NoteElement {
  val notes = Map[String, Key](
    "c" -> Key(midiNumber = 60),
    "d" -> Key(midiNumber = 62),
    "e" -> Key(midiNumber = 64),
    "f" -> Key(midiNumber = 65),
    "g" -> Key(midiNumber = 67),
    "a" -> Key(midiNumber = 69),
    "h" -> Key(midiNumber = 71),

    "HiHatClosed" -> Key(midiNumber = 42),
    "HiHatPedal" -> Key(midiNumber = 44),
    "HiHatOpen" -> Key(midiNumber = 46)
  )

  val note: Key = {
    println(accidental)
    var n = accidental match {
      case "sharp" => notes.get(name).get.sharp
      case "flat" => notes.get(name).get.flat
      case "dot" => notes.get(name).get.dot
      case _ => notes.get(name).orNull
    }
    n = octave match {
      case "+" => n +
      case "-" => n -
      case _ => n
    }
    n.ticks(if (duration > 0) 16/duration else 16)
  }
}