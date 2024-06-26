package flow

import model.{Position, PieceType}
import zio.Task

trait AddPieceFlow {
  def run(gameId: String, pieceType: PieceType, targetPosition: Position): Task[Int]
}
