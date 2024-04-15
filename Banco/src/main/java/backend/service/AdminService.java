package backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import backend.dto.LoginDTOpos;
import backend.dto.LoginDTOpre;
import backend.exception.AdminException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AdminService extends OncePerRequestFilter implements UserDetailsService {
	
	@Value("${root.login}")
	private String rootLogin;
	
	@Value("${root.password}")
	private String rootPassword;

	@Value("${user.login}")
	private String userLogin;
	
	@Value("${user.password}")
	private String userPassword;

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expirationDateInMs}")
	private long JWT_TOKEN_VALIDITY;

	@SuppressWarnings("unused")
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		if(requestTokenHeader!=null&&!requestTokenHeader.isEmpty()&&requestTokenHeader.startsWith("Bearer ")) {
			String jwtToken = requestTokenHeader.substring(7);
			String username = null;
			try {
				username = this.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.warn("###_Token nao recuperado");
			} catch (ExpiredJwtException e) {
				logger.warn("###_Token expirado");
			}
			if (username==null||username.isEmpty()) logger.warn("###_token nao reconhecido");
			if (SecurityContextHolder.getContext().getAuthentication()!=null) logger.warn("###_Usuario logado: "+SecurityContextHolder.getContext().getAuthentication().toString());
			if (username!=null&&!username.isEmpty()&&SecurityContextHolder.getContext().getAuthentication()==null){
				String password = "";
				if (isRootLogin(username)) {
					password=rootPassword;
				}else if(isUserLogin(username)) {
					password=userPassword;
				}
				UserDetails user = this.getUser(username, password);
				if (!this.validateToken(jwtToken, user)) {
					logger.warn("###_Token nao nao pertence ao usuario logado");
				}else {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					logger.warn("###_Validado! user:"+username+" token:"+jwtToken);
				}
			}
		}
		chain.doFilter(request, response);
	}

	public boolean isRootLogin(String login) {
		return rootLogin.equals(login);
	}
	
	public boolean isUserLogin(String login) {
		return userLogin.equals(login);
	}
	
	public boolean isRoot(String login, String password) {
		return rootLogin.equals(login)&&rootPassword.equals(password);
	}

	public boolean isUser(String login, String password) {
		return userLogin.equals(login)&&userPassword.equals(password);
	}

	public UserDetails getUser(String login, String password) throws UsernameNotFoundException {
		return new User(login, encoder.encode(password), new ArrayList<>());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts
				.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
		;
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public LoginDTOpos autenticar(LoginDTOpre loginDTO) throws AdminException {
		String token;
		LocalDateTime validade;
		if(!this.isRoot(loginDTO.getLogin(), loginDTO.getSenha())&&!this.isUser(loginDTO.getLogin(), loginDTO.getSenha())) {
			throw new AdminException("###_Credencial incorreta");
		}
		try {
			token = this.generateToken(this.getUser(loginDTO.getLogin(), loginDTO.getSenha()));
			validade = this.getExpirationDateFromToken(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LoginDTOpos logadoDTO = new LoginDTOpos();
			logadoDTO.setToken(token);
			logadoDTO.setValidade(validade);
			return logadoDTO;
		}catch(Exception e){
			throw new AdminException("###_Credencial incorreta",e);
		}
	}
	
}