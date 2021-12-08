package br.com.bernhoeft.meetings.authentication;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;


public interface AuthenticationUtil {
	
	/*
	 * auth patter ex: "7|3|5|4"
	 */
	public static void checkAuthorityProfile(String authPattern) throws AccessDeniedException {
		if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().matches(authPattern))) {
			throw new AccessDeniedException("Não autorizado");
		}
	}
	
	public static String getAuthenticatedUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static boolean hasAuthority(String authPattern) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().matches(authPattern));
	}

}
