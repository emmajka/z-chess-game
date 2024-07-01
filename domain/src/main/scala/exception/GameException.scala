package exception

import model.Position

enum GameException(val msg: String) extends Exception {
  case GameNotExists(gameId: String) extends GameException(s"Game with ID [$gameId] does not exists")
  case PieceNotExists(gameId: String, pieceId: Int) extends GameException(s"Game with ID [$gameId] does not have an active piece with ID [$pieceId]")
  case PiecePlaceTaken(gameId: String, pieceId: Int, pieceCoordinates: Position)
      extends GameException(s"Game with ID [$gameId] has an active piece [$pieceId] on position ${pieceCoordinates.x}:${pieceCoordinates.y}")
  case UnsupportedPawnMoveDirection extends GameException(s"Pawn can only move in straight lines")
  case UnsupportedBishopMoveDirection extends GameException(s"Bishop can only move diagonally")
  case ObstacleDuringMove extends GameException(s"Unable to move game piece, obstacle detected on the way")
}
