import cfg.{ConfigProvider, HttpConfig}
import http.Http4sServer
import http.route.impl.{ChessGameAdminRouteImpl, ChessGameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{ChessGameAdminRoute, ChessGameRoute, HealthRoute, HttpRoute}
import zio.{Fiber, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Application extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = ConfigProvider.hocon

  override def run: ZIO[ZIOAppArgs, Any, Any] = {

    for _          <- ZIO.log("hello world")
    routes         <- getAllRoutes
    httpServerPort <- ZIO.service[HttpConfig].map(_.port)
    _              <- ZIO.logInfo(s"Setting up http server on port $httpServerPort...")
    f1             <- Http4sServer.run(routes = routes, httpServerPort).fork
    _              <- ZIO.logInfo("Http server up & running")
    _              <- Fiber.collectAll(List(f1)).join
    yield ()

  }.provide(HttpConfig.live)

  private def getAllRoutes = {
    for healthRoute <- ZIO.service[HealthRoute]
    chessRoute      <- ZIO.service[ChessGameRoute]
    chessAdminRoute <- ZIO.service[ChessGameAdminRoute]
    appRoutes    = Seq(healthRoute, chessRoute, chessAdminRoute)
    openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, openApiRoute)
  }.provide(HealthRouteImpl.live, ChessGameRouteImpl.live, ChessGameAdminRouteImpl.live)
}
