package de.htwg.scalala.advancedDsl

import java.io.File

import de.htwg.scalala.midi.MidiFile

class Interpreter(song: Song) {

  val midiFile: MidiFile = new MidiFile()

  def run(): Unit = {
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

    if (musician.musicElement.isInstanceOf[NoteElements]) {
      val noteElements = musician.musicElement.asInstanceOf[NoteElements]

      noteElements.elements.foreach(el => {
        if (el.isInstanceOf[Note]) {
          val note = el.asInstanceOf[Note]
          midiFile.addKey(note.note, instr.channelID)
        }
      })
    }
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
