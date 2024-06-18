ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val application = (project in file("application"))
  .settings(
    name := "application",
    libraryDependencies ++= Dependencies.application
  )

lazy val client = (project in file("client"))
  .settings(
    name := "client",
    libraryDependencies ++= Dependencies.client
  )

lazy val root = (project in file("."))
  .aggregate(application)
  .settings(
    name := "recruitment-task-beone",
    run := (application / Compile / run).evaluated,
    Global / cancelable := false
  )
