package com.up42.codingchallenge.controller

import com.up42.codingchallenge.dto.FeatureDto
import com.up42.codingchallenge.service.FeatureService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/features")
class FeatureController(private val service: FeatureService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getFeatures(): List<FeatureDto> = service.getFeatures()
    @GetMapping("/{featureId}/quicklook")
    fun getFeatureImage(@PathVariable featureId: String): ResponseEntity<Any> {
        val imageBytes = service.getQuicklook(featureId)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
            .body(imageBytes)
    }
}
