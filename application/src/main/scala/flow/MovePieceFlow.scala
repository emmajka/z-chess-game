package flow

import model.{PieceCoordinates, PieceType}
import zio.Task

trait MovePieceFlow {
  def run(gameId: String, pieceId: Int, pieceType: PieceType,  pieceMoveToCoordinates: PieceCoordinates): Task[Unit]
}
