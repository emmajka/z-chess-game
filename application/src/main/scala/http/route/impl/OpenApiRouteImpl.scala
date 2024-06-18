package http.route.impl

import http.route.{HttpRoute, OpenApiRoute}
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.swagger.SwaggerUI
import sttp.tapir.ztapir.*

case class OpenApiRouteImpl(httpRoutes: Seq[HttpRoute]) extends OpenApiRoute {
  override def routes: Seq[ZServerEndpoint[Any, Any]] = {
    val endpoints = httpRoutes
      .flatMap(_.routes)
      .map(_.endpoint)
      .toList
    SwaggerUI(
      OpenAPIDocsInterpreter().toOpenAPI(endpoints, "chess-like game public API", "0.0.1").toYaml
    )
  }
}
