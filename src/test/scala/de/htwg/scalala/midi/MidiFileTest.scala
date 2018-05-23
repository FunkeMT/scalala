package de.htwg.scalala.midi

import de.htwg.scalala.music._
import org.scalatest.FunSuite

class MidiFileTest extends FunSuite {

  test("testSaveFile") {
    val myFile = new MidiFile()

    myFile.changeToInstrument(Piano.instrumentID, Piano.channelID)
    myFile.addKey(c, Piano.channelID)
    myFile.addKey(f, Piano.channelID)

    myFile.changeToInstrument(Marimba.instrumentID, Marimba.channelID)
    myFile.addKey(a, Marimba.channelID)
    myFile.addKey(b, Marimba.channelID)

    myFile.finalFile()
    myFile.saveFile()
  }

}
