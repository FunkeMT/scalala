package de.htwg.scalala.advancedDsl

import de.htwg.scalala.music.Key

case class Note(name: String, accidental: String = "", duration: Int = 0, octave: List[String] = List[String]()) extends NoteElement {
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
    "HiHatOpen" -> Key(midiNumber = 46),

    "TomLowFloor" -> Key(midiNumber = 41),
    "TomHighFloor" -> Key(midiNumber = 43),
    "TomLow" -> Key(midiNumber = 45),
    "TomLowMid" -> Key(midiNumber = 47),
    "TomHiMid" -> Key(midiNumber = 48),
    "TomHigh" -> Key(midiNumber = 50),

    "DrumAcousticBass" -> Key(midiNumber = 35),
    "DrumBass" -> Key(midiNumber = 36),

    "SnareAcoustic" -> Key(midiNumber = 38),
    "SnareElectric" -> Key(midiNumber = 40),

    "SideStick" -> Key(midiNumber = 37),
    "HandClap" -> Key(midiNumber = 39)
  )

  val note: Key = {
    var n = accidental match {
      case "sharp" => notes.get(name).get.sharp
      case "flat" => notes.get(name).get.flat
      case "dot" => notes.get(name).get.dot
      case _ => notes.get(name).orNull
    }
    octave map {
      case "+" => n = n +
      case "-" => n = n -
    }
    n.ticks(if (duration > 0) 16 / duration else 16)
  }
}