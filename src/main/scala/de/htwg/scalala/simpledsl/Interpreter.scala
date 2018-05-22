package de.htwg.scalala.simpledsl

class Interpreter(song: Song) {
  def run() {
    walk(song.line)
  }

  private def walk(tree: Line) {
    if (tree != null) {
      println("Instrument: " + tree.instrument)
      println("Notes:" + tree.notes)
    }
  }
}
