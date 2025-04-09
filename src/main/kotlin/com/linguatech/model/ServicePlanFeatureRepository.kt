package com.linguatech.model

import org.springframework.data.jpa.repository.JpaRepository

interface ServicePlanFeatureRepository : JpaRepository<ServicePlanFeature, Long> {

    // 서비스 플랜 ID와 기능 ID로 기능을 찾기
    fun findByServicePlanIdAndFeatureId(servicePlanId: Long, featureId: Long): ServicePlanFeature?
}