import scala.io
import scala.util.Try
import scala.util.Failure
import scala.util.Success
import org.openqa.selenium.chrome._
import com.deque.html.axecore.selenium._

object sample {

  def main(args: Array[String]): Unit = {

    val webDriver = new ChromeDriver(
      ChromeDriverService.createDefaultService,
      new ChromeOptions().addArguments(
        "--headless",
        "--disable-gpu",
        "--no-sandbox",
        "--window-size=1920,1200",
        "--ignore-certificate-errors"
      )
    )

    print("Enter URL: ")
    analyze(io.StdIn.readLine, webDriver) match {
      case Success((Some(violations), Some(incomplete))) =>
        println(
          s"***** Violations *****\n\n$violations\n\n***** Incomplete *****\n\n$incomplete"
        )
      case Success((Some(violations), None)) =>
        println(
          s"***** Violations *****\n\n$violations\n\nIncomplete was not detected."
        )
      case Success((None, Some(incomplete))) =>
        println(
          s"Violations was not detected.\n\n***** Incomplete *****\n\n$incomplete"
        )
      case Success((None, None)) => println("Violations and Incomplete were not detected.")
      case Failure(exception)    => println(exception)
    }

    webDriver.close
    webDriver.quit
  }

  def analyze(
      url: String,
      webDriver: ChromeDriver
  ): Try[(Option[String], Option[String])] = Try {

    webDriver.get(url)

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
      .withoutIframeSandboxes()
      .analyze(webDriver)

    val violations =
      if (
        AxeReporter.getReadableAxeResults(
          "analyze",
          webDriver,
          result.getViolations
        )
      ) Some(AxeReporter.getAxeResultString)
      else None

    val incomplete =
      if (
        AxeReporter.getReadableAxeResults(
          "analyze",
          webDriver,
          result.getIncomplete
        )
      ) Some(AxeReporter.getAxeResultString)
      else None

    (violations, incomplete)
  }
}
