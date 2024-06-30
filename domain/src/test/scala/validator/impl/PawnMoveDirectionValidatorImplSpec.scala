package validator.impl

import model.Position
import zio.*
import zio.test.*

object PawnMoveDirectionValidatorImplSpec extends ZIOSpecDefault {
  val sut = PawnMoveDirectionValidatorImpl()

  val spec = suiteAll("PawnMoveDirectionValidator tests") {
    val testData = Seq(
      ((Position(1, 1), Position(1, 2)), true),
      ((Position(2, 1), Position(1, 1)), true),
      ((Position(3, 6), Position(6, 6)), true),
      ((Position(5, 2), Position(5, 4)), true),
      ((Position(5, 2), Position(3, 4)), false),
      ((Position(1, 2), Position(3, 4)), false),
      ((Position(1, 1), Position(2, 2)), false)
    )

    test("when pawn's movement is in straight line then it should return true") {
      assertTrue {
        testData.forall {
          case ((from, to), expected) =>
            sut.validate(from = from, to = to) == expected
        }
      }
    }
  }

}
