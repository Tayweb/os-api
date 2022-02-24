package com.spring.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.ApplicationContextLoad;
import com.spring.domain.Funcionario;
import com.spring.repository.FuncionarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	// tempo de validade do token 2 dias. Tem um site que converte dias para
	// milesegundos
	private static final long EXPIRATION_TIME = 172800000;

	// Uma senha unica para compor a autenticação e ajudar na segurança
	private static final String SECRET = "*SenhaExtremamenteSecreta";

	/* Prefixo padrão de token */
	private static final String TOKEN_PREFIX = "Bearer";

	// Cabeçalho da resposta
	private static final String HEADER_STRING = "Authorization";

	/* Gerando token de autenticação e adicionando ao cabeçalho e resposta Http */

	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		String JWT = Jwts.builder()/* Chama o gerador de Token */
				.setSubject(username) /* Adiciona o usuario */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact(); /* Compactação e algoritmos de geração de senha */

		/* JUnta o token com o prefixo */
		String token = TOKEN_PREFIX + " " + JWT; /* Bearer ff34trgwgrgTBH */

		/* Adiciona no cabeçalho http */
		response.addHeader(HEADER_STRING, token); /* Authorization Bearer ff34trgwgrgTBH */

		ApplicationContextLoad.getApplicationContext().getBean(FuncionarioRepository.class).atualizaTokenUser(JWT,
				username);

		// Liberando resposta para portas diferentes que usam a API ou caso clientes Web

		/* Escreve token como resposta no corpo http */
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	/* Retorna o usuário validado com token ou caso não seja válido retorna null */

	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		/* Pega o token enviado no cabeçalho http */

		String token = request.getHeader(HEADER_STRING);

		try {
			if (token != null) {

				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

				// Faz a validação do token do usuário na resquisição
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(tokenLimpo).getBody()
						.getSubject(); /* João Silva */

				if (user != null) {

					Funcionario funcionario = ApplicationContextLoad.getApplicationContext()
							.getBean(FuncionarioRepository.class).acessoLogin(user);

					if (funcionario != null) {

						if (tokenLimpo.equalsIgnoreCase(funcionario.getToken())) {

							return new UsernamePasswordAuthenticationToken(funcionario.getLogin(),
									funcionario.getSenha(), funcionario.getAuthorities());
						}

					}

				}
			} // Fim condição Token

		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			try {
				response.getOutputStream()
						.println("Token expirado. Faça o login ou informe um novo Token para autenticação");
			} catch (IOException e1) {

			}
		}

		return null; // Não autorizado
	}

}
