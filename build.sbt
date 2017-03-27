val Scala211 = "2.11.8"

lazy val root = (project in file(".")).
  settings(
    name := "formatConverter",
    scalaVersion := Scala211,
    crossScalaVersions := "2.12.1" :: Scala211 :: "2.10.6" :: Nil,
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xlint"
    ),
    releaseCrossBuild := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "com.typesafe.play" %% "play-json" % "2.6.0-M6",
      "net.jcazevedo" %% "moultingyaml" % "0.4.0"
    )
  )

