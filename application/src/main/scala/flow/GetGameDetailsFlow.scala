package flow

import zio.Task

trait GetGameDetailsFlow {
  def run(gameId: String): Task[String]
}
