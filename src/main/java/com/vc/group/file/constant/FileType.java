package com.vc.group.file.constant;

/**
 * 文件类型
 * 
 * @author Kyo.Ou
 * 
 */
public enum FileType {
	FILE(".FILE"),
	IMAGE(".IMAGE"),
	PLIST(".PLIST");
	
	private FileType(String extName) {
		this.extName = extName;
	}

	private String extName;

	public String getExtName() {
		return extName;
	}
}
