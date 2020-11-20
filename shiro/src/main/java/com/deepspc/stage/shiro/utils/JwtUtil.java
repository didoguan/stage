package com.deepspc.stage.shiro.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.utils.StageUtil;
import io.jsonwebtoken.*;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/11/19
 **/
public class JwtUtil {

	/**
	 * 实例化token
	 * @param issuer 签发人
	 * @param subject 登录用户ID
     * @param claims 非私密内容
	 * @param overTime 超时时间(豪秒)
	 */
	public static String instanceToken(String issuer, String subject, Map<String, Object> claims, long overTime) {
        Calendar calendar = Calendar.getInstance();
		JwtBuilder builder = Jwts.builder()
                                .setHeaderParam("typ", "JWT")
                                .setSubject(subject)
                                .setIssuer(issuer)
                                .setIssuedAt(calendar.getTime())
                                .setClaims(claims)
                                .setId(IdUtil.randomUUID())
                                .signWith(SignatureAlgorithm.HS256, DatatypeConverter.parseBase64Binary(StageUtil.SECRET_KEY));
		if (overTime > 0) {
            long time = calendar.getTimeInMillis() + overTime;
            calendar.setTimeInMillis(time);
            builder.setExpiration(calendar.getTime());
        }
		return builder.compact();
	}

    /**
     * 验证token，获取内容
     * @param token token
     */
    public static Claims verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
		Claims claims = null;
        try {
            //验证通过则返回内容
			claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(StageUtil.SECRET_KEY))
                    .parseClaimsJws(token).getBody();

			boolean isExpired = expired(token);
			if (isExpired) {
				return null;
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * token是否过期(true-已过期，false-未过期)
     * @param token token
     */
    public static Boolean expired(String token) {
        Claims claims = verifyToken(token);
        if (null == claims) {
            return true;
        }
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 获取存放在token中的userId
     * @param token token
     */
    public static String getUserId(String token) {
        Claims claims = verifyToken(token);
        if (null == claims) {
            return null;
        }
        return claims.getSubject();
    }
}
