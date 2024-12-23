//package com.example.SecurityApp.SecurityApplication.filters;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//
//@Component
//@Order(1)
//public class LoggingFilter extends OncePerRequestFilter {
//    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        Long startTime = System.currentTimeMillis();
//        String requestMethod = request.getMethod();
//        String requestUri = request.getRequestURI();
//
//        ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
//        try {
//            filterChain.doFilter(wrapperRequest, wrapperResponse);
//            logRequestDetails(wrapperRequest);
//        } catch (Exception e) {
//            log.error("Exception occured:{} {}", requestMethod, requestUri, e);
//            throw e;
//        } finally {
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            logResponseDetails(wrapperResponse, elapsedTime);
//            wrapperResponse.copyBodyToResponse();
//        }
//    }
//
//    private void logResponseDetails(ContentCachingResponseWrapper wrapperResponse, long elapsedTime) throws UnsupportedEncodingException {
//        log.info("===OUTGOING Response====");
//        log.info("Http Status:{}", wrapperResponse.getStatus());
//        getHeaders(wrapperResponse);
//        log.info("Body:{}", getResponseBody(wrapperResponse));
//        log.info("Processing time:{}", elapsedTime);
//    }
//
//    private String getResponseBody(ContentCachingResponseWrapper wrapperResponse) throws UnsupportedEncodingException {
//        byte[] content = wrapperResponse.getContentAsByteArray();
//        return content.length > 0 ? new String(content, wrapperResponse.getCharacterEncoding()) : "NO BODY CONTENT";
//    }
//
//    private void logRequestDetails(ContentCachingRequestWrapper wrapperRequest) throws UnsupportedEncodingException {
//        wrapperRequest.getParameterMap();
//        String body = getRequestBody(wrapperRequest);
//        log.info("INCOMIMG REQUEST DETAILS:");
//        log.info("Method:{}", wrapperRequest.getMethod());
//        log.info("URL:{}", wrapperRequest.getRequestURI());
//        log.info("Query Parameters:{}", getQueryParam(wrapperRequest));
//        getHeaders(wrapperRequest);
//        log.info("Body :{}", body);
//    }
//
//    private String getQueryParam(ContentCachingRequestWrapper wrapperRequest) {
//        StringBuilder params=new StringBuilder();
//        Map<String,String[]> parameterMap=wrapperRequest.getParameterMap();
//        parameterMap.forEach((key,values)->
//                params.append(key).append("=").append(String.join(",",values)).append("&"));
//    return params.toString().isEmpty()?"None":params.toString();
//    }
//
//    private String getRequestBody(ContentCachingRequestWrapper wrapperRequest) throws UnsupportedEncodingException {
//        byte[] content = wrapperRequest.getContentAsByteArray();
//        return content.length > 0 ? new String(content, wrapperRequest.getCharacterEncoding()) : "NO BODY CONTENT";
//    }
//
//    private void getHeaders(ContentCachingRequestWrapper wrapperRequest) {
//        List<String> headerName = Collections.list(wrapperRequest.getHeaderNames());
//        headerName.forEach(headName -> {
//            String headerValue = wrapperRequest.getHeader(headName);
//            log.info("Header {}={}", headName, headerValue);
//        });
//    }
//
//    private void getHeaders(ContentCachingResponseWrapper response){
//        response.getHeaderNames().forEach(headerName->{
//            response.getHeaders(headerName).forEach(headerValue->
//                    log.info("Header:{}={}",headerName,headerValue));
//        });
//    }
//
//}
//
//
