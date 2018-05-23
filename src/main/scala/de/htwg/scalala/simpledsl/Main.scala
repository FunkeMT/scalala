package de.htwg.scalala.simpledsl

object Main {
  def main(args: Array[String]) {

    val in =
      """SONG:
        | PLAY Piano NOTES c,d,e
      """.stripMargin

    val parser = new Reader
    parser.parseAll(parser.song, in) match {
      case parser.Success(r, n) => {
        val interpreter = new Interpreter(r)
        try {
          interpreter.run
        } catch {
          case e: RuntimeException => println(e.getMessage)
        }
      }
      case parser.Error(msg, n) => println("Error: " + msg)
      case parser.Failure(msg, n) => println("Error: " + msg)
      case _ =>
    }
  }
}
