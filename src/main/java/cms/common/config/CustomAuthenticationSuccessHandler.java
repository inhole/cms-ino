package cms.common.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Authentication authentication) throws IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_MANAGER"));
        boolean isUser = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ROLE_USER"));

        String servletPath = request.getServletPath();
        boolean adminLoginRequest = "/admin/login".equals(servletPath);

        if (adminLoginRequest && !isAdmin) {
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
            response.sendRedirect("/admin/login?roleError");
            return;
        }

        if (!adminLoginRequest && !isUser) {
            SecurityContextHolder.clearContext();
            request.getSession().invalidate();
            response.sendRedirect("/login?roleError");
            return;
        }

        if (isAdmin) {
            response.sendRedirect("/admin/dashboard");
        } else {
            response.sendRedirect("/user/dashboard");
        }
    }
}
