package http.request

import model.{PieceType, Position}
import sttp.tapir.Schema

case class AddPieceRequest(pieceType: PieceType, targetPosition: Position)

object AddPieceRequest {
  import http.serde.PieceType.given
  import http.serde.Position.given
  import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

  given JsonDecoder[AddPieceRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[AddPieceRequest] = DeriveJsonEncoder.gen
  given Schema[AddPieceRequest] = Schema.derived
}
