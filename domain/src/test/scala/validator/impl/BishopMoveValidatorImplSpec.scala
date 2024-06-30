package validator.impl

import exception.GameException
import exception.GameException.{ObstacleDuringMove, UnsupportedBishopMoveDirection}
import model.Position
import zio.test.*

object BishopMoveValidatorImplSpec extends ZIOSpecDefault {
  val sut = BishopMoveValidatorImpl()

  val spec = suiteAll("BishopMoveValidatorImpl tests") {
    val testData = Seq(
      ((Position(1, 1), Position(3, 3), Position(4, 4)), Right(())),
      ((Position(3, 3), Position(1, 1), Position(4, 4)), Right(())),
      ((Position(3, 3), Position(1, 5), Position(2, 2)), Right(())),
      ((Position(5, 1), Position(3, 3), Position(4, 4)), Right(())),
      ((Position(6, 6), Position(1, 1), Position(3, 3)), Left(ObstacleDuringMove)),
      ((Position(1, 1), Position(6, 6), Position(3, 3)), Left(ObstacleDuringMove)),
      ((Position(1, 1), Position(1, 3), Position(4, 4)), Left(UnsupportedBishopMoveDirection)),
      ((Position(2, 4), Position(5, 4), Position(6, 6)), Left(UnsupportedBishopMoveDirection))
    )
    test("when an obstacle is detected during a move or move is unsupported for piece type then an exception should be returned") {
      assertTrue {
        testData.forall { case ((from, to, existing), expected) => sut.validate(from = from, to = to, existing = existing) == expected }
      }
    }
  }
}
