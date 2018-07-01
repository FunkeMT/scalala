package de.htwg.scalala.simpledsl

import java.io.File

import de.htwg.scalala.midi.MidiFile

class Interpreter(song: Song) {

  val midiFile: MidiFile = new MidiFile()

  def run(): File = {
    walk(song.line)
    midiFile.finalFile()
    midiFile.saveFile()
  }

  private def walk(tree: List[Line]) {
    if (!tree.isEmpty) {
      tree.head match {
        case Line(instrument, notes) => {
          println("Instrument: " + instrument)
          midiFile.changeToInstrument(instrument.instrument, instrument.instrument.channelID)

          println("Notes:" + notes)
          notes.foreach(note => midiFile.addKey(note.note, instrument.instrument.channelID))

          walk(tree.tail)
        }
        case _ => ()
      }
    }
  }
}
