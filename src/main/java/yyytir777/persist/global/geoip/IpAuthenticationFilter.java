package yyytir777.persist.global.geoip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Slf4j
@Component
@Profile("prod")
@RequiredArgsConstructor
public class IpAuthenticationFilter implements Filter {

    private final DatabaseReader databaseReader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress = HttpRequestUtils.getClientIpAddressIfServletRequestExist();
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        String country = null;

        try {
            country = databaseReader.country(inetAddress).getCountry().getName();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        if(country == null || !country.equals("South Korea")) {
            log.info("Access Rejected : {}, {}", ipAddress, country);
            return;
        }
        chain.doFilter(request, response);
    }
}