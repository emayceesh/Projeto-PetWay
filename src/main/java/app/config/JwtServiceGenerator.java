package app.config;

//JwtService.java

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import app.auth.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceGenerator {  

	///////////////////////////////////////////////////////
	//Parâmetros para geração do token
	public static final String SECRET_KEY = "4nd1fY0uG01W4nn4G0W1thY0u4nd1fY0uD1e1W4nn4D1eW1thY0u";
	public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256;
	public static final int HORAS_EXPIRACAO_TOKEN = 1;

	public Map<String, Object> gerarPayload(Usuario usuario){
		
		//dados enviados no payload
		Map<String, Object> payloadData = new HashMap<>();
		payloadData.put("id", usuario.getId().toString());
		payloadData.put("role", usuario.getRole());
		payloadData.put("username", usuario.getUsername());
		payloadData.put("nomeCompleto", usuario.getNomeCompleto());
		payloadData.put("email", usuario.getEmail());
		
		return payloadData;
	}

	///////////////////////////////////////////////////////

	
	
	
	
	public String generateToken(Usuario usuario) {

		Map<String, Object> payloadData = this.gerarPayload(usuario);

		return Jwts
				.builder()
				.setClaims(payloadData)
				.setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + 3600000 * this.HORAS_EXPIRACAO_TOKEN))
				.signWith(getSigningKey(), this.ALGORITMO_ASSINATURA)
				.compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}


	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}


	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

}
