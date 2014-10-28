package com.yan.subway.network;

public class ResponseMessage {
	public String message;
	public int code;
	public ResponseMessage(String m, int c) {
		message = m;
		code = c;
	}
}
