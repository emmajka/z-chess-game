package model

enum ChessPieceType (val code: String){
  case Pawn extends ChessPieceType("P")
  case Bishop extends ChessPieceType("B")
}

object ChessPieceType{
  private val codeMap: Map[String, ChessPieceType] = ChessPieceType.values.map(v => (v.code, v)).toMap
  def fromCode(code: String): ChessPieceType = codeMap(code)
}