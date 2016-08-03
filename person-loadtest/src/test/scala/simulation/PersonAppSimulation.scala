package simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory
import _root_.scenario.{CreatePersonScenario, ListOfPersonsScenario}

/**
  * Load test simulation for SmartSite.
  */
class PersonAppSimulation extends Simulation {

  val logger = LoggerFactory.getLogger("PersonSimulation")

  val url = System.getProperty("target.url", "localhost:8080")
  val numberOfUsers = Integer.getInteger("users", 1)
  val rampUp  = java.lang.Long.getLong("rampUp", 1L)

  logger.info("Perform load test on {} using {} users with ramp up time of {} seconds",
    url, numberOfUsers, rampUp)

  val httpProtocol = http
    .baseURL(url)
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .acceptLanguageHeader("en-US,de;q=0.8,en-US;q=0.6,en;q=0.4")
    .contentTypeHeader("application/json")
    .userAgentHeader("Apache-HttpClient/4.4.1 (Java/1.8.0_76-release)")

  setUp(CreatePersonScenario.scn.inject(rampUsers(numberOfUsers) over (rampUp seconds)),
    ListOfPersonsScenario.scn.inject(rampUsers(numberOfUsers) over (rampUp seconds))).protocols(httpProtocol)
      .assertions(
        global.successfulRequests.percent.greaterThan(98),
        details("list_of_persons").responseTime.max.lessThan(2000),
        details("create_person").responseTime.max.lessThan(1500),
        details("list_of_persons").responseTime.percentile2.lessThan(1200),
        details("create_person").responseTime.percentile2.lessThan(1000)
      )
}
