package com.linguatech.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

// 기업 Entity
@Entity
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    var credit: Int,

    @ManyToOne
    @JoinColumn(name = "service_plan_id")
    var servicePlan: ServicePlan? = null,

    @CreatedDate
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
)