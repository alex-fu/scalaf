import com.trueaccord.scalapb.{ ScalaPbPlugin => PB }

val scalazV = "7.2.1"
val akkaV = "2.4.14"
val akkaHttpV = "10.0.0"

val buildResolvers = resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  "typesafe.com" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "m" at "http://central.maven.org/maven2"
)

lazy val root = (project in file(".")).settings(
  organization := "com.github.fyfhust",
  name := "scalaf",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.8",

  initialCommands := s"""println("Hi, initialCommands")""",

  libraryDependencies ++= Seq(
    "org.scalaz"              %% "scalaz-core"          % scalazV,
    "com.typesafe.akka"       %% "akka-slf4j"           % akkaV,
    "com.typesafe.akka"       %% "akka-actor"           % akkaV,
    "com.typesafe.akka"       %% "akka-http-core"       % akkaHttpV,
    "com.typesafe.akka"       %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka"       %% "akka-http"            % akkaHttpV,
    "com.trueaccord.scalapb"  %% "scalapb-runtime"      % "0.5.31",
    "org.scalactic" %% "scalactic" % "2.2.6",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
).settings(buildResolvers: _*)
 .settings(PB.protobufSettings: _*)
 .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
