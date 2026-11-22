package com.itheima;

import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class JwtTest {
    @Test
    void generatedTokenPreservesIdentity() throws Exception {
        String token = JwtUtils.generateToken(Map.of("id", 42, "username", "demo"));
        assertEquals(42, JwtUtils.parseToken(token).get("id", Integer.class));
        assertEquals("demo", JwtUtils.parseToken(token).get("username", String.class));
    }

    @Test
    void rejectsTamperedSignature() {
        String token = JwtUtils.generateToken(Map.of("id", 42));
        int start = token.lastIndexOf('.') + 1;
        char replacement = token.charAt(start) == 'A' ? 'B' : 'A';
        String tampered = token.substring(0, start) + replacement + token.substring(start + 1);
        assertThrows(JwtException.class, () -> JwtUtils.parseToken(tampered));
    }

    @Test
    void rejectsExpiredToken() {
        String token = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, System.getenv("JWT_SECRET"))
            .claim("id", 42)
            .setExpiration(new Date(System.currentTimeMillis() - 60000))
            .compact();
        assertThrows(ExpiredJwtException.class, () -> JwtUtils.parseToken(token));
    }
}
