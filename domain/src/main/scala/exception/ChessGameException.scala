package exception

import model.PieceCoordinates

enum ChessGameException(msg: String) extends Exception {
  case ChessGameNotExists(gameId: String) extends ChessGameException(s"Chess game with ID [$gameId] does not exists")
  case ChessPieceDoesNotExists(gameId: String, pieceId: Int) extends ChessGameException(s"Chess game with ID [$gameId] does not have a piece with ID [$pieceId]")
  case ChessPiecePlaceTaken(gameId: String, pieceId: Int, pieceCoordinates: PieceCoordinates)
      extends ChessGameException(s"Chess game with ID [$gameId] has an active piece [$pieceId] on position ${pieceCoordinates.x}:${pieceCoordinates.y}")
}
