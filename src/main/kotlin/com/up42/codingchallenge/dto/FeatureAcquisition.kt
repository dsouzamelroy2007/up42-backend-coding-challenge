package com.up42.codingchallenge.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeatureAcquisition(
    var beginViewingDate: Long?,
    var endViewingDate: Long?,
    var missionName: String?
)
