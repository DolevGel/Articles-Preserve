package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.algorithm.FIFOAlgoCache;
import com.hit.model.Article;
import com.hit.service.Controller;
import com.hit.service.Service;


public class Server implements Runnable{
	int port;
	Executor threadpool;
	Thread serverThread;
	private static Service service = new Service(new FIFOAlgoCache(50));;

	public Server(int port) {
		this.port = port;
		this.threadpool = Executors.newFixedThreadPool(5);
		this.serverThread = new Thread(this);
		this.serverThread.start();
	}
	
	public static Service getService() {
		return service;
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();

				System.out.println("New client connected");

				HandleRequest req = new HandleRequest(socket, new Controller<Article>());

				threadpool.execute(req);
			}

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1)
			return;

		int port = Integer.parseInt(args[0]);

	}
}
