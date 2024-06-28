import cfg.{ConfigProvider, HttpConfig}
import flow.impl.{GameInitializationFlowImpl, RetrieveGameDetailsFlowImpl}
import http.Http4sServer
import http.handler.impl.ChessGameAdminHandlerImpl
import http.route.impl.{ChessGameAdminRouteImpl, ChessGameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{ChessGameAdminRoute, ChessGameRoute, HealthRoute, HttpRoute}
import mysql.{MysqlConnection, MysqlCtx}
import repository.impl.GameChessRepositoryImpl
import service.impl.GameIdGeneratorImpl
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

  }.provide(
    HttpConfig.live,
    HealthRouteImpl.live,
    ChessGameRouteImpl.live,
    ChessGameAdminRouteImpl.live,
    ChessGameAdminHandlerImpl.live,
    GameChessRepositoryImpl.live,
    GameInitializationFlowImpl.live,
    RetrieveGameDetailsFlowImpl.live,
    GameIdGeneratorImpl.live,
    MysqlConnection.ctx,
    MysqlConnection.ds
  )

  private def getAllRoutes = {
    for healthRoute <- ZIO.service[HealthRoute]
    chessRoute      <- ZIO.service[ChessGameRoute]
    chessAdminRoute <- ZIO.service[ChessGameAdminRoute]
    appRoutes    = Seq(healthRoute, chessRoute, chessAdminRoute)
    openApiRoute = OpenApiRouteImpl(appRoutes)
    yield Seq(healthRoute, chessRoute, chessAdminRoute, openApiRoute)
  }
}
