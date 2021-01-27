package com.vc.group.file.util;

import java.io.ByteArrayInputStream;

import com.alibaba.druid.util.StringUtils;
import com.vc.group.file.constant.FileType;
import com.vc.group.file.entity.FileNode;

/**
 * 文件服务器路径分割
 * @类名称:PathUtil.java
 * @时间:2017年5月23日下午12:07:25
 * @作者:lihai 
 * @版权:公司 Copyright (c) 2017
 */
public class FileServerUtil {
	private static final String protocol = "http://";
	private static final String https = "https://";
	private static final int oldGroupId = 1;

	/**
	 * @描述: 将一个整数倒序分割成子目录字符串，倒序分割的子目录字符串  eg 12345->/45/23/1/,或null,若i<0
	 * @作者:lihai 
	 * @时间:2017年5月23日 下午12:07:57
	 */
	public static String splitLong(Long l) {
		if (l == null || l < 0) {
			return null;
		}
		String whole = l.toString();
		int len = whole.length();
		StringBuilder builder = new StringBuilder();
		builder.append('/');
		int idx = len;
		while (idx >= 2) {
			builder.append(whole.substring(idx - 2, idx)).append('/');
			idx -= 2;
		}
		builder.append(whole.substring(0, idx));
		if (idx > 0) {
			builder.append('/');
		}
		return builder.toString();
	}

	/**
	 * @描述:构建节点地址
	 * @作者:lihai 
	 * @时间:2017年5月23日 下午12:08:32
	 */
	public static String buildNodeUrl(FileNode node) {
		String httpType = node.getFileType().equals(FileType.PLIST.name())?https:protocol;
		StringBuilder builder = new StringBuilder(httpType);
		builder.append(node.getHost());
		Integer port = node.getPort();
		if (port != null && !port.equals(80)) {
			builder.append(':').append(node.getPort());
		}
		/*String nodePath = node.getPath();*/
		//builder.append(nodePath);
		Integer groupId = node.getNodeId();
		if (groupId != oldGroupId){
			/*if (!nodePath.endsWith("/")) {
				builder.append('/');
			}*/
			builder.append(groupId);
		}
		return builder.toString();
	}
	
	/**
	 * @描述:提取文件ID
	 * @作者:lihai 
	 * @时间:2017年5月23日 下午12:09:10
	 */
	public static Long extractFileId(String url) {
		String suffix = url.substring(url.lastIndexOf("/") + 1);
		int dotIndex = -1;
		if((dotIndex = suffix.indexOf(".")) > -1) {
			return Long.parseLong(suffix.substring(0, dotIndex));
		} else {
			return Long.parseLong(suffix);
		}
		
	}
	
   public static class BytesWrapper extends ByteArrayInputStream {
        public BytesWrapper(byte[] buf) {
            super(buf);
        }
        public byte[] getBytes() {
            return buf;
        }
    }
}
