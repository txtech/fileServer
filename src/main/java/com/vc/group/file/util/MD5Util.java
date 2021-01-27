package com.vc.group.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5工具类
 * 
 * @author Kyo.Ou 2012-9-17
 * 
 */
public class MD5Util {
	private static Logger logger = Logger.getLogger(MD5Util.class);
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final MessageDigest messageDigest;
	static {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("error get instance of md5", e);
		}
		messageDigest = digest;
	}

	/**
	 * 计算文件的MD5
	 * 
	 * @param fileName
	 *            文件的绝对路径
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5(String fileName) throws IOException {
		File f = new File(fileName);
		return getFileMD5(f);
	}

	/**
	 * 计算文件的MD5，重载方法
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		return getFileMD5(in, true);
	}
	
	/**
	 * 计算文件的MD5，重载方法
	 * @param in 文件输入流
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5(InputStream in, boolean isClose) throws IOException {
		byte[] buf = new byte[1024 * 100];// 100k 缓冲
		int c = -1;
		while ((c = in.read(buf)) > -1) {
			messageDigest.update(buf, 0, c);
		}
		if(isClose)
			in.close();
		return bufferToHex(messageDigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuilder builder = new StringBuilder(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], builder);
		}
		return builder.toString();
	}

	private static void appendHexPair(byte bt, StringBuilder builder) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		builder.append(c0);
		builder.append(c1);
	}
	
}
