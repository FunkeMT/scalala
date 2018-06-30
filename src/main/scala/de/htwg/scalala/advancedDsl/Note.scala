package de.htwg.scalala.advancedDsl

import de.htwg.scalala.music.Key

case class Note(name: String) extends NoteElement {
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

  val note: Key = notes.get(name).orNull
}