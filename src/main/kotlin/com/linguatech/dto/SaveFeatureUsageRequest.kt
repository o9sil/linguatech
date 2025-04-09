package com.linguatech.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class SaveFeatureUsageRequest(
    @field:NotBlank(message = "사용 기업은 필수입니다.")
    @Schema(description = "기업 ID", example = "1")
    val companyId: Long,

    @field:NotBlank(message = "사용 기능은 필수입니다.")
    @Schema(description = "기능 ID", example = "1")
    val featureId: Long,

    @Schema(description = "사용자 입력 텍스트", example = "안녕하세요.")
    val inputText: String
)
