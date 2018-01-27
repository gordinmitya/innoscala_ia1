package ru.gordinmitya.dwitter

import org.scalatra._

class MyScalatraServlet extends ScalatraServlet {

  get("/") {
    views.html.hello()
  }

}
