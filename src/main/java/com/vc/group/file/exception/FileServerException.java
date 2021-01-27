package com.vc.group.file.exception;

/**
 * 保存文件异常类
 * @类名称:FileSaveException.java
 * @时间:2017年5月23日下午12:05:13
 * @作者:lihai 
 * @版权:公司 Copyright (c) 2017
 */
public class FileServerException extends RuntimeException {
	public FileServerException() {
		super();
	}

	public FileServerException(String msg) {
		super(msg);
	}

	public FileServerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	private static final long serialVersionUID = 1L;
}
