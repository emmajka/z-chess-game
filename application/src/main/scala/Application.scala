import http.Http4sServer
import http.route.impl.{ChessGameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{ChessGameRoute, HealthRoute, HttpRoute}
import zio.{Fiber, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object Application extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {

    (for _ <- ZIO.log("hello world")
         routes <- getAllRoutes
         _ <- ZIO.logInfo("Setting up http server...")
         f1 <- Http4sServer.run(routes = routes, 8081).fork
         _ <- ZIO.logInfo("Http server up & running")
         _ <- Fiber.collectAll(List(f1)).join
    yield ()
      ).provide()
  }

  private def getAllRoutes = {
    for healthRoute <- ZIO.service[HealthRoute]
        chessRoute <- ZIO.service[ChessGameRoute]
        appRoutes: Seq[HttpRoute] = Seq(healthRoute, chessRoute)
        openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, openApiRoute)
  }.provide(HealthRouteImpl.layer, ChessGameRouteImpl.layer)
}
