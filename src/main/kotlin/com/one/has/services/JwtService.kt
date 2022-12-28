package com.one.has.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.sql.DataSource
import kotlin.collections.HashMap

@Service
class JwtService(
    @Autowired
    private val dataSource: DataSource
) {
    fun generateJWTTokenForUser(userId: Long): String? {
        val claims = HashMap<String, Any>()
        claims["role"] = getUserRole(userId)

        val now = Date()

        val issuer = userId.toString()

        val expiryDate = Date(System.currentTimeMillis() + 60 * 24 * 1000)

        val secretKey = Base64.getEncoder().encodeToString("secret".toByteArray())

        return Jwts.builder()
            .setClaims(DefaultClaims(claims))
            .setId(issuer)
            .setSubject("jwt")
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    private fun getUserRole(userId: Long): String {
      var sql = """
         SELECT r.name
         FROM role r
         JOIN public.user u ON r.id = u.role_id
         WHERE u.id = ?
      """.trimIndent()

        var role = ""

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                statement.setLong(1, userId)
                statement.executeQuery().use { resultSet ->
                    if (resultSet.next()) {
                        role = resultSet.getString("name")
                    }
                }
            }
        }

        return role
    }
}