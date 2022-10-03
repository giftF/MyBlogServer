package org.gift.Utiliy;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    // 过期时间,单位毫秒
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    // jwt密钥
    private static final String SECRET = "test";

    // 生成token
    public static String sign(String userID, Map<String, Object> info){
        try{
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create().withAudience(userID).withClaim("info", info).withExpiresAt(date).sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 校验token
    public static boolean checkSign(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e){
            LogUtil.LOGGING.info(String.valueOf(e));
            return false;
        }
    }
}






















