package hu.futureofmedia.task.contactsapi.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
	@Override
	public void commence(HttpServletRequest httpServletRequest,
						 HttpServletResponse httpServletResponse,
						 AuthenticationException e) throws IOException, ServletException {
		logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
		if(e instanceof BadCredentialsException) {
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			String invalidUserNameOrPassword =( "Incorrect account or password");
			httpServletResponse.getOutputStream().write(new ObjectMapper()
					.writeValueAsBytes(invalidUserNameOrPassword));
		} else {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Unauthorized access");
		}
	}
}