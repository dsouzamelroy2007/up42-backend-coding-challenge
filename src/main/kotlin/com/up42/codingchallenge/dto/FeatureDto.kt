package com.up42.codingchallenge.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeatureDto(
    var id: UUID?,
    var timestamp: Long?,
    var beginViewingDate: Long?,
    var endViewingDate: Long?,
    var missionName: String?
)
