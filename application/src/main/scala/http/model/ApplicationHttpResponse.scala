package http.model

import sttp.tapir.Schema
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class ApplicationHttpResponse [T](body: T)

object ApplicationHttpResponse {
  given[T] (using JsonEncoder[T]): JsonEncoder[ApplicationHttpResponse[T]] = DeriveJsonEncoder.gen
  given[T] (using JsonDecoder[T]): JsonDecoder[ApplicationHttpResponse[T]] = DeriveJsonDecoder.gen
  given[T] (using Schema[T]): Schema[ApplicationHttpResponse[T]] = Schema.derived[ApplicationHttpResponse[T]]
}
