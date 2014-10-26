package in.BBAT.presenter.dialogs;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		final HttpURLConnectionExample http = new HttpURLConnectionExample();
		
		/*for (int i = 0; i < 100; i++) {
			runThread(http,i);
			if(i%10==0){
				Thread.sleep(5000);
			}
		}*/
		
		
		System.out.println("Testing 1 - Send Http GET request");



		/*System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
		 */
	}

	private static void runThread(final HttpURLConnectionExample http, final int i) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					long t1 = System.currentTimeMillis();
					http.sendGet("dsds "+ i, "gsgrefe "+ i, "grgdvfvad "+ i, "fagfdff");
					System.out.println(i+"  " + (System.currentTimeMillis()-t1));
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		t.start();
	}

	// HTTP GET request
	public void sendGet(String emailAddress, String companyName,String designation,String name) throws Exception {

		String url = "http://uiautomatorbot-mehtab.rhcloud.com/in?mail="+URLEncoder.encode(emailAddress, "UTF-8")+"&name="+URLEncoder.encode(name, "UTF-8")+"&company="+URLEncoder.encode(companyName, "UTF-8")+"&designation="+URLEncoder.encode(designation, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		
	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

}