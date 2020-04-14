import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoadZooService extends Simulation {

  val users = 1
  val ramp = 30
  val url = "http://localhost:8080"

  val httpProtocol = http
    .baseUrl(url)
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36")

  val userCredentails= csv("animals.csv").circular

  val scn = scenario("Zoo load test")
    .feed(userCredentails)
    .exec(http("POST")
      .post("/animals")
      .body(StringBody("{\"id\": ${id}, \"type\": \"${type}\", \"name\": \"${name}\", \"age\": ${age}}")).asJson
      .check(jsonPath("$.id").find.saveAs("created_id")))
    .exec(http("GET by list")
      .get("/animals/list"))
    .exec(http("PUT")
      .put("/animals/${created_id}")
      .body(StringBody(String.format("{\"id\": ${id}, \"type\": \"${type}\", \"name\": \"${name}%s\", \"age\": ${age}}", " test"))).asJson)
    .pause(5)
    .exec(http("GET by id")
      .get("/animals/${created_id}"))
    .exec(http("DELETE")
      .delete("/animals/${id}"))

  super.setUp(scn.inject(constantUsersPerSec(users) during(ramp second))).protocols(httpProtocol)
}
