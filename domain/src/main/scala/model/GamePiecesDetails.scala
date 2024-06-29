package model

case class GamePiecesDetails(
  pieceId: Int,
  pieceType: PieceType,
  xCoordinate: Int,
  yCoordinate: Int,
  active: Boolean)
