package com.linguatech.controller

import com.linguatech.service.CompanyService
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/company")
class CompanyController(
    private val companyService: CompanyService
) {

    // 기업에 서비스 요금제 연결
    @PutMapping("/{companyId}/{servicePlanId}")
    fun updateServicePlanForCompany(
        @PathVariable companyId: Long,
        @PathVariable servicePlanId: Long
    ): ResponseEntity<Any> {
        // 기업에 서비스 요금제 연결
        val updateServicePlanForCompany = companyService.updateServicePlanForCompany(companyId, servicePlanId)
        // 성공 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(updateServicePlanForCompany)
    }

    // 기업에 연결된 서비스 요금제 조회
    @GetMapping("/{companyId}")
    @Schema(description = "기업의 서비스 요금제 정보를 조회합니다.", example = "companyId를 통해 연결된 서비스 요금제를 조회합니다.")
    fun getServicePlanForCompany(@PathVariable companyId: Long): ResponseEntity<Any> {
        val company = companyService.getCompanyWithServicePlan(companyId)

        // 기업이 존재하지 않거나 서비스 요금제가 연결되지 않은 경우 예외처리
        if (company != null && company.servicePlan != null) {
            return ResponseEntity.ok(company)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("기업 또는 서비스 요금제가 존재하지 않습니다.")
        }
    }
}