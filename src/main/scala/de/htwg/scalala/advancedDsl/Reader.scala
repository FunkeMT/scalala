package de.htwg.scalala.advancedDsl

import scala.language.postfixOps
import scala.util.parsing.combinator.syntactical._

class Reader extends StandardTokenParsers {
  lexical.reserved += ("LOOP",
    "PATTERN",
    "CHORD",
    "NOTES",
    "INSTRUMENT",
    "MUSICIAN",
    "TOGETHER",
    "WITH",
    "PLAY",
    "PLAYS",
    "TEMPO",
    "AT"
  )
  lexical.delimiters += (",", "(", ")")


  def song: Parser[List[Statement]] = rep(track | musician) ^^ {
    s => s
  }

  def track: Parser[Track] = "PLAY" ~ musicVars ~ opt(tempo) ^^ {
    case p ~ m ~ None => new Track(m)
    case p ~ m ~ t    => new Track(m, t.get)
  }

  def tempo: Parser[Int] = "WITH" ~> "TEMPO" ~> numericLit ^^ {
    n => n.toInt
  }

  def musicVars: Parser[List[MusicVar]] = repsep(musicVar, ",") ^^ {
    _ toList
  }

  def musicVar: Parser[MusicVar] = ident ~ opt(playAt) ^^ {
    case i ~ None => new MusicVar(i)
    case i ~ a    => new MusicVar(i, a.get)
  }

  def playAt: Parser[Int] = "AT" ~> numericLit ^^ {
    n => n.toInt
  }





  def musician: Parser[Musician] = ("MUSICIAN" ~> ident) ~ ("INSTRUMENT" ~> instrument) ~ ("PLAYS" ~> noteElements | loopElements) ^^ {
    case m ~ i ~ e => new Musician(m, i, e)
  }

  def instrument: Parser[Instrument] = ident ^^ {
    case i => new Instrument(i)
  }



  def noteElements: Parser[NoteElements] = repsep(noteElement, ",") ^^ {
    case e => new NoteElements(e)
  }

  def noteElement: Parser[NoteElement] = chord | note ^^ {
    e => e
  }


  
  def loopElements: Parser[LoopElement] = ("LOOP" ~> "(") ~> noteElements <~ ")" ^^ {
    case l => new LoopElement(l)
  }



  def chord: Parser[Chord] = ("CHORD" ~> "(") ~> repsep(note, ",") <~ ")" ^^ {
    case n => new Chord(n)
  }

  def note: Parser[Note] = ident ^^ {
    case i => new Note(i.charAt(0))
  }



  def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
    phrase(p)(new lexical.Scanner(in))
  }
}
