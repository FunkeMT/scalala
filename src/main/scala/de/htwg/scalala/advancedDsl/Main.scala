package de.htwg.scalala.advancedDsl

object Main {
  def main(args: Array[String]) {

    val in =
      """PLAY piano_1, piano_2 AT 30 WITH TEMPO 80
        |
        |  MUSICIAN piano_1
        |    INSTRUMENT Piano
        |    PLAYS d,e,f,g,CHORD(c,d)
        |
        |  MUSICIAN piano_2
        |    INSTRUMENT Piano
        |    PLAYS CHORD(a,b)
      """.stripMargin

    val parser = new Reader
    parser.parseAll(parser.song, in) match {
      case parser.Success(r, n) => {
        println("Success:")
        println(r)
      }
      case parser.Error(msg, n) => println("Error: " + msg)
      case parser.Failure(msg, n) => println("Error: " + msg)
      case _ =>
    }
  }
}
