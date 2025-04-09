package com.linguatech.dto

data class FeatureUsageSummary(
    val featureId: Long,
    val usageCount: Long,
    val totalCreditUsed: Long
)
