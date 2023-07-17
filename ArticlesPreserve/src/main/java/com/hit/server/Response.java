package com.hit.server;

public class Response<T> {

	private String status;
	private T dataModel;

	public T getDataModel() {
		return dataModel;
	}

	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}

	public Response(String status, T dataModel) {
		this.status = status;
		this.dataModel = dataModel;
	}
	
	public Response() {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", dataModel=" + dataModel + "]";
	}


	
}
