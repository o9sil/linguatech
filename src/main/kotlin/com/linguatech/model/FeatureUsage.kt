package com.linguatech.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

// 기능 사용 이력 Entity
@Entity
data class FeatureUsage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "company_id")
    val company: Company,

    @ManyToOne
    @JoinColumn(name = "feature_id")
    val feature: Feature,

    var useCredit: Int,

    @CreatedDate
    val createdAt: LocalDateTime? = null
)
