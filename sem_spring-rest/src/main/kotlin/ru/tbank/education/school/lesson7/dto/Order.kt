package ru.tbank.education.school.lesson7.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Order response")
data class Order(
    @Schema(description = "Order id", example = "1")
    val id: Long,

    @Schema(description = "Customer email", example = "demo@example.com")
    val customerEmail: String,

    @Schema(description = "Delivery address", example = "Green st, 1")
    val deliveryAddress: String,

    @Schema(description = "Order items")
    val items: List<OrderItem>,

    @Schema(description = "Total order amount", example = "399.98", minimum = "0.01")
    val totalAmount: BigDecimal,

    @Schema(description = "Current status")
    val status: OrderStatus
)
