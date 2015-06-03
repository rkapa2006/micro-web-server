package org.micro.web.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.micro.web.server.util.WebServerProperties;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This method sends static content to the client.
 */
public class StaticContentHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		URI requestUri = httpExchange.getRequestURI();
		String path = requestUri.getPath();

		File resourceFile = getResourceFile(path);
		if (!resourceFile.exists()) {
			httpExchange.sendResponseHeaders(404, -1);
		} else {
			serveFile(httpExchange, resourceFile);
		}

	}

	private File getResourceFile(String path) {
		if ("/".equals(path)) {
			path = "index.html";
		}

		String parent = WebServerProperties.getStaticPath();

		return new File(parent, path);
	}

	private void serveFile(HttpExchange httpExchange, File resourceFile) {

		try (
				BufferedInputStream inputStream = new BufferedInputStream(
				new FileInputStream(resourceFile));
				OutputStream responseOutputStream = httpExchange
						.getResponseBody();
				) {

			httpExchange.sendResponseHeaders(200, 0);
			Path resourcePath = Paths.get(resourceFile.getAbsolutePath());
			Files.copy(resourcePath, responseOutputStream);

		} catch (IOException ioException) {
			sendServerError(httpExchange);
		}

	}

	private void sendServerError(HttpExchange httpExchange) {

		try (OutputStream responseOutput = httpExchange.getResponseBody()) {
			httpExchange.sendResponseHeaders(500, -1);
		} catch (IOException ioException) {
			throw new RuntimeException("Error Writing the server response",
					ioException);
		}
	}

}
