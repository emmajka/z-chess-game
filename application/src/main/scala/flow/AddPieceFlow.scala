package flow

import model.{PieceType, PieceCoordinates}
import zio.Task

trait AddPieceFlow {
  def run(gameId: String, pieceType: PieceType, pieceCoordinates: PieceCoordinates): Task[Unit]
}
