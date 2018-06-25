package de.htwg.scalala.advancedDsl

object Main {
  def main(args: Array[String]) {

    val in =
      """musician piano_1
        |  instrument Piano
        |  plays d,e,f,g,d,e,f,g,d,e,f,g,a,b,d,e,f,c
        |
        |musician marimba_1
        |  instrument Marimba
        |  plays chord(c,d,e)
        |
        |musician guitar_1
        |  instrument Guitar
        |  plays loop(a,b)
        |
        |musician drummer_1
        |  instrument Drum
        |  plays loop(HiHatClosed)
        |
        |play with tempo 50
        |  piano_1,
        |  marimba_1 at 12,
        |  marimba_1 at 7,
        |  guitar_1,
        |  drummer_1
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
