import com.trueaccord.scalapb.{ ScalaPbPlugin => PB }

val scalazV = "7.2.1"
val akkaV = "2.4.7"

val buildResolvers = resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  "typesafe.com" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "m" at "http://central.maven.org/maven2"
)

lazy val root = (project in file(".")).settings(
  organization := "com.github.fyfhust",
  name := "scalaf",
  version := "1.0",
  scalaVersion := "2.11.8",

  initialCommands := s"""println("Hi, initialCommands")""",

  libraryDependencies ++= Seq(
    "org.apache.derby"        % "derby"                 % "10.4.1.3",
    "org.scalaz"              %% "scalaz-core"          % scalazV,
    "com.typesafe.akka"       %% "akka-slf4j"           % akkaV,
    "com.typesafe.akka"       %% "akka-actor"           % akkaV,
    "com.typesafe.akka"       %% "akka-cluster"         % akkaV,
    "com.typesafe.akka"       %% "akka-cluster-tools"   % akkaV,
    "com.typesafe.akka"       %% "akka-cluster-metrics" % akkaV,
    "com.typesafe.akka"       %% "akka-contrib"         % akkaV,
    "com.typesafe.akka"       %% "akka-http-core"       % akkaV,
    "com.typesafe.akka"       %% "akka-http-spray-json-experimental"      % akkaV,
    "com.typesafe.akka"       %% "akka-http-experimental"                 % akkaV,
    "com.trueaccord.scalapb"  %% "scalapb-runtime"      % "0.4.20"
  )
).settings(buildResolvers: _*)
 .settings(PB.protobufSettings: _*)
 .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
