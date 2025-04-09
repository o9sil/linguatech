package com.linguatech.service

import com.linguatech.dto.CreateServicePlanRequest
import com.linguatech.model.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServicePlanService(
    private val servicePlanRepository: ServicePlanRepository,
    private val servicePlanFeatureRepository: ServicePlanFeatureRepository,
    private val featureRepository: FeatureRepository
) {

    // 서비스 요금제 생성
    @Transactional
    fun createServicePlan(createServicePlanRequest: CreateServicePlanRequest): ServicePlan {
        // 서비스 요금제 생성
        val servicePlan = ServicePlan(
            name = createServicePlanRequest.name,
            description = createServicePlanRequest.description
        )
        val servicePlanSave = servicePlanRepository.save(servicePlan)

        // 생성된 서비스 요금제에 기능 생성
        val servicePlanFeatures = createServicePlanRequest.featureIds.map { featureId ->
            // 기능 사용 가능여부 확인
            val feature = featureRepository.findById(featureId)
                .orElseThrow { RuntimeException("$featureId 기능은 사용 불가능합니다.") }

            // 서비스 요금제에 기능 연결
            ServicePlanFeature(
                servicePlan = servicePlanSave,
                feature = feature
            )
        }

        // 생성된 기능들 저장
        servicePlanFeatureRepository.saveAll(servicePlanFeatures)

        return servicePlanSave
    }

    // 서비스 요금제 조회
    fun getServicePlan(id: Long): ServicePlan {
        return servicePlanRepository.findById(id)
            .orElseThrow { RuntimeException("서비스 요금제를 찾을 수 없습니다.") }
    }
}