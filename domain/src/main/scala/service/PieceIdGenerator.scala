package service

import model.GamePiecesDetails

trait PieceIdGenerator {
  def generate(pieces: Seq[GamePiecesDetails]): Int
}
