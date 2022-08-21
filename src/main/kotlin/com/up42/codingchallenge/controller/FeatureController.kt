package com.up42.codingchallenge.controller

import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.exception.ResourceNotFoundException
import com.up42.codingchallenge.service.FeatureService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/features")
@Api(value = "Feature API Controller", description = "Feature API")
class FeatureController(private val service: FeatureService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    @ApiOperation(value = "Return all features.")
    fun getFeatures(): List<FeatureDto> = service.getFeatures()

    @RequestMapping(value = ["/{featureId}/quicklook"], method = [RequestMethod.GET], produces = [MediaType.IMAGE_PNG_VALUE, MediaType.TEXT_HTML_VALUE])
    @ApiOperation(value = "Return quicklook for a feature by its id.")
    fun getFeatureImage(@PathVariable featureId: String): ResponseEntity<Any> {
        try {
            val imageBytes = service.getQuicklook(featureId)
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .body(imageBytes)
        } catch (e: ResourceNotFoundException) {
            return ResponseEntity.badRequest()
                .body(e.message)
        }
    }
}
