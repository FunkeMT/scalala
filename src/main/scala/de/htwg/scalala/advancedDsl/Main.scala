package de.htwg.scalala.advancedDsl

import de.htwg.scalala.simpledsl.Interpreter

object Main {
  def main(args: Array[String]) {

    val in =
      """MUSICIAN piano_1
        |  INSTRUMENT Piano
        |  PLAYS d,e,f,g
        |
        |MUSICIAN piano_2
        |  INSTRUMENT Marimba
        |  PLAYS a
        |
        |PLAY piano_1, piano_2 AT 12, piano_2 AT 7 WITH TEMPO 50
      """.stripMargin

    val parser = new Reader
    parser.parseAll(parser.song, in) match {
      case parser.Success(r, n) => {
        println("Success:")
        println(r)

        val interpreter = new Interpreter(r)
        try {
          interpreter.run
        } catch {
          case e: RuntimeException => println("Error: " + e.printStackTrace())
        }
      }
      case parser.Error(msg, n) => println("Error: " + msg)
      case parser.Failure(msg, n) => println("Error: " + msg)
      case _ =>
    }
  }
}
