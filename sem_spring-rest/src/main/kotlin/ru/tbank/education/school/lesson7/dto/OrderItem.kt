package ru.tbank.education.school.lesson7.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Line item in the order")
data class OrderItem(
    @Schema(description = "Product SKU", example = "SKU-123")
    val sku: String,

    @Schema(description = "Quantity", example = "2", minimum = "1")
    val quantity: Int,

    @Schema(description = "Price for a single item", example = "199.99", minimum = "0.01")
    val price: BigDecimal,

    @Schema(description = "Total for this line", example = "399.98", minimum = "0.01")
    val lineTotal: BigDecimal
)
