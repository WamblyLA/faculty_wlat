package ru.tbank.education.school.lesson7.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Order status")
enum class OrderStatus {
    NEW,
    CONFIRMED,
    CANCELLED
}
