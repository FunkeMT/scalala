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

  private def walk(tree: Line) {
    if (tree != null) {
      println("Instrument: " + tree.instrument)
      midiFile.changeToInstrument(tree.instrument.instrument.instrumentID)

      println("Notes:" + tree.notes)
      tree.notes.foreach(note => midiFile.addKey(note.note))
    }
  }
}
