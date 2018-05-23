package de.htwg.scalala.simpledsl

import de.htwg.scalala.music.{ Instrument => MidiInstrument, Key }

import scala.util.parsing.input.Positional

case class Line(val instrument: Instrument, val notes: List[Note]) extends Positional

case class Instrument(name: String) {
  val instruments = Map[String, MidiInstrument](
    "Piano" -> MidiInstrument(name = "Piano", instrumentID = 0, channelID = 0),
    "Marimba" -> MidiInstrument(name = "Marimba", instrumentID = 13, channelID = 1),
    "Xylophone" -> MidiInstrument(name = "Xylophone", instrumentID = 14, channelID = 2),
    "Organ" -> MidiInstrument(name = "Organ", instrumentID = 20, channelID = 3),
    "Guitar" -> MidiInstrument(name = "Guitar", instrumentID = 25, channelID = 4),
    "Bass" -> MidiInstrument(name = "Bass", instrumentID = 33, channelID = 5),
    "Violin" -> MidiInstrument(name = "Violin", instrumentID = 41, channelID = 6),
    "Cello" -> MidiInstrument(name = "Cello", instrumentID = 43, channelID = 7),
    "Trumpet" -> MidiInstrument(name = "Trumpet", instrumentID = 57, channelID = 8),
    "Tuba" -> MidiInstrument(name = "Tuba", instrumentID = 59, channelID = 10),
    "Horn" -> MidiInstrument(name = "Horn", instrumentID = 61, channelID = 11),
    "Sax" -> MidiInstrument(name = "Sax", instrumentID = 68, channelID = 12),
    "Oboe" -> MidiInstrument(name = "Oboe", instrumentID = 69, channelID = 13),
    "Clarinet" -> MidiInstrument(name = "Clarinet", instrumentID = 72, channelID = 14),
    "Flute" -> MidiInstrument(name = "Flute", instrumentID = 74, channelID = 15)
  )

  val instrument: MidiInstrument = instruments.get(name).orNull
}

case class Note(name: Char) {
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