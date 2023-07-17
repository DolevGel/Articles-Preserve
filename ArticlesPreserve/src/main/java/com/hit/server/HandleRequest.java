package com.hit.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.model.Article;
import com.hit.service.Controller;

public class HandleRequest implements Runnable {
	private Socket socket;
	private Controller<Article> controller;

	public HandleRequest(Socket socket, Controller<Article> controller) {
		this.socket = socket;
		this.controller = controller;
	}

	@Override
	public void run() {
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

				PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			StringBuffer sb = new StringBuffer();

			int data = in.read();
			while (data != 10) {
				sb.append((char) data);
				data = in.read();
			}
			System.out.println("DEBUG: request" + sb);

			Type ref = new TypeToken<Request<Article>>() {
			}.getType();
			Request<Article> request = new Gson().fromJson(sb.toString(), ref);
			// process response
			Response<Article> response = controller.processRequest(request);

			String jsonOut = new Gson().toJson(response);
			System.out.println("DEBUG: response" + jsonOut);
			writer.println(jsonOut);
		} catch (IOException i) {
		}

	}

}
