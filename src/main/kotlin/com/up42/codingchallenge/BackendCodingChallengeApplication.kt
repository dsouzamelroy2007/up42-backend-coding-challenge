package com.up42.codingchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2
class BackendCodingChallengeApplication

fun main(args: Array<String>) {
    runApplication<BackendCodingChallengeApplication>(*args)
}
