package com.panipuri.presentation.viewbuilding;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class CustomViewResolver  extends InternalResourceViewResolver{

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        
        
		if (viewName != null && (viewName.startsWith("forward:") || (viewName.startsWith("redirect:")))) {
            View forwardView = super.resolveViewName(viewName, locale);            
            return forwardView;
        }
        
        
        String modifiedViewName = null;
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();        
        String formatParameter = request.getParameter("F");
        if (formatParameter != null && formatParameter.equals("J")) {
            return new JsonViewV2(viewName);
        } else if (formatParameter != null && (formatParameter.equals("H") || formatParameter.equals("M"))) {
            if (viewName.startsWith("/")) {
                modifiedViewName = "/fragments" + viewName;
            } else {
                modifiedViewName = "/fragments/" + viewName;
            }
        } else {
            if (viewName.startsWith("/")) {
                modifiedViewName = "/WEB-INF/views" + viewName+".jsp";
            } else {
                modifiedViewName = "/WEB-INF/views/" + viewName+".jsp";
            }
        }
           
        if (modifiedViewName == null) {
            return null;
        } else {
            if (formatParameter != null && formatParameter.equals("M")) {
               return new HTMLWrappedInJSONView(super.resolveViewName(modifiedViewName, locale));
            } else if (formatParameter != null && formatParameter.equals("H")) {
               return super.resolveViewName(modifiedViewName, locale);
            } else {
                return super.resolveViewName(modifiedViewName, locale);
            }
        }
    }
    

}
