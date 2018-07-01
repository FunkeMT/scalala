package de.htwg.scalala.midi

import de.htwg.scalala.music._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MidiFileTest extends FunSuite {

  test("testSaveFile") {
    val myFile = new MidiFile()

    myFile.changeToInstrument(Piano, Piano.channelID)
    myFile.addKey(c, Piano.channelID)
    myFile.addKey(f, Piano.channelID)

    myFile.changeToInstrument(Marimba, Marimba.channelID)
    myFile.addKey(a, Marimba.channelID)
    myFile.addKey(b, Marimba.channelID)

    myFile.finalFile()
    myFile.saveFile()
  }

}
