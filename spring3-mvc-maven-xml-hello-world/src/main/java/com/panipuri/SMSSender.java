package com.panipuri;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.panipuri.vo.StatusVo;

public class SMSSender {

	public static void main(String[] args) {
		try {
			String recipient = "9673142211";
			String message = " Greetings from Mr. Gupta! Have a nice day!";
			String username = "9673142211";
			String password = "c859ba4";
			String requestUrl = "http://bhashsms.com/api/sendmsg.php?" + "user=" + URLEncoder.encode(username, "UTF-8")
					+ "&pass=" + URLEncoder.encode(password, "UTF-8") + "&sender="
					+ URLEncoder.encode("PANIPR", "UTF-8") + "&phone=" + URLEncoder.encode(recipient, "UTF-8")
					+ "&text=" + URLEncoder.encode(message, "UTF-8") + "&priority=" + URLEncoder.encode("ndnd", "UTF-8")
					+ "&stype=normal";

			System.out.println(requestUrl);

			URL url = new URL(requestUrl);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();

			System.out.println(uc.getResponseMessage());

			uc.disconnect();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}

	}

	public StatusVo sendCustomerSMS(String recipient, String message) {
		try {
			
			String username = "9673142211";
			String password = "c859ba4";
			String requestUrl = "http://bhashsms.com/api/sendmsg.php?" + "user=" + URLEncoder.encode(username, "UTF-8")
					+ "&pass=" + URLEncoder.encode(password, "UTF-8") + "&sender="
					+ URLEncoder.encode("PANIPR", "UTF-8") + "&phone=" + URLEncoder.encode(recipient, "UTF-8")
					+ "&text=" + URLEncoder.encode(message, "UTF-8") + "&priority=" + URLEncoder.encode("ndnd", "UTF-8")
					+ "&stype=normal";

			
			URL url = new URL(requestUrl);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();

			uc.getResponseMessage();
			uc.disconnect();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}
		return new StatusVo();
	}
	public StatusVo sendAdminSMS(String recipient, String message) {
		try {
			
			String username = "9673142211";
			String password = "c859ba4";
			String requestUrl = "http://bhashsms.com/api/sendmsg.php?" + "user=" + URLEncoder.encode(username, "UTF-8")
					+ "&pass=" + URLEncoder.encode(password, "UTF-8") + "&sender="
					+ URLEncoder.encode("PANIPR", "UTF-8") + "&phone=" + URLEncoder.encode(recipient, "UTF-8")
					+ "&text=" + URLEncoder.encode(message, "UTF-8") + "&priority=" + URLEncoder.encode("ndnd", "UTF-8")
					+ "&stype=normal";

			
			URL url = new URL(requestUrl);
			HttpURLConnection uc = (HttpURLConnection) url.openConnection();

			uc.getResponseMessage();
			uc.disconnect();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}
		return new StatusVo();
	}

}