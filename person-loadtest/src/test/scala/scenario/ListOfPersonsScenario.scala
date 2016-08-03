/*
 * *************************************************************************
 *
 * Copyright:       Robert Bosch GmbH, 2016
 *
 * *************************************************************************
 */

package scenario

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import common.Headers._

/**
  * Scenario for calling REST API for getting list of persons.
  */
object ListOfPersonsScenario {

  val scn = scenario("ListOfPersonsScenario")
    .exec(http("list_of_persons")
      .get("/persons")
      .headers(headers_0))

}
