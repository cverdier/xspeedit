name := "xspeedit"
version := "1.0"
scalaVersion := "2.12.6"

lazy val akkaVersion = "2.5.12"

mainClass in (Compile, run) := Some("net.xspeedit.Main")

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.6",

  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
)
