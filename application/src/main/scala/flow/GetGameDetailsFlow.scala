package flow

import flow.impl.GetGameDetailsResult
import zio.Task

trait GetGameDetailsFlow {
  def run(gameId: String): Task[GetGameDetailsResult]
}
