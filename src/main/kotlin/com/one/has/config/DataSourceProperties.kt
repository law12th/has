package com.one.has.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("classpath:application.properties")
class DataSourceProperties {
    @Value("\${user}")
    val user: String = ""

    @Value("\${password}")
    val password: String = ""

    @Value("\${serverNameOrIp}")
    val serverNameOrIp: String = ""

    @Value("\${serverPort}")
    val serverPort: String = ""

    @Value("\${databaseName}")
    val databaseName: String = ""
}