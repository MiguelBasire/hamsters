name := "hamsters"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
   "io.github.scala-hamsters" %% "hamsters" % "1.0.0-BETA1",
   "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

resolvers += Resolver.url("github repo for html5tags", url("http://scala-hamsters.github.io/hamsters/releases/"))(Resolver.ivyStylePatterns)

