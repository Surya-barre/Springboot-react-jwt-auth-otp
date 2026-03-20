package in.surya.Security.filter;

import in.surya.Security.UtilJwt.JwtUtil;
import in.surya.Security.service.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

private static final List<String> PUBLIC_URLS = List.of("/login","/api/profile/register","/send-reset-opt","/reset-password","/logout");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("this is line 30"+request);
        System.out.println("this is line 31"+response);
        System.out.println("this is line 32"+filterChain);
  String requestURI = request.getRequestURI();
        System.out.println("This is the 34reqeustUrl"+requestURI);
   String path = request.getServletPath();
        System.out.println("This is the pathurl"+path);
        if (PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = null;
        String email = null;
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        if(jwtToken==null) {
            Cookie[] cookies = request.getCookies();
            System.out.println("cookies relavent data"+cookies);
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwt")) {
                        jwtToken = cookie.getValue();
                        break;
                    }

                }
            }
        }
        if(jwtToken!=null)
        {

            email= jwtUtil.extractEmail(jwtToken);
            System.out.println("this is line 61 in jwtRequestFilter"+email);
            System.out.println("this is line 62 in jwtRequestFilter"+SecurityContextHolder.getContext());
            if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                System.out.println("this is line 64 in jwtRequestFilter"+email);
                UserDetails userDetails= appUserDetailsService.loadUserByUsername(email);
                System.out.println("this is line 63 in jwtRequestFilter"+userDetails);
                if(jwtUtil.validateToken(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    System.out.println("this is line 67 in jwtRequestFilter"+authToken);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        }
        filterChain.doFilter(request, response);
                }
            }
