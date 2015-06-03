package org.micro.web.server.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.Properties;

public class WebServerProperties {

	private static String APP_PROPS_PATH = "conf/app.properties";
	private static String WEB_SERVER_HOST = "webserver.host";
	private static String WEB_SERVER_PORT = "webserver.port";
	private static String WEB_SERVER_STATIC = "webserver.static";

	private static String DEFAULT_HOST = "127.0.0.1";
	private static int DEFAULT_PORT = 8080;

	private static Optional<Properties> optProperties = Optional.empty();

	public static InetSocketAddress getWebServerSocketAddress() {
		return new InetSocketAddress(getWebServerHost(), getWebServerPort());
	}

	/**
	 * Returns the ip Address specified in the properties file
	 */
	public static String getWebServerHost() {
		Properties properties = getProperties();

		String hostAddress = properties.getProperty(WEB_SERVER_HOST);

		if (hostAddress == null || hostAddress.isEmpty()) {
			hostAddress = DEFAULT_HOST;
		}

		return hostAddress;
	}

	public static String getStaticPath() {
		Properties properties = getProperties();
		
		return properties.getProperty(WEB_SERVER_STATIC);
	}

	/**
	 * Returns the port number of http server
	 */
	public static int getWebServerPort() {
		Properties properties = getProperties();

		String portStr = properties.getProperty(WEB_SERVER_PORT);

		Integer port = null;

		if (portStr == null || portStr.isEmpty()) {
			port = DEFAULT_PORT;
		} else {
			port = Integer.valueOf(properties.getProperty(WEB_SERVER_PORT));
		}

		return port;
	}

	private static Properties getProperties() {

		if (!optProperties.isPresent()) {
			readProperties();
		}

		return optProperties.get();
	}

	private static void readProperties() {

		Properties properties = new Properties();

		try {
			properties.load(new BufferedReader(new FileReader(APP_PROPS_PATH)));
			optProperties = Optional.of(properties);
		} catch (IOException ioException) {
			throw new RuntimeException("Error Reading the properties file",
					ioException);
		}

	}
}
