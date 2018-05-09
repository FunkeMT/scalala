package de.htwg.scalala.midi

import de.htwg.scalala.music._
import org.scalatest.FunSuite

class MidiFileTest extends FunSuite {

  test("testSaveFile") {
    val myFile = new MidiFile()
    println(myFile.currTick)
    myFile.changeToInstrument(0)
    println(myFile.currTick)
    myFile.addKey(c1, 0)
    println(myFile.currTick)
    myFile.changeToInstrument(13, 0xC1)
    println(myFile.currTick)
    myFile.addKey(f1, 1)
    println(myFile.currTick)
    myFile.finalFile()
    println(myFile.currTick)
    myFile.saveFile()
  }

}
