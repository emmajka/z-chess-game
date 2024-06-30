package http.request

import model.Position
import sttp.tapir.Schema

case class MovePieceRequest(targetPosition: Position)

object MovePieceRequest {
  import http.serde.Position.given
  import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

  given JsonDecoder[MovePieceRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[MovePieceRequest] = DeriveJsonEncoder.gen
  given Schema[MovePieceRequest] = Schema.derived
}
