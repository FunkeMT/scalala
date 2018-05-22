package de.htwg.scalala.simpledsl

import scala.language.postfixOps
import scala.util.parsing.combinator.syntactical._

class Reader extends StandardTokenParsers {
  lexical.reserved += ("PLAY", "NOTES", "SONG")
  lexical.delimiters += (",", ":")

  def instrument: Parser[Instrument] = ident ^^ {
    case i => new Instrument(i)
  }

  def note: Parser[Note] = ident ^^ {
    case i => new Note(i.charAt(0))
  }

  def playCommand: Parser[Instrument] = "PLAY" ~> instrument ^^ {
    i => i
  }

  def noteCommand: Parser[List[Note]] = "NOTES" ~> repsep(note, ",") ^^ {
    _ toList
  }

  def line: Parser[Line] = playCommand ~ noteCommand ^^ {
    case p ~ n => new Line(p, n)
  }

  def song: Parser[Song] = ("SONG" ~ ":") ~ line ^^ {
    case l => new Song(l._2)
  }


  def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
    phrase(p)(new lexical.Scanner(in))
  }
}
