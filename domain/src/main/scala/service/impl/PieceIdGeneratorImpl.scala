package service.impl

import model.GamePiecesDetails
import service.PieceIdGenerator
import zio.ZLayer

case class PieceIdGeneratorImpl() extends PieceIdGenerator {
  override def generate(pieces: Seq[GamePiecesDetails]): Int = pieces.maxByOption(_.pieceId).map(_.pieceId + 1).getOrElse(1)
}

object PieceIdGeneratorImpl {
  lazy val live = ZLayer.derive[PieceIdGeneratorImpl]
}
