package com.one.has.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.*

@Configuration
class DataSourceConfig(
    @Autowired
    private val config: DataSourceProperties
) {
    private val hikariDataSource: HikariDataSource

    companion object {
        private val logger = LoggerFactory.getLogger(DataSourceConfig::class.java)
    }

    init {
        val hikariProperties = Properties()
        val dataSourceProperties = Properties()

        val serverName = config.serverNameOrIp
        val serverPort = config.serverPort
        val databaseName = config.databaseName
        val username = config.user
        val password = config.password

        hikariProperties.setProperty(
            "maximumPoolSize",
            "10"
        )

        val hikariConfig = HikariConfig(hikariProperties)
        hikariConfig.dataSourceProperties = dataSourceProperties
        hikariConfig.jdbcUrl = "jdbc:postgresql://$serverName:$serverPort/$databaseName"
        hikariConfig.username = username
        hikariConfig.password = password

        hikariDataSource = HikariDataSource(hikariConfig)

        logger.info("Hikari CP - Maximum Pool Size: 10")
    }

    @Bean
    @Primary
    fun dataSource(): HikariDataSource = hikariDataSource
}