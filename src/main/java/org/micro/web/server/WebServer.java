package org.micro.web.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.micro.web.server.util.WebServerProperties;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

	public static void main(String[] args) throws IOException {
		WebServer webServer = new WebServer();

		HttpServer httpServer = webServer.createAndStartWebServer();

		httpServer.start();
	}

	private HttpServer createAndStartWebServer() throws IOException {
		InetSocketAddress serverSocketAddress = WebServerProperties
				.getWebServerSocketAddress();

		HttpServer httpServer = HttpServer.create(serverSocketAddress, 20);
		
		StaticContentHandler contentHandler = new StaticContentHandler();
		httpServer.createContext("/", contentHandler);

		return httpServer;
	}

	public HttpHandler getStaticContentHandler() {

		return new StaticContentHandler();
	}
}

