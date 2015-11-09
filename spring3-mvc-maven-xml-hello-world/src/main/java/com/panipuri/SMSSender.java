package com.panipuri;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SMSSender {

	public static void main(String[] args) {
        try {
                String recipient = "9673142211";
                String message = " Greetings from Mr. Gupta! Have a nice day!";
                String username = "9673142211";
                String password = "c859ba4";
                String originator = "+440987654321";

                String requestUrl  = "http://bhashsms.com/api/sendmsg.php?"+
                					 "user=" + URLEncoder.encode(username, "UTF-8") +
                					 "&pass=" + URLEncoder.encode(password, "UTF-8") +
                					 "&sender=" + URLEncoder.encode("test", "UTF-8") +
                					 "&phone=" + URLEncoder.encode(recipient, "UTF-8") +                					
                					 "&text=" + URLEncoder.encode(message, "UTF-8") +
                					 "&priority=" + URLEncoder.encode("Priority", "UTF-8") +
                					 "&stype=smstype" ;

                System.out.println(requestUrl);

                URL url = new URL(requestUrl);
                HttpURLConnection uc = (HttpURLConnection)url.openConnection();

                System.out.println(uc.getResponseMessage());

                uc.disconnect();

        } catch(Exception ex) {
                System.out.println(ex.getMessage());

        }
        
	}

}