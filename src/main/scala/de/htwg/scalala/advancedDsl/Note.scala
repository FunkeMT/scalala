package de.htwg.scalala.advancedDsl

import de.htwg.scalala.music.Key

case class Note(name: Char) extends NoteElement {
  val notes = Map[Char, Key](
    'c' -> Key(midiNumber = 60),
    'd' -> Key(midiNumber = 62),
    'e' -> Key(midiNumber = 64),
    'f' -> Key(midiNumber = 65),
    'g' -> Key(midiNumber = 67),
    'a' -> Key(midiNumber = 69),
    'b' -> Key(midiNumber = 71)
  )

  val note: Key = notes.get(name).orNull
}