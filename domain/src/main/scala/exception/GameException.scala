package exception

import model.PieceCoordinates

enum GameException(msg: String) extends Exception {
  case GameNotExists(gameId: String) extends GameException(s"Game with ID [$gameId] does not exists")
  case PieceNotExists(gameId: String, pieceId: Int) extends GameException(s"Game with ID [$gameId] does not have a piece with ID [$pieceId]")
  case PiecePlaceTaken(gameId: String, pieceId: Int, pieceCoordinates: PieceCoordinates)
      extends GameException(s"Game with ID [$gameId] has an active piece [$pieceId] on position ${pieceCoordinates.x}:${pieceCoordinates.y}")
  case PawnNotStraightMove extends GameException(s"Pawn can only move in straight lines")
  case ObstacleDuringMove extends GameException(s"Unable to move game piece, obstacle detected on the way")
}
