package ru.gordinmitya.dwitter

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

class MyScalatraServlet extends ScalatraServlet with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  var dwits = List(Message(1, "Hello"), Message(2, "World"))

  get("/messages") {
    dwits
  }

  get("/messages/:id") {
    val id = params.getOrElse("id", halt(400)).toInt
    if (!dwits.exists(_.id == id))
      halt(404)
    dwits.find(_.id == id)
  }

  post("/messages") {
    val newDwit = parsedBody.extract[Message]
    dwits = newDwit :: dwits
  }

  put("/messages/:id") {
    val id = params.getOrElse("id", halt(400)).toInt
    if (!dwits.exists(_.id == id))
      halt(404)
    val newDwit = Message(id, parsedBody.extract[Message].text)
    dwits = newDwit :: dwits.filterNot(_.id == id)
  }

  delete("/messages/:id") {
    val id = params.getOrElse("id", halt(400)).toInt
    if (!dwits.exists(_.id == id))
      halt(404)
    dwits = dwits.filterNot(_.id == id)
  }
}
