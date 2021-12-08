package br.com.bernhoeft.meetings.authentication;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class JWTUtil {
	private static final String key = "aZqK2XzLTaUWd4j2g5rJFGgb3A2Xz2XEtcA3czjN37VeXzhLnCSwkAtGM5TPhy3qdtmByJ9mPd62P4NdtecrBx7eCkRMnSCbwaapVn5vs4eyCmycxXXY9qvw9peLfjcn";
	private static final long exp = 14400000L;
	public static final String header_name = "authentication";
	static final String TOKEN_PREFIX = "Bearer";

	
	@SuppressWarnings("unused")
	public static void generate(HttpServletResponse response, Authentication authResult ) {
		Claims claims = Jwts.claims().setSubject(authResult.getName());
		
		List<String> auth_profiles = new ArrayList<String>();
		
		authResult.getAuthorities().forEach(a -> auth_profiles.add(a.getAuthority()));
		
		claims.put("authorities", auth_profiles);
		Date extDate = new Date(System.currentTimeMillis() + exp);
		String jwt = Jwts.builder().setClaims(claims).setIssuedAt(new Date()).setExpiration(extDate).signWith(SignatureAlgorithm.HS512, key).compact();
		response.addHeader(header_name, jwt);
		
	}

	@SuppressWarnings("unused")
	public static String getUser(String jwt) throws ExpiredJwtException, MalformedJwtException {

			Claims claims = Jwts.parser().setSigningKey(JWTUtil.key).parseClaimsJws(jwt).getBody();
			return claims.getSubject();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public static ArrayList<String> getAutority(String jwt) throws ExpiredJwtException {
		
		Claims claims = Jwts.parser().setSigningKey(JWTUtil.key).parseClaimsJws(jwt).getBody();
		return (ArrayList<String>) claims.get("authorities");
	}

}
