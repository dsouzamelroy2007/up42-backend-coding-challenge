package com.up42.codingchallenge.aspect

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Order(1)
@Component
class EndpointAspect {
    private val logger = KotlinLogging.logger {}

    @Before("within(com.up42.codingchallenge.controller.FeatureController..*)")
    fun endpointBefore(p: JoinPoint) {
        if (logger.isDebugEnabled) {
            logger.debug(
                (p.target.javaClass.simpleName + " " + p.signature.name).toString() + " START"
            )
            val signatureArgs: Array<Any?> = p.args
            val mapper = ObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            try {
                if (signatureArgs[0] != null) {
                    logger.debug(
                        """Request object: ${mapper.writeValueAsString(signatureArgs[0])}""".trimIndent()
                    )
                }
            } catch (e: JsonProcessingException) {
                logger.error(e.message)
            }
        }
    }

    @AfterReturning(value = "within(com.up42.codingchallenge.controller.FeatureController..*)", returning = "returnValue")
    fun endpointAfterReturning(p: JoinPoint, returnValue: Any?) {
        if (logger.isDebugEnabled) {
            val mapper = ObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            try {
                logger.debug(
                    """ Response object: ${mapper.writeValueAsString(returnValue)} """.trimIndent()
                )
            } catch (e: JsonProcessingException) {
                logger.error(e.message)
            }
            logger.debug((p.target.javaClass.simpleName + " " + p.signature.name) + " END")
        }
    }

    @AfterThrowing(pointcut = "within(com.up42.codingchallenge.controller.FeatureController..*)", throwing = "e")
    @Throws(Exception::class)
    fun endpointAfterThrowing(p: JoinPoint, e: Exception) {
        if (logger.isErrorEnabled) {
            e.printStackTrace()
            logger.error((p.target.javaClass.simpleName + " " + p.signature.name) + " " + e.message)
        }
    }
}
