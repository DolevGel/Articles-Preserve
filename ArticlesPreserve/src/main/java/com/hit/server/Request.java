package com.hit.server;

public class Request <T>{

	private Header headers;
	private T body;
	
	
	
	public Request(Header headers, T body) {
		super();
		this.headers = headers;
		this.body = body;
	}
	
	public Request(String action, T body) {
		this.headers = new Header(action);
		this.body = body;
	}
	
	public Header getHeaders() {
		return headers;
	}

	public void setHeaders(Header headers) {
		this.headers = headers;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	

	@Override
	public String toString() {
		return "Request [headers=" + headers + ", body=" + body + "]";
	}



	public class Header{
		public String action;
		public Header(String action) {
			this.action = action;
		}
		
		public String getAction() {
			return this.action;
		}
		
		public void setAction(String action) {
			this.action = action;
		}
		
		@Override
		public String toString() {
			return "[ header: " + this.action + "]";
		}
	}
	
	public class Body{
		public String name;
		public String content;
		public Body(String name, String content) {
			this.name = name;
			this.content = content;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getContent() {
			return this.content;
		}
		
		public void setContent(String content) {
			this.name = content;
		}
		
		@Override
		public String toString() {
			return "[ body: {name:" + this.name + ", content:" + this.content + "}]";
		}
	}
}
