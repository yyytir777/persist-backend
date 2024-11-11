package yyytir777.persist.domain.log.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class ViewCountValidator {

    // 봤으면 true (조회수 오르면 안됨)
    public boolean hasViewedInCoookie(HttpServletRequest request, HttpServletResponse response, String logId) {

        Cookie[] cookies = request.getCookies();
        List<Cookie> list = cookies != null ? Arrays.stream(cookies).toList() : List.of(); // null 체크 추가
        String cookieName = "postViewed";
        for (Cookie cookie : list) {
            System.out.println("cookie = " + cookie);
            // 게시글 이력 쿠키 조회
            if(cookie.getName().equals(cookieName)) {
                
                //logId가 있을 시
                if (findLogIdInCookie(cookie, logId)) {
                    String dateStr = cookie.getValue().split(logId)[1].substring(1, 9);
                    String today = new SimpleDateFormat("yyyyMMdd").format(new Date());

                    // 시간 안지났으면 return false
                    if(dateStr.equals(today)) return true;
                    // 시간 지났으면 return true & 그 밑으로 삭제
                    else {
                        String newValue = cookie.getValue().split(logId)[1].substring(9, cookie.getValue().length());
                        Cookie newCookie = new Cookie(cookieName, newValue);
                        newCookie.setPath("/");
                        newCookie.setMaxAge(24 * 60 * 60);
                        response.addCookie(newCookie);
                        return false;
                    }
                } else {
                    // 쿠키에 logId 없으면 생성 -> return true;
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String formattedDate = formatter.format(date);

                    Cookie newCookie = new Cookie(cookieName, cookie.getValue() + "/" + logId + ":" + formattedDate);
                    cookie.setPath("/");
                    cookie.setMaxAge(24 * 60 * 60); // 30분
                    response.addCookie(newCookie);
                    return false;
                }
            }
        }

        // 게시글 이력 쿠키가 없는 경우 -> 쿠키 생성
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = formatter.format(date);

        Cookie cookie = new Cookie(cookieName, logId + ":" + formattedDate);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60); // 30분
        response.addCookie(cookie);
        return false;
    }

    // postViewed = "{logId}:20241110/"

    private boolean findLogIdInCookie(Cookie cookie, String logId) {
        String cookieValue = cookie.getValue();
        String[] logInfos = cookieValue.split("/");
        
        for (String logInfo : logInfos) {
            String id = logInfo.split(":")[0];
            if (logId.equals(id)) return true;
        }
        return false;
    }
}
