package de.htwg.scalala.midi

import java.io.File

import de.htwg.scalala.music.{Key, Instrument}
import javax.sound.midi._

class MidiFile() {

  // DEFAULT VALUES
  val TempoBPM = 120

  // CONTROL CHANGE MESSAGES
  val NoteON = 0x90
  val NoteOFF = 0x80

  // Tick Counter
  var lastTick = 1

  //**** Create a new Sequence
  val sequence = new Sequence(javax.sound.midi.Sequence.PPQ, 24)

  //****  Obtain a MIDI track from the sequence  ****
  val track = sequence.createTrack
  val b = Array(0xF0.toByte, 0x7E, 0x7F, 0x09, 0x01, 0xF7.toByte).map(_.toByte)
  val sm = new SysexMessage()
  sm.setMessage(b, 6)
  var me = new MidiEvent(sm, 0.toLong)
  track.add(me)

  //****  set track name (meta event)  ****
  var mt = new MetaMessage
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

  def setTempo(tempoBPM: Int = TempoBPM): Unit = {
    //****  set tempo (meta event)  ****
    var mt = new MetaMessage
    //val bt = Array(0x07, 0xA1, 0x20).map(_.toByte)
    val tempo = 60000000 / tempoBPM
    val bt = BigInt(tempo).toByteArray
    mt.setMessage(0x51, bt, bt.length)
    me = new MidiEvent(mt, 0.toLong)
    track.add(me)
  }

  def changeToInstrument(instrument: Instrument, pos: Int = 1): Boolean = {
    //****  set instrument  ****
    mm = new ShortMessage
    mm.setMessage(instrument.channelID + 0xC0, instrument.instrumentID, 0x00)
    me = new MidiEvent(mm, pos)
    track.add(me)
  }


  def addKey(key: Key, channel: Int, pos: Int = 0): Unit = {
    require(channel >= 0 && channel <= 15)

    //****  note on  ****
    mm = new ShortMessage
    mm.setMessage(NoteON + channel, key.midiNumber, 0x60)
    me = new MidiEvent(mm, pos)
    track.add(me)

    val tickEnd = pos + key.ticks

    //****  note off  ****
    mm = new ShortMessage
    mm.setMessage(NoteOFF + channel, key.midiNumber, 0x40)
    me = new MidiEvent(mm, tickEnd)
    track.add(me)

    lastTick = if (tickEnd > lastTick) tickEnd else lastTick
  }

  def finalFile(): Unit = {
    //****  set end of track (meta event) 1 ticks later  ****

    mt = new MetaMessage
    val bet = Array[Byte]() // empty array
    mt.setMessage(0x2F, bet, 0)
    me = new MidiEvent(mt, lastTick + 1)
    track.add(me)
  }

  def saveFile(): File = {
    //****  write the MIDI sequence to a MIDI file  ****
    var file = new File("midifile.mid")
    MidiSystem.write(sequence, 1, file)

    println("MIDI-File written at: ")
    println(file.getAbsolutePath)

    file
  }
}
