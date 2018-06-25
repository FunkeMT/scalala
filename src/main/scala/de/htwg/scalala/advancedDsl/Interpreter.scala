package de.htwg.scalala.advancedDsl

import java.io.File

import de.htwg.scalala.music.{ Instrument => MidiInstrument }
import de.htwg.scalala.midi.MidiFile

import scala.collection.mutable.ListBuffer

class Interpreter(song: Song) {

  val midiFile: MidiFile = new MidiFile()

  // Loop Map
  var loopListBuff = ListBuffer[(NoteElements, MidiInstrument)]()

  def run(): File = {
    walk(song.statements)
    midiFile.finalFile()
    midiFile.saveFile()
  }

  private def addTrack(track: Track): Unit = {

  }

  private def addMusician(musician: Musician, position: Int) = {
    val instr = musician.instrument.instrument

    midiFile.changeToInstrument(
      instr.instrumentID,
      instr.channelID,
      position
    )

    musician.musicElement match {
      case noteElements: NoteElements => {
        addNoteElements(noteElements, instr)
      }
      case loopElement: LoopElement => {
        loopListBuff.append((loopElement.noteElements, instr))
      }
    }
  }

  private def addLoopElements() = {
    val maxTick = midiFile.tickMap.maxBy(_._2)._2

    loopListBuff.toList.foreach(elem => {
      // start from track begin
      midiFile.changeToInstrument(elem._2.instrumentID, elem._2.channelID, 0)

      while (midiFile.tickMap(elem._2.channelID) <= maxTick) {
        addNoteElements(elem._1, elem._2)
      }
    })
  }

  private def addNoteElements(noteElements: NoteElements, instrument: MidiInstrument) = {
    noteElements.elements.foreach(el => {
      el match {
        case note: Note => midiFile.addKey(note.note, instrument.channelID)
        case chord: Chord => midiFile.addChord(chord.notes.map(_.note), instrument.channelID)
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
