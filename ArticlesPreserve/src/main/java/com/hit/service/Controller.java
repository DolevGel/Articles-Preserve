package com.hit.service;

import com.hit.model.Article;
import com.hit.server.Request;
import com.hit.server.Response;
import com.hit.server.Server;

public class Controller<T> {

	private Service service;

	public Controller() {
		service = Server.getService();
	}

	public Response<Article> processRequest(Request<Article> req) {
		Response<Article> response = new Response<>();
		switch (req.getHeaders().getAction()) {
		case "ADD":
			boolean isAdded = service.add(req.getBody());
			String addStatus = String.valueOf(isAdded);
			response.setStatus(addStatus);
			break;
		case "GET":

			Article a = service.get();
			if (a == null) {
				response.setStatus("nok");
			} else {
				response.setStatus("ok");
			}
			response.setDataModel(a);
			break;
		default:
			return response;
		}

		return response;
	}
}
