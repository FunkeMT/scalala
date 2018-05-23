package de.htwg.scalala.midi

import java.io.File

import de.htwg.scalala.music.Key
import javax.sound.midi._

class MidiFile(instrumentID: Int = 0, channelID: Int = 0) {

  // CONTROL CHANGE MESSAGES
  val NoteON = 0x90
  val NoteOFF = 0x80

  // Tick Counter
  var currTick = 1

  //**** Create a new Sequence
  val sequence = new Sequence(javax.sound.midi.Sequence.PPQ, 24)

  //****  Obtain a MIDI track from the sequence  ****
  val track = sequence.createTrack
  val b = Array(0xF0.toByte, 0x7E, 0x7F, 0x09, 0x01, 0xF7.toByte).map(_.toByte)
  val sm = new SysexMessage()
  sm.setMessage(b, 6)
  var me = new MidiEvent(sm, 0.toLong)
  track.add(me)

  //****  set tempo (meta event)  ****
  var mt = new MetaMessage
  val bt = Array(0x07, 0xA1, 0x20).map(_.toByte)
  mt.setMessage(0x51, bt, 3)
  me = new MidiEvent(mt, 0.toLong)
  track.add(me)

  //****  set track name (meta event)  ****
  mt = new MetaMessage
  val TrackName = new String("midifile track")
  mt.setMessage(0x03, TrackName.getBytes, TrackName.length)
  me = new MidiEvent(mt, 0.toLong)
  track.add(me)

  //****  set omni on  ****
  var mm = new ShortMessage
  mm.setMessage(0xB0, 0x7D, 0x00)
  me = new MidiEvent(mm, 0.toLong)
  track.add(me)

  //****  set poly on  ****
  mm = new ShortMessage
  mm.setMessage(0xB0, 0x7F, 0x00)
  me = new MidiEvent(mm, 0.toLong)
  track.add(me)

  def changeToInstrument(instrumentID: Int = 0, channel: Int = 0xC0): Boolean = {
    //****  set instrument  ****
    mm = new ShortMessage
    mm.setMessage(channel, instrumentID, 0x00)
    me = new MidiEvent(mm, currTick.toLong)
    track.add(me)
  }

  def addKey(key: Key, channel: Int = 0): Boolean = {
    require(channel >= 0 && channel <= 15)

    //****  note on  ****
    mm = new ShortMessage
    mm.setMessage(NoteON + channel, key.midiNumber, 0x60)
    me = new MidiEvent(mm, currTick.toLong)
    track.add(me)

    currTick += key.ticks

    //****  note off  ****
    mm = new ShortMessage
    mm.setMessage(NoteOFF + channel, key.midiNumber, 0x40)
    me = new MidiEvent(mm, currTick.toLong)
    track.add(me)
  }

  def finalFile(): Boolean = {
    //****  set end of track (meta event) 1 ticks later  ****

    currTick += 1

    mt = new MetaMessage
    val bet = Array[Byte]() // empty array
    mt.setMessage(0x2F, bet, 0)
    me = new MidiEvent(mt, currTick.toLong)
    track.add(me)
  }

  def saveFile(): File = {
    //****  write the MIDI sequence to a MIDI file  ****
    var file = new File("./midifile.mid")
    MidiSystem.write(sequence, 1, file)

    println("MIDI-File written at: ")
    println(file.getAbsolutePath)

    file
  }
}
