package com.linguatech.service

import com.linguatech.dto.FeatureUsageSummary
import com.linguatech.dto.SaveFeatureUsageRequest
import com.linguatech.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class FeatureUsageService(
    private val companyRepository: CompanyRepository,
    private val featureRepository: FeatureRepository,
    private val featureUsageRepository: FeatureUsageRepository,
    private val servicePlanFeatureRepository: ServicePlanFeatureRepository
) {

    // 기업이 서비스 사용 처리
    @Transactional
    fun saveFeatureUsage(saveFeatureUsageRequest: SaveFeatureUsageRequest): ResponseEntity<String> {
        // 기업 조회
        val company = companyRepository.findById(saveFeatureUsageRequest.companyId).orElse(null)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("${saveFeatureUsageRequest.companyId} 기업이 존재하지 않습니다.")
        // 기능 조회
        val feature = featureRepository.findById(saveFeatureUsageRequest.featureId).orElse(null)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("${saveFeatureUsageRequest.featureId} 기능이 존재하지 않습니다.")

        // 기능에 따른 제약 처리
        if (feature.type == "CHAR_PER_CALL") {
            // 1회 호출시 글자수 제한
            if (saveFeatureUsageRequest.inputText.length > feature.credit) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력한 글자수가 ${feature.credit}자를 초과할 수 없습니다.")
            }
        } else if (feature.type == "COUNT_PER_MONTH") {
            // 월별 호출 제한 횟수
        }

        // 해당 기업에 서비스 등록 여부 확인
        var servicePlanId = company.servicePlan?.id
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("$company.id 기업에 서비스가 등록되지 않았습니다.")

        // 서비스 플랜에 해당 기능이 포함되어 있는지 확인
        if (servicePlanFeatureRepository.findByServicePlanIdAndFeatureId(servicePlanId, feature.id) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이 기업의 서비스 플랜에는 $feature.id 기능이 포함되어 있지 않습니다.")
        }

        // 잔여 크레딧 확인
        if (company.credit < feature.credit) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잔여 크레딧이 부족합니다.")
        }

        // 크레딧 차감 후 저장
        company.credit -= feature.credit
        companyRepository.save(company)

        // 이력 저장
        featureUsageRepository.save(FeatureUsage(company = company, feature = feature, useCredit = feature.credit))

        return ResponseEntity.ok("SUCCESS")
    }

    // 사용 통계 조회
    fun getFeatureUsageSummary(
        featureId: Long,
        startDate: String,
        endDate: String
    ): List<FeatureUsageSummary> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        // LocalDateTime 변경
        val startDateAtStartOfDay = start.atStartOfDay()
        val endDateAtEndOfDay = end.atTime(LocalTime.MAX)

        // 데이터 조회
        return featureUsageRepository.findFeatureUsageSummary(featureId, startDateAtStartOfDay, endDateAtEndOfDay)
    }
}