package com.up42.codingchallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Feature(
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var properties: FeatureProperties? = null,
    var id: UUID?,
    var timestamp: Long?,
    var beginViewingDate: Long?,
    var endViewingDate: Long?,
    var missionName: String?
)