package cr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.model.Config;

public class Query <T> {
	
	private final static String HEADER_X_RATE_LIMIT = "x-ratelimit-limit";
	
	private final static String HEADER_X_RATE_LIMIT_REMAINING = "x-ratelimit-remaining";
	
	private final static String HEADER_X_RATE_LIMIT_RETRY_AFTER = "x-ratelimit-retry-after";
	
	private String[] params = null;
	
	private Map<String, List<String>> queryParams = new HashMap<>(); 
	
	private String url;
	private final ObjectMapper objectMapper;
	private Class<T> type;
	private boolean isArray = false;
	private boolean isJsonResponse = true;
	
	public Query(String url,  Class<T> type) {
		
		this.url = url;
		this.type = type;
		this.objectMapper = new ObjectMapper();
		this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		if ( type.isArray() ) {
			isArray = true;
		}

	}

	protected Query<T> withParam(String param) {
		params = new String[] {param};
		return this;
	}
	
	protected Query<T> withResponseNotJson() {
		this.isJsonResponse = false;
		return this;
	}
	
	public Query<T> withExcludes(List<String> excludes) {
		queryParams.put("excludes", excludes);
		return this;
	}
	
	public Query<T> withKeys(List<String> keys) {
		queryParams.put("keys", keys);
		return this;
	}
	
	public Query<T> withPage(int page) {
		
		if ( isArray ) { 
			queryParams.put("page", Arrays.asList("" + page) );
		}
		
		return this;
	}
	
	public Query<T> withMax(int max) {
		
		if ( isArray ) { 
			queryParams.put("max", Arrays.asList("" + max) );
		}
		
		return this;
	}

	
	protected String getUrl() {
		String resUrl = null;
		if ( params != null ) {
			resUrl =  String.format(this.url, (Object[]) params);
		}
		else {
			resUrl = this.url;
		}
		
		if ( !queryParams.isEmpty() ) {
			resUrl += "?";
			boolean first = true;
			for (Map.Entry<String, List<String>> queryParam : queryParams.entrySet()) {
				
				if ( !first ) {
					resUrl += "&";
				}
				
				resUrl += queryParam.getKey()  + "=" + StringUtils.join(queryParam.getValue(), ",");
				
				first = false;
			}
		}
		
		return resUrl;
	}
	

	public T execute() throws CrServiceException {
		
		String url = getUrl();
		QueryResponse response = executeGetQuery(url);
		try { 
			if ( isJsonResponse ) {
				return  objectMapper.readValue(response.getJson(), type);
			}
			return (T) response.getJson();
		} catch (IOException e) {
			throw new CrServiceException("Deserialisation Pbm ", e );
		}
	}
	
	/**
	 * Excecute a http get and return the json
	 * @throws IOException 
	 * @throws CrServiceException 
	 */
	private QueryResponse executeGetQuery(String strUrl) throws CrServiceException {

		try {
			long start = System.currentTimeMillis();
			URL url = new URL(strUrl);
			
			//System.out.println("Executing:" + strUrl);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			String key= Config.getConfig().getApiKey();
			conn.setRequestProperty("auth", key);
			
			if (conn.getResponseCode() != 200) {
				System.out.println("Error with query [" + strUrl + "] code " + conn.getResponseCode() );
				System.out.println( HEADER_X_RATE_LIMIT  + ":" + conn.getHeaderField(HEADER_X_RATE_LIMIT) );
				System.out.println( HEADER_X_RATE_LIMIT_REMAINING + ":" + conn.getHeaderField(HEADER_X_RATE_LIMIT_REMAINING) );
				System.out.println( HEADER_X_RATE_LIMIT_RETRY_AFTER + ":" + conn.getHeaderField(HEADER_X_RATE_LIMIT_RETRY_AFTER));
		
			}
			else {
				System.out.println("#" + Thread.currentThread().getId()  + ":Query OK [" + strUrl + "] in " +  (System.currentTimeMillis() - start) + " ms");
			}
	
			if (conn.getResponseCode() == 400) {
				throw new CrServiceException("Bad Request -- Your request sucks. (" + strUrl + ")");
			}
			
			if (conn.getResponseCode() == 401) {
				throw new CrServiceException("Unauthorized -- No authentication was provided, or key invalid.");
			}
			
			if (conn.getResponseCode() == 404) {
				throw new CrServiceException("Not Found -- The specified player / clan cannot be found. Could be invalid tags.");
			}

			if (conn.getResponseCode() == 500) {
				throw new CrServiceException("Internal Server Error -- We had a problem with our server. Try again later.["  + url + "]");
			}
			
			if (conn.getResponseCode() == 503) {
				throw new CrServiceException("Service Unavailable -- We're temporarily offline for maintenance. Please try again later0. ["  + url + "]");
			}
			
			if (conn.getResponseCode() == 522) {
				throw new CrServiceException("Service Unavailable -- We're temporarily offline. Please try again later.");
			}
			
			if (conn.getResponseCode() != 200) {
				throw new CrServiceException("Error code not 200 : HTTP error code : " + conn.getResponseCode());
			}
			
			
	
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	
			String output;
			StringBuffer outputBuffer = new StringBuffer();
			//System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				outputBuffer.append(output);
			}
	
			conn.disconnect();
			//System.out.println(outputBuffer);
			
			String headerRateLimit = conn.getHeaderField(HEADER_X_RATE_LIMIT);
			String headerRateLimitRemaining = conn.getHeaderField(HEADER_X_RATE_LIMIT_REMAINING);
			String headerRateLimitRetryAfter = conn.getHeaderField(HEADER_X_RATE_LIMIT_RETRY_AFTER);
			
			return new QueryResponse(outputBuffer.toString(), headerRateLimit, headerRateLimitRemaining, headerRateLimitRetryAfter);
		}
		catch(IOException ioe) {
			throw new CrServiceException("Iternal Error", ioe);
		}

	}
	 
}
