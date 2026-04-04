package com.speakweb.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// leer la cabecera Authorization
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		// si no hay cabecera o no empieza por Bearer
		if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return; // deja pasar sin autenticar
		}
		
		// extracción del token- quita prefijo "Bearer " (con el espacio son 7)
		String jwt = authHeader.substring(7);
		
		// token inválido
		if (!jwtUtil.isValid(jwt)) {
            filterChain.doFilter(request, response);
            return; // deja pasar sin autenticar
		}
		
		// extrae email del token
		String username = jwtUtil.getUserName(jwt);

		// hay usuario pero no autenticado en SecurityContext
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// busca al usuario en la bd por su email (username): obtiene sus datos y permisos
            var userDetails = this.userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
            	// creación del objeto de autenticación con sus roles
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // añade info extra
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // registra la autenticación
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
		
	}

}
