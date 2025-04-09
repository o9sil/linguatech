package com.linguatech.model

import com.linguatech.dto.FeatureUsageSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface FeatureUsageRepository : JpaRepository<FeatureUsage, Long> {

    // 사용 통계 조회 쿼리
    @Query("SELECT new com.linguatech.dto.FeatureUsageSummary(fu.feature.id, COUNT(fu), SUM(fu.useCredit)) " +
            "FROM FeatureUsage fu " +
            "WHERE fu.feature.id = :featureId " +
            "AND fu.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY fu.feature.id")
    fun findFeatureUsageSummary(
        @Param("featureId") featureId: Long,
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime
    ): List<FeatureUsageSummary>
}