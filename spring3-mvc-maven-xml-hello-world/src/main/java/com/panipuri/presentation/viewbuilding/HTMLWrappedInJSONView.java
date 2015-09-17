package com.panipuri.presentation.viewbuilding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;

import flexjson.JSONSerializer;
import flexjson.transformer.AbstractTransformer;

public class HTMLWrappedInJSONView extends JstlView {

	 private static final JSONSerializer jsonSerializer = new JSONSerializer().transform(new ExcludeTransformer(), void.class).exclude("*.class");

   private View htmlView;

   public HTMLWrappedInJSONView(View htmlView) {
       this.htmlView = htmlView;
   }

   @Override
   public void render(Map<String, ?> model,
           HttpServletRequest request,
           HttpServletResponse response)
           throws Exception {

       final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response) {
           PrintWriter printWriter = null;
           BufferedServletOutputStream bufferedServletOutputStream = null;

           @Override
           public PrintWriter getWriter() throws IOException {
               if (printWriter == null) {
                   printWriter = new PrintWriter(byteArrayOutputStream, true);
               }
               return printWriter;
           }

           @Override
           public ServletOutputStream getOutputStream() throws IOException {
               if (bufferedServletOutputStream == null) {
                   bufferedServletOutputStream = new BufferedServletOutputStream(byteArrayOutputStream);
               }
               return bufferedServletOutputStream;
           }
       };
       JsonViewV2.addCacheControlHttpHeaders(response);
       JsonViewV2.addSecurityControlHttpHeaders(response);
       htmlView.render(model, request, wrapper);
       wrapper.flushBuffer();

     
     
       String encodedHTML = byteArrayOutputStream.toString();

               
       HybridContentTo hybridContentTo = new HybridContentTo();
       hybridContentTo.setHtmlResponse(encodedHTML);
       hybridContentTo.setKONICHIWA8(System.getProperty(Constants.ACCOUNTS_AGENT, Constants.ACCOUNTS_AGENT_DEFAULT_VALUE));		
       Object additionalData = request.getAttribute(Constants.MIXED_HOME_ADDITIONAL_DATA_REQUEST_ATTRIBUTE_NAME);
       if (additionalData != null) {
           hybridContentTo.setAdditionalData(additionalData);
       }
       if(model.get(Constants.FAILURE_MESSAGE_MODEL) != null) {
       	hybridContentTo.setFailureMessageBean(model.get(Constants.FAILURE_MESSAGE_MODEL));
       }
       StringBuffer buffer = new StringBuffer();
		jsonSerializer.deepSerialize(hybridContentTo, buffer);
       StringBuilder jsonResponse=new StringBuilder();
       jsonResponse.append(Constants.ESCAPED_DOUBLE_QUOTE).append(Constants.WELLSFARGOPROPRIETARY_START).append(buffer)
		.append(Constants.WELLSFARGOPROPRIETARY_END).append(Constants.ESCAPED_DOUBLE_QUOTE);
       try {
           response.getOutputStream().print(jsonResponse.toString());
       } catch (IllegalStateException ise) {
           response.getWriter().append(encodedHTML);
       }

   }

   private static class HybridContentTo {

       private String htmlResponse;
       private Object additionalData;
       private String KONICHIWA8;
       private Object failureMessageBean;
       public String getHtmlResponse() {
           return htmlResponse;
       }

       public void setHtmlResponse(String htmlResponse) {
           this.htmlResponse = htmlResponse;
       }

       public Object getAdditionalData() {
           return additionalData;
       }

       public void setAdditionalData(Object additionalData) {
           this.additionalData = additionalData;
       }
		
		public String getKONICHIWA8() {
			return KONICHIWA8;
		}

		public void setKONICHIWA8(String kONICHIWA8) {
			KONICHIWA8 = kONICHIWA8;
		}

		public Object getFailureMessageBean() {
			return failureMessageBean;
		}

		public void setFailureMessageBean(Object failureMessageBean) {
			this.failureMessageBean = failureMessageBean;
		}
		
   }

   static class ExcludeTransformer extends AbstractTransformer {

       @Override
       public Boolean isInline() {
           return true;
       }

       public void transform(Object object) {
           // Do nothing, null objects are not serialized.
           return;
       }
   }

}
