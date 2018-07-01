package de.htwg.scalala.advancedDsl

import scala.language.postfixOps
import scala.util.parsing.combinator.syntactical._

class Reader extends StandardTokenParsers {
  lexical.reserved += ("loop",
    "chord",
    "notes",
    "instrument",
    "musician",
    "with",
    "play",
    "plays",
    "tempo",
    "at",
    "c", "d", "e", "f", "g", "a", "h",
    "2", "4", "8", "16",
    "sharp", "flat", "dot",
    "HiHatClosed")
  lexical.delimiters += (",", "(", ")", "/", ".", "+", "-")

  def song: Parser[Song] = rep(musician) ~ track ^^ {
    case m ~ t => new Song(m, t)
  }

  def musician: Parser[Musician] = ("musician" ~> ident) ~ ("instrument" ~> instrument) ~ ("plays" ~> musicElement) ^^ {
    case m ~ i ~ e => new Musician(m, i, e)
  }

  def instrument: Parser[Instrument] = ident ^^ {
    case i => new Instrument(i)
  }

  def musicElement: Parser[MusicElement] = (loopElements | noteElements) ^^ {
    e => e
  }

  def noteElements: Parser[NoteElements] = repsep(noteElement, ",") ^^ {
    case e => new NoteElements(e)
  }

  def loopElements: Parser[LoopElement] = ("loop" ~> "(") ~> noteElements <~ ")" ^^ {
    case l => new LoopElement(l)
  }

  def noteElement: Parser[NoteElement] = (note | chord) ^^ {
    e => e
  }

  def note: Parser[Note] = opt(octave) ~ ("c" | "d" | "e" | "f" | "g" | "a" | "h" | "HiHatClosed") ~ opt("." ~> accidental) ~ opt("/" ~> duration) ^^ {
    case o ~ n ~ a ~ d => new Note(n, a.getOrElse(""), d.getOrElse(0), o.getOrElse(List[String]()))
  }

  def octave: Parser[List[String]] = rep("+" | "-") ^^ {
    o => o
  }

  def accidental: Parser[String] = ("sharp" | "flat" | "dot") ^^ {
    a => a
  }

  def duration: Parser[Int] = numericLit ^^ {
    d => d.toInt
  }

  def chord: Parser[Chord] = ("chord" ~> "(") ~> repsep(note, ",") <~ ")" ^^ {
    case n => new Chord(n)
  }


  def track: Parser[Track] = "play" ~ opt(tempo) ~ musicVars ^^ {
    case p ~ None ~ m => new Track(m)
    case p ~ t ~ m => new Track(m, t.get)
  }

  def tempo: Parser[Int] = "with" ~> "tempo" ~> numericLit ^^ {
    n => n.toInt
  }

  def musicVars: Parser[List[MusicVar]] = repsep(musicVar, ",") ^^ {
    m => m
  }

  def musicVar: Parser[MusicVar] = ident ~ opt(playAt) ^^ {
    case i ~ None => new MusicVar(i)
    case i ~ a => new MusicVar(i, a.get)
  }

  def playAt: Parser[Int] = "at" ~> numericLit ^^ {
    n => n.toInt
  }


  def parseAll[T](p: Parser[T], in: String): ParseResult[T] = {
    phrase(p)(new lexical.Scanner(in))
  }
}
