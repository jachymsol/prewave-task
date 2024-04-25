name := "prewave"

version := "0.1"

scalaVersion := "3.4.1"

val circeVersion = "0.14.1"

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.9.5"
libraryDependencies += "com.softwaremill.sttp.client3" %% "circe" % "3.9.5"

libraryDependencies ++= Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser"
).map(_ % circeVersion)

lazy val root = project in file(".")
