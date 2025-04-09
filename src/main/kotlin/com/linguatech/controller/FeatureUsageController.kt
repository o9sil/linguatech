package com.linguatech.controller

import com.linguatech.dto.SaveFeatureUsageRequest
import com.linguatech.dto.FeatureUsageSummary
import com.linguatech.service.FeatureUsageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/feature-usage")
class FeatureUsageController(
    private val featureUsageService: FeatureUsageService
) {

    @PostMapping("/save")
    fun saveFeatureUsage(@RequestBody saveFeatureUsageRequest: SaveFeatureUsageRequest): ResponseEntity<String> {
        // 기업의 기능 사용 및 크레딧 차감 처리
        return featureUsageService.saveFeatureUsage(saveFeatureUsageRequest)
    }

    // 기능별 사용 내역 및 크레딧 사용량 조회 API
    @GetMapping("/summary")
    fun getFeatureUsageSummary(
        @RequestParam featureId: Long,
        @RequestParam startDate: String,  // "yyyy-MM-dd" 형식
        @RequestParam endDate: String    // "yyyy-MM-dd" 형식
    ): ResponseEntity<List<FeatureUsageSummary>> {
        val summary = featureUsageService.getFeatureUsageSummary(featureId, startDate, endDate)
        if (summary.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyList())
        } else {
            return ResponseEntity.ok(summary)
        }
    }
}