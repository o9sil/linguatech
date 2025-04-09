package com.linguatech.model

import jakarta.persistence.*

// 서비스 요금제와 기능 연결 Entity
@Entity
data class ServicePlanFeature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "service_plan_id")
    val servicePlan: ServicePlan,

    @ManyToOne
    @JoinColumn(name = "feature_id")
    val feature: Feature
)
