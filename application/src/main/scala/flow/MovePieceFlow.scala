package flow

import model.{Position, PieceType}
import zio.Task

trait MovePieceFlow {
  def run(gameId: String, pieceId: Int, pieceType: PieceType,  pieceMoveToCoordinates: Position): Task[Unit]
}
