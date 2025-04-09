package com.linguatech.model

import org.springframework.data.jpa.repository.JpaRepository

interface FeatureRepository : JpaRepository<Feature, Long> {
}