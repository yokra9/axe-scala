import com.typesafe.sbt.packager.docker._

/* add axe-core-maven-html from local repository */
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.deque.html.axe-core" % "selenium" % "4.1.1"
)

/* settings for docker plugin */
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

packageName in Docker := "axe-scala"
version in Docker := "latest"

// add latest chrome & chromedriver to docker image
dockerCommands ++= Seq(
  Cmd("USER", "root"),
  Cmd(
    "RUN",
    """cd /tmp/ && \
    apt update && \
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome-stable_current_amd64.deb && \
    wget https://chromedriver.storage.googleapis.com/$(curl https://chromedriver.storage.googleapis.com/LATEST_RELEASE)/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip -d /bin/ && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*"""
  ),
  Cmd("USER", "1001:0"),
)
