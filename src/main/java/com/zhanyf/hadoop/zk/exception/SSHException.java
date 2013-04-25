package com.zhanyf.hadoop.zk.exception;

public class SSHException extends Exception {
	private static final long serialVersionUID = 1L;

	public SSHException() {
		super();
	}

	public SSHException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSHException(String message) {
		super(message);
	}

	public SSHException(Throwable cause) {
		super(cause);
	}

}
