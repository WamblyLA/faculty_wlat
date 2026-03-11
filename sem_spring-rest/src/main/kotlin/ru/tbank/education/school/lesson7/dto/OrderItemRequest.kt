package ru.tbank.education.school.lesson7.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Schema(description = "Line item in the order request")
data class OrderItemRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 32)
    @Schema(description = "Product SKU", example = "SKU-123")
    val sku: String,

    @field:Positive
    @Schema(description = "Quantity", example = "2", minimum = "1")
    val quantity: Int,

    @field:DecimalMin("0.01")
    @field:Digits(integer = 10, fraction = 2)
    @Schema(description = "Price for a single item", example = "199.99", minimum = "0.01")
    val price: BigDecimal
)
