package com.booktown.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class GetRequestJsonUtils {
    static Gson gson = new Gson();
    public static HashMap<String,String> getRequestJson(HttpServletRequest request) throws IOException {
        String json = getRequestJsonString(request);
        JsonElement jsonElement = JsonParser.parseString(json);
        TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>(){};
        return gson.fromJson(json, typeToken.getType());
    }
    /***
     * 获取 request 中 json 字符串的内容
     *
     * @return : <code>byte[]</code>
     */
    public static String getRequestJsonString(HttpServletRequest request)
            throws IOException {
        String submitMethod = request.getMethod();
        // GET
        if (submitMethod.equals("GET")) {
            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readLen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

//    public static String getRequestPayload(HttpServletRequest request) throws IOException {
//        BufferedReader reader = request.getReader();
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//        String data = sb.toString();
//        return data;
//    }
//
//    public static HashMap getRequestContent(HttpServletRequest request) throws IOException {
//        String data = getRequestPayload(request);
//        return gson.fromJson(data,HashMap.class);
//    }


}
