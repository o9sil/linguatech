package com.linguatech.controller

import com.linguatech.dto.CreateServicePlanRequest
import com.linguatech.model.ServicePlan
import com.linguatech.service.ServicePlanService
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/service-plan")
class ServicePlanController(
    private val servicePlanService: ServicePlanService
) {

    @PostMapping("")
    fun createServicePlan(
        @RequestBody createServicePlanRequest: CreateServicePlanRequest
    ): ResponseEntity<Any> {
        // 서비스 요금제 생성
        val createServicePlan = servicePlanService.createServicePlan(createServicePlanRequest)
        // 성공 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createServicePlan)
    }

    // 서비스 요금제 조회
    @GetMapping("/{id}")
    @Schema(description = "서비스 요금제 조회", example = "서비스 요금제 ID를 입력하여 해당 요금제의 세부 정보를 조회합니다.")
    fun getServicePlan(@PathVariable id: Long): ResponseEntity<ServicePlan> {
        val servicePlan = servicePlanService.getServicePlan(id)
        return ResponseEntity.ok(servicePlan)
    }
}