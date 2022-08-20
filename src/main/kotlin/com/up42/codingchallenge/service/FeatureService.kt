package com.up42.codingchallenge.service

import com.up42.codingchallenge.datasource.impl.FeatureFileDataSource
import com.up42.codingchallenge.dto.FeatureDto
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class FeatureService(@Qualifier("file") private val dataSource: FeatureFileDataSource) {
    fun getFeatures(): List<FeatureDto> = dataSource.retrieveFeatures()
}
