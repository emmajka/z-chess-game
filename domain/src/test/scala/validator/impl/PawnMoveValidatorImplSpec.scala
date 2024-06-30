package validator.impl

import exception.GameException
import model.Position
import zio.test.*

object PawnMoveValidatorImplSpec extends ZIOSpecDefault {
  val sut = PawnMoveValidatorImpl()

  val spec = suiteAll("PawnMoveValidatorImpl tests") {
    val testData = Seq(
      ((Position(1, 1), Position(1, 3), Position(4, 4)), Right(())),
      ((Position(1, 1), Position(1, 4), Position(2, 2)), Right(())),
      ((Position(6, 6), Position(6, 1), Position(2, 7)), Right(())),
      ((Position(6, 1), Position(6, 6), Position(2, 7)), Right(())),
      ((Position(6, 6), Position(6, 1), Position(6, 2)), Left(GameException.ObstacleDuringMove)),
      ((Position(6, 1), Position(6, 6), Position(6, 2)), Left(GameException.ObstacleDuringMove)),
      ((Position(1, 1), Position(1, 3), Position(1, 2)), Left(GameException.ObstacleDuringMove)),
      ((Position(1, 3), Position(1, 1), Position(1, 2)), Left(GameException.ObstacleDuringMove)),
      ((Position(1, 1), Position(3, 3), Position(4, 4)), Left(GameException.UnsupportedPawnMoveDirection)),
      ((Position(4, 4), Position(2, 3), Position(5, 6)), Left(GameException.UnsupportedPawnMoveDirection))
    )
    test("when an obstacle is detected during a move or move is unsupported for pawn type then an exception should be returned") {
      assertTrue {
        testData.forall { case ((from, to, existing), expected) => sut.validate(from = from, to = to, existing = existing) == expected }
      }
    }
  }
}
