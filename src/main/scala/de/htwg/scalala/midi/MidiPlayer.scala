
package de.htwg.scalala.midi

import de.htwg.scalala.music.{ Context, Key }

import scala.concurrent.duration._
import scala.language.postfixOps

case class MidiPlayer(instrumentID: Int = 0, channelID: Int = 0) {

  changeToInstrument(instrumentID)

  def play(key: Int = 60, duration: FiniteDuration = 800 milliseconds, volume: Int = Context.volume): Unit = {
    // function deactivated
  }
  def play(set: Set[Key], volume: Int): Unit = {
    val duration = set.head.duration
    set.foreach { key => start(key = key.midiNumber, volume = volume) }
    Thread.sleep(duration.toMillis)
    set.foreach { key => stop(key.midiNumber, volume = volume) }
  }

  def start(key: Int, volume: Int = 75): Unit = {
    // function deactivated
  }

  def stop(key: Int, volume: Int = 75): Unit = {
    // function deactivated
  }

  def changeToInstrument(instrumentID: Int = 0) = {
    // function deactivated
  }

}