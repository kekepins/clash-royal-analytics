package cr.service;

public class QueryResponse {
	
	private final String json;
	private int rateLimit;
	private int rateLimitRemaining;
	private int rateLimitRetryAfter;

	public QueryResponse(String json, String headerRateLimit, String headerRateLimitRemaining, String headerRateLimitRetryAfter) {
		this.json = json;
		
		if ( headerRateLimit != null ) {
			rateLimit = Integer.parseInt(headerRateLimit);
		}
		
		if ( headerRateLimitRemaining != null ) {
			rateLimitRemaining = Integer.parseInt(headerRateLimitRemaining);
		}
		
		if ( headerRateLimitRetryAfter != null ) {
			rateLimitRetryAfter = Integer.parseInt(headerRateLimitRetryAfter);
		}

	}
	
	
	public String getJson() {
		return json;
	}
	
	public int getRateLimit() {
		return rateLimit;
	}


	public int getRateLimitRemaining() {
		return rateLimitRemaining;
	}


	public int getRateLimitRetryAfter() {
		return rateLimitRetryAfter;
	}

}
