package de.htwg.scalala.advancedDsl

object Main {
  def main(args: Array[String]) {

    val in =
      """musician piano_1
        |  instrument Piano
        |  plays d,e,f,g,d,e,f,g,d,e,f,g,a,h,d,e,f,c
        |
        |musician marimba_1
        |  instrument Marimba
        |  plays loop(c)
        |
        |musician drummer
        |  instrument Drum
        |  plays loop(HiHatClosed)
        |
        |play with tempo 50
        |  piano_1,
        |  marimba_1,
        |  drummer
      """.stripMargin

    val in2 =
      """musician piano_left
        |  instrument Piano
        |  plays d,a,h,a,d,a,d,a,h,d,h,d,a
        |
        |musician piano_right
        |  instrument Piano
        |  plays d/16,e/16,f.sharp,a,f.sharp,e/8,d.dot / 2,f.sharp,a/8,chord(d,g,h),d/8,c,a,chord(a,d/2,c.sharp / 2)
        |
        |play with tempo 50
        |  piano_left,
        |  piano_right
      """.stripMargin

    val in3 =
      """musician piano_1
        |  instrument Piano
        |  plays c,c.sharp,c.flat,c.dot,c/2,c/4,c/8,c/16, c.sharp/2,chord(c.sharp,d.flat),c,+c,-c,++c,--c
        |
        |play with tempo 30
        |   piano_1
      """.stripMargin

    parse(in)
    //autoComplete(in3)
  }

  def parse(inputStr: String) = {
    val parser = new Reader
    parser.parseAll(parser.song, inputStr) match {
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

  def autoComplete(inputStr: String) = {
    val parserAC = new ReaderAutoComplete
    val autoComp = parserAC.autoCompletion(parserAC.song, inputStr)
    println(autoComp)
  }
}
