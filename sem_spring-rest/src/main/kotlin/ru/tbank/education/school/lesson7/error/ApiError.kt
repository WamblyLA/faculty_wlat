package ru.tbank.education.school.lesson7.error

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Error response")
data class ApiError(
    @Schema(description = "Short error message", example = "Validation failed")
    val message: String,

    @Schema(description = "Detailed validation errors")
    val details: List<String> = emptyList()
)
