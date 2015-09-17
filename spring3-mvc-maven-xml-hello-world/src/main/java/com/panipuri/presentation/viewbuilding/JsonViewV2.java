package com.panipuri.presentation.viewbuilding;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class JsonViewV2 implements View {

    private final String viewName;
    /** Constant for data type application/json */
	public static final String APPLICATION_JSON = "application/json";
    public JsonViewV2(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Getter for ContentType callback method of View interface.
     *
     * @return
     */
    public String getContentType() {
        return APPLICATION_JSON;
    }

    /**
     * Renders the POJO as JSON. Also added proprietary custom prefix and suffix
     * to the JSON response.
     *
     * @param model - POJO which needs to be serialized.
     * @param request - HttpServletRequest object.
     * @param response - HttpServletResponse object.
     * @throws Exception - Exception is thrown id any error occurs during the
     * serializing the POJO.
     */
    public void render(Map<String, ?> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map viewNameMap = new HashMap();
        viewNameMap.put("viewName", viewName);
        model.putAll(viewNameMap);
        response.setContentType(APPLICATION_JSON);
        addCacheControlHttpHeaders(response);
        addSecurityControlHttpHeaders(response);
        
        JsonUtil jsonUtil = new JsonUtil(); 
        response.getWriter().write(jsonUtil.renderJsonString(model));
    }
    /**
     * Adds cache control header to the response.
     * @param response HttpServletResponse object.
     */
    public static void addCacheControlHttpHeaders(HttpServletResponse response) {
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1
        response.addHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", -1);
    }
    
    public static void addSecurityControlHttpHeaders(HttpServletResponse response) {

    	response.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
    	response.setHeader("Strict-Transport-Security", "max-age=8640000");
    
    }
}
