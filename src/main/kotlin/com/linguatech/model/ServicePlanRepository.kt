package com.linguatech.model

import org.springframework.data.jpa.repository.JpaRepository

interface ServicePlanRepository : JpaRepository<ServicePlan, Long> {
}