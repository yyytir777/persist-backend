package yyytir777.persist.global.geoip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.record.Country;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.InetAddress;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class IpAuthenticationFilter extends OncePerRequestFilter {

    private final DatabaseReader databaseReader;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        try {
            InetAddress ipAddress = InetAddress.getByName(clientIp);
            Country country = databaseReader.country(ipAddress).getCountry();

            if(!"KR".equals(country.getIsoCode())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return;
            }
        } catch (GeoIp2Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "unable to process GeoIP data");
            return;
        }
        filterChain.doFilter(request, response);
    }
}