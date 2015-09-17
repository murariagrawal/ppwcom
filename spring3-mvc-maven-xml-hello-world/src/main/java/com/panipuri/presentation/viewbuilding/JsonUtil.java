package com.panipuri.presentation.viewbuilding;
import java.util.regex.Pattern;

import flexjson.JSONSerializer;

public class JsonUtil {
	 
    /**
     * JSONSerializer object based on FlexJson library. This is initialize with
     * "exclude" parameter = "*.class". Default implementation includes POJO's
     * class name as one of the JSON data element when the POJO is serialized.
     * This settings will exclude that.
     */
    private JSONSerializer jsonSerializer;
    
    private static final Pattern pattrenEscapedBackquote = Pattern.compile("\\\\\\\\");
    private static final Pattern pattrenEscapedQuoubleQuotePrefixedwithBackquote = Pattern.compile("\\\\\\\"");
    private static final Pattern pattrenForDoubleQuote = Pattern.compile("(\")\"");
    private static final Pattern pattrenEscapedQuoubleQuoteNotPrefixedwithBackquote = Pattern.compile("([^\\\\])\"");
    
    /**
     * Default constructor which initializes FlexJSON library's Json Serializer.
     */
    public JsonUtil() {
        this.jsonSerializer = new JSONSerializer().transform(new ExcludeTransformer(), void.class).exclude("*.class");
    }
    
    /**
     * Serializes provided object into a JSON string.
     * @param model object to be serialized.
     * @return Sting JSON format of the provided object.
     */
    public String renderJsonString(Object model) {
        
        StringBuffer buffer = new StringBuffer();
        jsonSerializer.deepSerialize(model, buffer);
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append(buffer);
        
        //Pattern.compile(regex).matcher(str).replaceAll(repl)
        String s = pattrenEscapedBackquote.matcher(jsonResponse.toString()).replaceAll("\\\\\\\\\\\\\\\\");
        //String s = jsonResponse.toString().replaceAll("\\\\\\\\", "\\\\\\\\\\\\\\\\");
        s = pattrenEscapedQuoubleQuotePrefixedwithBackquote.matcher(s).replaceAll("\\\\\\\\\\\\\\\"");
        //s = s.replaceAll("\\\\\\\"", "\\\\\\\\\\\\\\\"");
        s = pattrenForDoubleQuote.matcher(s).replaceAll("$1\\\\\"");
        s = pattrenEscapedQuoubleQuoteNotPrefixedwithBackquote.matcher(s).replaceAll("$1\\\\\"");
        //s = s.replaceAll("([^\\\\])\"", "$1\\\\\"");
        
        return ("\"" + s + "\"");
    }
    
    /**
     * Serializes provided object into a JSON string. This is static method 
     * implementation of {@link JsonUtil#renderJsonString(java.lang.Object) }. 
     * Is is used by the registered JSP function.
     * 
     * @see #renderJsonString(java.lang.Object) 
     * @param model object to be serialized.
     * @return Sting JSON format of the provided object. 
     * 
     * Serializes provided object into a JSON string.
     */
    public static String transformToJson(Object model) {
        
        JsonUtil jsonUtil = new JsonUtil();
        return jsonUtil.renderJsonString(model);
    }
}
