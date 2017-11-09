package com.miraclebank.security.jwt

import org.apache.commons.codec.binary.Base64
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

@Service
class JwtService {

    fun createToken(username: String, secret: String, expireAt: Date?): String? {
        if (StringUtils.hasText(username) && StringUtils.hasText(secret) && expireAt != null && expireAt.after(Date())) {
            val secret2 = String(Base64.encodeBase64(secret.toByteArray()))
            return Jwts.builder()
                    .setSubject(username)
                    .signWith(SignatureAlgorithm.HS512, secret2)
                    .setExpiration(expireAt)
                    .compact()
        }
        return null
    }

    fun isValid(token: String, secret: String): Boolean {
        if (StringUtils.hasText(token) && StringUtils.hasText(secret)) {
            val secret2 = String(Base64.encodeBase64(secret.toByteArray()))
            Jwts.parser().setSigningKey(secret2).parseClaimsJws(token)
            return true
        }
        return false
    }

    fun getUsername(token: String, secret: String): String? {
        if (StringUtils.hasText(token) && StringUtils.hasText(secret)) {
            val secret2 = String(Base64.encodeBase64(secret.toByteArray()))
            return Jwts.parser().setSigningKey(secret2).parseClaimsJws(token).getBody().getSubject()
        }
        return null
    }

    companion object {
        internal val LOGGER = LoggerFactory.getLogger(JwtService::class.java)
    }


}
