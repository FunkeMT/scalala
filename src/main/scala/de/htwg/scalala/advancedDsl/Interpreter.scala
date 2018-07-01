package de.htwg.scalala.advancedDsl

import java.io.File

import de.htwg.scalala.music.{ Instrument => MidiInstrument }
import de.htwg.scalala.midi.MidiFile

import scala.collection.mutable.ListBuffer

class Interpreter(song: Song) {

  val midiFile: MidiFile = new MidiFile()

  // Loop Map
  var loopListBuff = ListBuffer[Musician]()

  // Local Tick Map
  var tickMap = collection.mutable.Map[String, Int]().withDefaultValue(1)

  def run(): File = {
    walk(song.musicians)
    walk(List[Statement](song.track))
    midiFile.finalFile()
    midiFile.saveFile()
  }

  private def addMusician(musician: Musician, position: Int) = {
    val instr = musician.instrument.instrument
    tickMap += (musician.identifier -> position)

    midiFile.changeToInstrument(
        instr,
        position
    )

    musician.musicElement match {
      case noteElements: NoteElements => {
        addNoteElements(musician.identifier, noteElements, instr)
      }
      case loopElement: LoopElement => {
        loopListBuff.append(musician)
      }
    }
  }

  private def addLoopElements() = {
    val maxTick = tickMap.maxBy(_._2)._2

    loopListBuff.toList.foreach(elem => {
      // start from track begin
      midiFile.changeToInstrument(elem.instrument.instrument, 0)

      while (tickMap(elem.identifier) < maxTick) {
        addNoteElements(elem.identifier, elem.musicElement.asInstanceOf[LoopElement].noteElements, elem.instrument.instrument)
      }
    })
  }

  private def addNoteElements(identifier: String, noteElements: NoteElements, instrument: MidiInstrument) = {
    noteElements.elements.foreach(el => {
      println("tickMap = " + tickMap)
      el match {
        case note: Note => {
          midiFile.addKey(note.note, instrument.channelID, tickMap.getOrElse(identifier, 1))
          tickMap(identifier) += note.note.ticks
        }
        case chord: Chord => {
          chord.notes.map(_.note).foreach(midiFile.addKey(_, instrument.channelID, tickMap.getOrElse(identifier, 1)))
        }
        case _ => sys.error("Error: Undefined identifier '" + el + "' being called")
      }
    })
  }

  private def sysError(tree: List[Statement], identifier: String) = {
    sys.error("Error: Undefined identifier '" + identifier + "' being called at position [" + tree.head.pos.column + "] on line: " + tree.head.pos.line)
  }

  private def walk(tree: List[Statement]) {
    if (!tree.isEmpty) {
      tree.head match {
        case track: Track => {
          println("### Track called")
          println(track.musicVars)
          println(track.tempo)

          midiFile.setTempo(track.tempo)

          for (musicVar <- track.musicVars) {
            song.musician.get(musicVar.identifier) match {
              case Some(value) => addMusician(value, musicVar.position)
              case None => sysError(tree, musicVar.identifier)
            }
          }
          addLoopElements()

          walk(tree.tail)
        }
        case musician: Musician => {
          song.musician += (musician.identifier -> musician)
          println("### Musician")
          println(musician.identifier)
          println(musician.instrument)
          println(musician.musicElement)

          walk(tree.tail)
        }
        case _ => (println("not found ..."))
      }
    }
  }
}
