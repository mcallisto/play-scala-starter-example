import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.play.PlaySpec

import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

import models.Pet_Item

class JsonSerdeSpec extends PlaySpec with GuiceOneAppPerSuite {

  "Pet_Items" should {

    val mockJson = Json.obj("id" -> 42, "price" -> 4.2, "name" -> "Giant Rabbit")

    implicit val readsPet_Item = Json.reads[Pet_Item]

    "render the pets page" in {
      val pets = route(app, FakeRequest(POST, "/pets").withJsonBody(mockJson)).get
      status(pets) === OK
      contentType(pets) mustBe Some("application/json")
      val jsonResult = contentAsJson(pets)

      jsonResult.toString() === """{"id" : 42, "price" : 4.2, "name" : "Giant Rabbit"}"""
    }
  }
}
