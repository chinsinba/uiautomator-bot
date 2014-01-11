package in.bbat.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceHelper
{

	private static HttpClient getHttpClientObject()
	{
		SchemeRegistry localSchemeRegistry = new SchemeRegistry();
		Scheme localScheme1 = new Scheme("https", 443, SSLSocketFactory.getSocketFactory());
		localSchemeRegistry.register(localScheme1);
		Scheme localScheme2 = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		localSchemeRegistry.register(localScheme2);
		BasicClientConnectionManager basicClientConnectionManager = new BasicClientConnectionManager(localSchemeRegistry);
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
		HttpConnectionParams.setSoTimeout(basicHttpParams, 20000);
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient(basicClientConnectionManager, basicHttpParams);
		String host = "";//PreferenceServiceUtil.getValue("com.littleeyelabs.eclipseide.littleeye.proxy.host");
		int port = 0;//PreferenceServiceUtil.getIntValue("com.littleeyelabs.eclipseide.littleeye.proxy.port");
		String protocol = "";//PreferenceServiceUtil.getValue("com.littleeyelabs.eclipseide.littleeye.proxy.protocol");
		if ((host != null) && (!host.isEmpty()) && (protocol != null) && (!protocol.isEmpty()) && (port > 0))
		{
			HttpHost httpHost = new HttpHost(host, port, protocol);
			defaultHttpClient.getParams().setParameter("http.route.default-proxy", httpHost);
		}
		String userName = "";//PreferenceServiceUtil.getValue("com.littleeyelabs.eclipseide.littleeye.proxy.username");
		String pwd = "";//PreferenceServiceUtil.getValue("com.littleeyelabs.eclipseide.littleeye.proxy.password");
		if ((userName != null) && (!(userName).trim().isEmpty()))
		{
			defaultHttpClient.getCredentialsProvider().setCredentials(new AuthScope(host, port), new NTCredentials(userName, pwd, "", ""));
			ArrayList paramList = new ArrayList();
			paramList.add("Digest");
			paramList.add("Basic");
			paramList.add("NTLM");
			defaultHttpClient.getParams().setParameter("http.auth.proxy-scheme-pref", paramList);
		}
		return defaultHttpClient;
	}

	static String executeAndGetResponse(HttpClient paramHttpClient, HttpPost paramHttpPost)throws WebServiceException
	{
		StringBuilder response = new StringBuilder();
		try
		{
			HttpResponse localHttpResponse = paramHttpClient.execute(paramHttpPost);
			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpResponse.getEntity().getContent()));
			String str;
			while ((str = localBufferedReader.readLine()) != null)
				response.append(str);
		}
		catch (Exception localException)
		{
			//      LOG.E("WebServiceHelper", "Could not read the server's output:" + localException.getMessage() + "," + localException.getClass().getName() + ". The Server's response was:" + localStringBuilder.toString(), localException);
			throw new WebServiceException("Could not read the server's output:" + localException.getMessage() + "," + localException.getClass().getName(), localException);
		}
		return response.toString();
	}

	private static void login(HttpClient paramHttpClient)throws ClientProtocolException, IOException
	{
		HashMap<String, String> propMap = new HashMap<String, String>();
		propMap.put("username", "bbat");
		propMap.put("password", "enthupreneurs");
		HttpPost localHttpPost = new HttpPost("http://worm.bbat.in/g2ui/login");
		ArrayList nameValPairList = new ArrayList();
		Iterator localIterator = propMap.keySet().iterator();
		while (localIterator.hasNext())
		{
			String key = (String)localIterator.next();
			nameValPairList.add(new BasicNameValuePair(key, (String)propMap.get(key)));
		}
		localHttpPost.setEntity(new UrlEncodedFormEntity(nameValPairList, "UTF-8"));
		Object localObject = paramHttpClient.execute(localHttpPost);
		EntityUtils.consume(((HttpResponse)localObject).getEntity());
	}

	public static JSONObject makeMultiPartRequest(String paramString, File paramFile, Map<String, String> paramMap)	throws WebServiceException
	{
		HttpClient localHttpClient = getHttpClientObject();
		String str = "";
		try
		{
			login(localHttpClient);
			HttpPost localHttpPost = new HttpPost(paramString);
			MultipartEntity localMultipartEntity = new MultipartEntity();
			localMultipartEntity.addPart("file", new FileBody(paramFile));
			Iterator localIterator = paramMap.keySet().iterator();
			while (localIterator.hasNext())
			{
				String key = (String)localIterator.next();
				localMultipartEntity.addPart(key, new StringBody((String)paramMap.get(key), "text/plain", Charset.forName("UTF-8")));
			}
			localHttpPost.setEntity(localMultipartEntity);
			str = executeAndGetResponse(localHttpClient, localHttpPost);
			JSONObject localObject = new JSONObject(str);
			localHttpClient.getConnectionManager().shutdown();
			return localObject;
		}
		catch (JSONException localJSONException)
		{
			//      LOG.E("WebServiceHelper", "Error constructing JSON from response:" + localJSONException.getMessage() + "The server returned:" + str, localJSONException);
			throw new WebServiceException("Error constructing JSON from response:" + localJSONException.getMessage(), localJSONException);
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			//      LOG.E("WebServiceHelper", "Error constructing string body to send in the upload POST request:" + localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
			throw new WebServiceException("Error constructing JSON from response:" + localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
		}
		catch (ClientProtocolException localClientProtocolException)
		{
			//      LOG.E("WebServiceHelper", "Error with the client protocol:" + localClientProtocolException.getMessage(), localClientProtocolException);
			throw new WebServiceException("Error with the client protocol:" + localClientProtocolException.getMessage(), localClientProtocolException);
		}
		catch (IOException localIOException)
		{
			//      LOG.E("WebServiceHelper", "IOException while uploading the file:" + localIOException.getMessage(), localIOException);
			throw new WebServiceException("IOException while uploading the file:" + localIOException.getMessage(), localIOException);
		}
	}

	public static JSONObject makeRequest(String urlString, String jsonParams)throws WebServiceException
	{
		HttpPost localHttpPost = new HttpPost(urlString);
		try
		{
			StringEntity mimeType = new StringEntity(jsonParams);
			mimeType.setContentType("application/json");
			localHttpPost.setEntity(mimeType);
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			//      LOG.E("WebServiceHelper", "Error with the input JSON:" + localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
			throw new WebServiceException("Error with the input JSON:" + localUnsupportedEncodingException.getMessage() + "," + localUnsupportedEncodingException.getClass().getName(), localUnsupportedEncodingException);
		}
		HttpClient localHttpClient = getHttpClientObject();
		String str = executeAndGetResponse(localHttpClient, localHttpPost);
		JSONObject localJSONObject;
		try
		{
			localJSONObject = new JSONObject(str);
		}
		catch (JSONException localJSONException)
		{
			//      LOG.E("WebServiceHelper", "Could not construct JSON object from server's response:" + localJSONException.getMessage() + "," + localJSONException.getClass().getName() + ". The Server's response was:" + str, localJSONException);
			throw new WebServiceException("Could not construct JSON object from server's response:" + localJSONException.getMessage() + "," + localJSONException.getClass().getName(), localJSONException);
		}
		localHttpClient.getConnectionManager().shutdown();
		return localJSONObject;
	}
}
