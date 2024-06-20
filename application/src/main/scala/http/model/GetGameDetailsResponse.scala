package http.model

import sttp.tapir.Schema
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class GetGameDetailsResponse(gameId: String)

object GetGameDetailsResponse {
   given JsonEncoder[GetGameDetailsResponse] = DeriveJsonEncoder.gen
   given JsonDecoder[GetGameDetailsResponse] = DeriveJsonDecoder.gen
   given Schema[GetGameDetailsResponse] = Schema.derived
}