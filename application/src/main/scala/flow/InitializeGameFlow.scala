package flow

import zio.Task

trait InitializeGameFlow {
  def run(): Task[Unit]
}
