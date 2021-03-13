import scala.io
import org.openqa.selenium.chrome._
import com.deque.html.axecore.selenium._

object sample {

  def main(args: Array[String]): Unit = {

    val webDriver = new ChromeDriver(
      ChromeDriverService.createDefaultService,
      new ChromeOptions().addArguments(
        "--headless",
        "--disable-gpu",
        "--window-size=1920,1200",
        "--ignore-certificate-errors"
      )
    )

    print("Enter URL: ")
    webDriver.get(io.StdIn.readLine)

    val result = new AxeBuilder()
      .withTags(
        java.util.Arrays.asList(
          "wcag2a",
          "wcag2aa",
          "wcag21a",
          "wcag21aa",
          "experimental",
          "best-practice"
        )
      )
      .analyze(webDriver)

    val Violations =
      if (
        AxeReporter.getReadableAxeResults(
          "analyze",
          webDriver,
          result.getViolations
        )
      ) "***** Violations *****\n\n" + AxeReporter.getAxeResultString

    val Incomplete =
      if (
        AxeReporter.getReadableAxeResults(
          "analyze",
          webDriver,
          result.getIncomplete
        )
      ) "***** Incomplete *****\n\n" + AxeReporter.getAxeResultString

    println(s"$Violations\n\n$Incomplete")

    webDriver.close
    webDriver.quit
  }
}
