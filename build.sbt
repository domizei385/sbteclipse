lazy val root = (project in file("."))
  .enablePlugins(GitVersioning, SbtPlugin)
  .settings(
    versionWithGit ++
    sbtrelease.ReleasePlugin.projectSettings ++
    Seq(
      organization := "com.typesafe.sbteclipse",
      name := "sbteclipse-plugin",
      sbtPlugin := true,
      isSnapshot := false,
      sbtVersion in GlobalScope := {
        System.getProperty("sbt.build.version", (sbtVersion in GlobalScope).value)
      },
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-target:jvm-1.8"),
      scalaVersion := "2.12.13",
      libraryDependencies ++= Seq(
        "org.scala-lang.modules" %% "scala-xml"     % "1.3.0",
        "org.scalaz"             %% "scalaz-core"   % "7.2.14",
        "org.scalaz"             %% "scalaz-effect" % "7.2.14",
        "org.scalatest"          %% "scalatest"     % "3.2.3" % "test"
      ),
      publishTo := {
        val repo = "https://maven.zalando.net/"
        if (isSnapshot.value) {
          Some("snapshots" at repo + "content/repositories/snapshots")
        }
        else {
          Some("releases" at repo + "content/repositories/releases")
        }
      },
      publishConfiguration := publishConfiguration.value.withOverwrite(true),
      publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
      publishMavenStyle := true,
      publishArtifact in Test := false,
      pomIncludeRepository := { _ => false },
      licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in (Compile, packageSrc) := false,
      scriptedBufferLog := false,
      scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    )
  )
