package com.up42.codingchallenge.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeatureProperties(
    var id: UUID?,
    var timestamp: Long?,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var acquisition: FeatureAcquisition? = null,
)
