package org.pkuse2020grp4.pkusporteventsbackend.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.pkuse2020grp4.pkusporteventsbackend.configuation.JwtConfig;
import org.pkuse2020grp4.pkusporteventsbackend.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
public class JwtUtils {
    public static String sign(User user, JwtConfig jwtConfig){
        String token = null;
        try {
            Date expire = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
            token = JWT.create()
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static Map<String, Claim> verify(String token, JwtConfig jwtConfig){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtConfig.getSecret())).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }
}