lazy val core = (project in file("core"))
  .settings(
    name := "core",
    scalaVersion := "2.12.6"
  )
  .dependsOn(macros)

lazy val macros = (project in file("macros"))
  .settings(
    name := "macros",
    scalaVersion := "2.12.6",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )
