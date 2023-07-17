package com.hit.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

//package com.hit.utils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.model.Article;
import com.hit.server.Request;
import com.hit.server.Response;

//import com.hit.model.Article;

public class CLI extends JFrame {

	enum CLIActions {
		ADD, GET;
	}

	private JButton btnAdd;
	private JButton btnGet;
	private JTextField txtTitle;
	private JTextArea txtContent;

	private JLabel lblStatus;
	private JLabel lblTime;

	private Article article;
	private String title;
	private String content;

	Timer timer;
	private Socket socket;
	private int port;

	public CLI(int port) {
		this.port = port;
		init();
	}

	private void init() {
		setTitle("Articles");
		setResizable(false);
		setLayout(new BorderLayout(5, 5));
		getContentPane().setBackground(new Color(253, 254, 254));

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setPreferredSize(new Dimension(450, 120));
		panel.setBackground(new Color(118, 215, 196));

		this.txtTitle = new JTextField(this.title, 30);
		txtTitle.setBackground(new Color(242, 243, 244));
		this.txtContent = new JTextArea(this.content, 5, 30);
		txtContent.setBackground(new Color(242, 243, 244));

		this.btnAdd = new JButton(CLIActions.ADD.toString());
		this.btnGet = new JButton(CLIActions.GET.toString());
		panel.add(new JLabel("  TITLE"));
		panel.add(txtTitle);
		panel.add(new JLabel("CONTENT"));
		panel.add(txtContent);
		ActionListener addHandler = new HandleAddButton();
		ActionListener getHandler = new HandleGetButton();
		btnAdd.addActionListener(addHandler);
		btnGet.addActionListener(getHandler);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(248, 196, 113));
		buttonsPanel.add(btnAdd);
		buttonsPanel.add(btnGet);

		JPanel statusPannel = new JPanel(new BorderLayout(10, 10));
		statusPannel.setBorder(BorderFactory.createRaisedBevelBorder());

		lblStatus = new JLabel("ready");
		lblStatus.setBorder(BorderFactory.createLoweredBevelBorder());
		lblTime = new JLabel("ready");
		lblTime.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		statusPannel.add(lblStatus, BorderLayout.LINE_START);
		statusPannel.add(lblTime, BorderLayout.LINE_END);

		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				lblTime.setText(new Date().toString());
			}
		}, 0, 1000);

		add(panel, BorderLayout.NORTH);
		add(buttonsPanel, BorderLayout.CENTER);
		add(statusPannel, BorderLayout.SOUTH);

		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.setMinimumSize(new Dimension(460, 160));
		super.pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		CLI cli = new CLI(34567);

	}

	private Response<Article> connect(Request<Article> request) {
		try {
			lblStatus.setText("Working");
			this.socket = new Socket("127.0.0.1", port);

			// BufferedWriter bw = new BufferedWriter(new
			// OutputStreamWriter(socket.getOutputStream()));
			BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

			String jsonOut = encode(request);
			out.println(jsonOut); // write to server

			StringBuffer sb = new StringBuffer(); // read from server
			String data = null;
			while ((data = bf.readLine()) != null) {
				sb.append(data);
			}
			Response<Article> response = decode(sb.toString());

			out.close();
			bf.close();
			return response;

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private String encode(Request<Article> request) {
		String json = new Gson().toJson(request);
		return json;
	}

	private Response<Article> decode(String json) {
		java.lang.reflect.Type ref = new TypeToken<Response<Article>>() {
		}.getType();
		Response<Article> response = new Gson().fromJson(json, ref);
		return response;
	}

	private void controller(CLIActions action) {
		Article art = null;
		Request<Article> req = null;
		Response<Article> response = null;
		switch (action) {
		case ADD:
			art = new Article(txtTitle.getText(), txtContent.getText());
			req = new Request<>(CLIActions.ADD.toString(), art);
			response = connect(req);
			if (response == null) {
				JOptionPane.showMessageDialog(this,
						String.format("Unable to add article with title %s", txtTitle.getText()), "Add results",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			break;
		case GET:
			art = new Article(null, null);
			req = new Request<>(CLIActions.GET.toString(), art);
			response = connect(req);
			if (response == null) {
				JOptionPane.showMessageDialog(this,
						String.format("Unable to retrieve article with title %s", txtTitle.getText()), "Get results",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (response.getStatus().equals("ok")) {
				txtTitle.setText(response.getDataModel().getTitle());
				txtContent.setText(response.getDataModel().getContent());
				lblStatus.setText("Ready");
			} else {
				txtTitle.setText("");
				txtContent.setText("");
				lblStatus.setText("no more Articles");

			}
		}
	}

	private class HandleAddButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller(CLIActions.ADD);
		}

	}

	private class HandleGetButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller(CLIActions.GET);
		}

	}
}
