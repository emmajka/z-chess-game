import cfg.{ConfigProvider, HttpConfig}
import flow.impl.{AddPieceFlowImpl, InitializeGameFlowImpl, RetrieveGameDetailsFlowImpl}
import http.Http4sServer
import http.handler.impl.{ChessGameAdminHandlerImpl, ChessGameHandlerImpl}
import http.route.impl.{ChessGameAdminRouteImpl, ChessGameRouteImpl, HealthRouteImpl, OpenApiRouteImpl}
import http.route.{ChessGameAdminRoute, ChessGameRoute, HealthRoute, HttpRoute}
import mysql.{MysqlConnection, MysqlCtx}
import repository.impl.ChessGameRepositoryImpl
import service.impl.{GameIdGeneratorImpl, PieceIdCreatorImpl, PieceIdGeneratorImpl}
import validator.impl.GamePieceValidatorImpl
import zio.{Fiber, ZIO, ZIOAppArgs, ZIOAppDefault, ZLayer}

object Application extends ZIOAppDefault {

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = ConfigProvider.hocon

  override def run: ZIO[ZIOAppArgs, Any, Any] = {

    for _          <- ZIO.log("hello world")
    routes         <- getAllRoutes
    httpServerPort <- ZIO.serviceWith[HttpConfig](_.port)
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
    ChessGameHandlerImpl.live,
    ChessGameRepositoryImpl.live,
    InitializeGameFlowImpl.live,
    RetrieveGameDetailsFlowImpl.live,
    AddPieceFlowImpl.live,
    GameIdGeneratorImpl.live,
    PieceIdGeneratorImpl.live,
    GamePieceValidatorImpl.live,
    PieceIdCreatorImpl.live,
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
