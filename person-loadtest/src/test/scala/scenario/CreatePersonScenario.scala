/*
 * *************************************************************************
 *
 * Copyright:       Robert Bosch GmbH, 2016
 *
 * *************************************************************************
 */

package scenario

import common.Headers._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Scenario for calling REST API for creating a person.
  */
object CreatePersonScenario {

  val scn = scenario("CreatePersonScenario")
    .exec(http("create_person")
      .post("/persons")
      .headers(headers_0)
      .body(RawFileBody("CreatePersonScenario_request.txt")))
}