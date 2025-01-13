package yyytir777.persist.domain.log.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class ViewCountValidator {

    private static final String COOKIE_NAME = "postViewed";
    private static final int COOKIE_MAX_AGE = 24 * 60 * 60; // 1 day in seconds
    private static final String DATE_FORMAT = "yyyyMMdd-HHmm";

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    // 해당 게시글을 조회한게 유효한지 체크 (조회한 날짜 내에서만 유효)
    public boolean hasViewedInCoookie(HttpServletRequest request, HttpServletResponse response, Long logId) {

        Cookie viewedCookie = getCookie(request);

        if (viewedCookie != null) {
            if(isLogIdInCookie(viewedCookie, logId)) {
                return checkAndUpdateCookie(viewedCookie, response, logId);
            } else {
                updateCookieWithNewLogId(viewedCookie, response, logId);
                return false;
            }
        } else {
            createNewCookie(response, logId);
            return false;
        }
    }
    
    // getCookies()에서 특정 이름의 쿠키를 찾음
    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return (cookies == null) ? null : Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                        .findFirst()
                        .orElse(null);
    }

    // cookie에 특정 logId가 포함되어있는지 확인
    private boolean isLogIdInCookie(Cookie cookie, Long logId) {
        return Arrays.stream(cookie.getValue().split("/"))
                .anyMatch(entry -> entry.startsWith(logId + ":"));
    }

    // 쿠키 날짜를 확인하고 필요시 업데이트하여 조회수를 막는 로직
    private boolean checkAndUpdateCookie(Cookie cookie, HttpServletResponse response, Long logId) {
        String today = getCurrentDate();
        String[] entries = cookie.getValue().split("/");

        for (String entry : entries) {
            String[] parts = entry.split(":");
            if(parts[0].equals(logId)) {
                if(parts[1].equals(today)) return true;
                else {
                    updateCookieWithLogId(cookie, response, logId);
                    return false;
                }
            }
        }
        return false;
    }

    // logId:yyyyMMdd-HHmm/
    // 만료된 logId를 없애고 해당 logId 새로 추가하는 로직 (시간 순서대로 정렬되어있으므로 조회하지 않는 logId도 한꺼번에 정리 가능)
    private void updateCookieWithLogId(Cookie cookie, HttpServletResponse response, Long logId) {
        String stringLogId = logId.toString();
        String deletedExpireLogIdInValue = cookie.getValue().split(stringLogId)[1].substring(DATE_FORMAT.length() + 1);
        String updateValue = deletedExpireLogIdInValue + "/" + logId + ":" + getCurrentDate();
        addCookie(response, updateValue);
    }
    
    //새로 logId를 추가하여 쿠키 추가
    private void updateCookieWithNewLogId(Cookie cookie, HttpServletResponse response, Long logId) {
        String updateValue = cookie.getValue() + "/" + logId + ":" + getCurrentDate();
        addCookie(response, updateValue);
    }
    
    // 새로운 쿠키 생성하는 로직 (logId 처음으로 정의)
    private void createNewCookie(HttpServletResponse response, Long logId) {
        addCookie(response, logId + ":" + getCurrentDate());
    }

    private void addCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(cookie);
        response.setCharacterEncoding("UTF-8");
    }

    // 현재 날짜(yyyyMMdd) 반환
    private String getCurrentDate() {
        return dateFormatter.format(new Date());
    }
}