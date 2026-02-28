package com.example.demo1

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "items")
class GivenProperties(
    val maxCnt: Int = 100,
    val impossibleNames: List<String> = emptyList(),
)