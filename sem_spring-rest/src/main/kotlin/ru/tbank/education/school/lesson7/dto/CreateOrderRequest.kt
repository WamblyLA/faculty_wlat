package ru.tbank.education.school.lesson7.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "Request to create or update an order")
data class CreateOrderRequest(
    @field:NotBlank
    @field:Email
    @Schema(description = "Customer email", example = "demo@example.com")
    val customerEmail: String,

    @field:NotBlank
    @field:Size(max = 200)
    @Schema(description = "Delivery address", example = "Green st, 1")
    val deliveryAddress: String,

    @field:NotEmpty
    @field:Size(max = 20)
    @field:Valid
    @Schema(description = "Order items")
    val items: List<OrderItemRequest>
)
