package com.linguatech.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

// 서비스 요금제 생성 Request
data class CreateServicePlanRequest(
    @field:NotBlank(message = "서비스 요금제 이름은 필수입니다.")
    @Schema(description = "서비스 요금제 이름", example = "샘플 요금제")
    val name: String,

    @Schema(description = "서비스 요금제 설명", example = "AI 번역, AI 뉘앙스 조절을 포함한 요금제입니다.")
    val description: String? = null,

    @field:NotEmpty(message = "서비스 요금제에 포함될 기능은 1개 이상 입력이 필요합니다.")
    @Schema(description = "서비스 요금제에 포함될 기능들의 ID", example = "[1, 3]")
    val featureIds: List<Long>
)
