package de.htwg.scalala.advancedDsl

object Main {
  def main(args: Array[String]) {

    val in =
      """MUSICIAN piano_1
        |  INSTRUMENT Piano
        |  PLAYS d,e,f,g,d,e,f,g,d,e,f,g,a,b,d,e,f,c
        |
        |MUSICIAN marimba_1
        |  INSTRUMENT Marimba
        |  PLAYS CHORD(c,d,e)
        |
        |MUSICIAN guitar_1
        |  INSTRUMENT Guitar
        |  PLAYS LOOP(a,b)
        |
        |MUSICIAN drummer_1
        |  INSTRUMENT Drum
        |  PLAYS LOOP(HiHatClosed)
        |
        |PLAY piano_1, marimba_1 AT 12, marimba_1 AT 7, guitar_1, drummer_1 WITH TEMPO 50
      """.stripMargin

    val in2 =
      """MUSICIAN guitar_1
        |  INSTRUMENT Guitar
        |  PLAYS LOOP(a,b)
        |
        |PLAY guitar_1 WITH TEMPO 50
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
