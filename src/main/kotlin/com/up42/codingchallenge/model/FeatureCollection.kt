package com.up42.codingchallenge.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.geojson.Feature

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeatureCollection(var features: List<Feature>)
