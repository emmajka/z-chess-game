package model

enum PieceType(val code: String){
  case Pawn extends PieceType("P")
  case Bishop extends PieceType("B")
}

object PieceType{
  private val codeMap: Map[String, PieceType] = PieceType.values.map(v => (v.code, v)).toMap
  def fromCode(code: String): PieceType = codeMap(code)
}