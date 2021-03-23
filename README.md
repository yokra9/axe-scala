# axe-scala

Very small example to use [axe-core-maven-html](https://github.com/dequelabs/axe-core-maven-html) in Scala.

## How to use

1. clone `axe-core-maven-html` and `mvn install`.
2. fix version of `com.deque.html.axe-core.selenium` at `buildt.sbt`.
3. just `sbt run` !

## Run as docker container

1. `sbt docker:publishLocal` to build docker image.
2. just `docker run -it --rm axe-scala` !
