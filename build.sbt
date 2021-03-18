resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.deque.html.axe-core" % "selenium" % "4.1.1",
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

packageName in Docker := "axe-scala"
version in Docker := "latest"