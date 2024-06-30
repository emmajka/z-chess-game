package validator

import model.Position

trait PawnObstacleDetector {
  def detect(from: Position, to: Position, existing: Position): Boolean
}
