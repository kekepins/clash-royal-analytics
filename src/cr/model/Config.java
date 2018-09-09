package cr.model;

import java.util.concurrent.ThreadLocalRandom;

public class Config {
	private static Config config  = new Config();
	
	private String apiKey1;
	private String apiKey2;
	private boolean isProxy = false;
	private String proxyHost;
	private String proxyPort;
	
	
	public static Config getConfig() {
		return config;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getApiKey1() {
		return apiKey1;
	}

	public void setApiKey1(String apiKey1) {
		this.apiKey1 = apiKey1;
	}

	public String getApiKey2() {
		return apiKey2;
	}

	public void setApiKey2(String apiKey2) {
		this.apiKey2 = apiKey2;
	}

	public String getApiKey() {
		//return apiKey2;
		int rand = ThreadLocalRandom.current().nextInt(0, 2);
		
		if ( rand == 0 ) {
			return apiKey1;
		}
		
		return apiKey2;
	}
	
	/*public static void main(String[] args) {
		for (int i = 0; i < 10; i++ ) {
			System.out.println("rand:" + ThreadLocalRandom.current().nextInt(0, 2));
		}
	}*/

}
