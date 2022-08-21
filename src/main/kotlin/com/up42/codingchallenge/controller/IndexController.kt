package com.up42.codingchallenge.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController : ErrorController {

    @RequestMapping("/error")
    fun error(): String {
        return "Page Not Found"
    }
}
