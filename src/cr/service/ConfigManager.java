package cr.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cr.model.Config;

public class ConfigManager {
	
	// Mandatory
	private final static String API_KEY1 = "api.key1";
	private final static String API_KEY2 = "api.key2";
	
	// Option
	private final static String USE_PROXY = "proxy";
	private final static String PROXY_URL = "proxy.url";
	private final static String PROXY_PORT = "proxy.port";

	public static void init() throws CrServiceException {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			Config config = Config.getConfig();

			input = ConfigManager.class.getClassLoader().getResourceAsStream("cr_api.conf");
			
			if ( input == null ) {
				throw new CrServiceException("Can't find file conf/cr_api.conf in classpath");
			}
			// load a properties file
			prop.load(input);

			// Read properties :
			String apiKey1 = prop.getProperty(API_KEY1);
			config.setApiKey1(apiKey1);
			
			String apiKey2 = prop.getProperty(API_KEY2);
			config.setApiKey2(apiKey2);
			

			
			if ( ( apiKey1 == null ) || "".equals(apiKey1) ) {
				throw new CrServiceException("no api key found in  conf/cr_api.conf");
			}
				
			String useProxy = prop.getProperty(USE_PROXY, "false");
			boolean isUseProxy = "true".equals(useProxy);   // Boolean.getBoolean(useProxy);
			config.setProxy(isUseProxy);
			if (isUseProxy) {
				String proxyUrl = prop.getProperty(PROXY_URL);
				config.setProxyHost(proxyUrl);
				String proxyPort = prop.getProperty(PROXY_PORT);
				config.setProxyPort(proxyPort);
				
				// Set proxy ...
				System.setProperty("http.proxyHost", proxyUrl);
				System.setProperty("http.proxyPort", proxyPort);
				System.setProperty("https.proxyHost", proxyUrl);
				System.setProperty("https.proxyPort", proxyPort);
			}
		} catch (IOException e) {
			throw new CrServiceException("Problem during conf int", e );
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
