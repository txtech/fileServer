package com.vc.group.file.service;

import java.io.InputStream;

import com.vc.group.file.constant.FileType;
import com.vc.group.file.entity.FileInfo;
import com.vc.group.file.exception.FileServerException;

/**
 * 文件服务接口,主要提供:
 * 1.文件写入服务
 * 2.文件url查询服务
 * @类名称:FileService.java
 * @时间:2017年5月23日下午2:06:23
 * @作者:lihai 
 * @版权:公司 Copyright (c) 2017
 */
public interface FileServerManager {
    
	/**
	 * @描述:以块的方式保存一个文件
	 * @作者:lihai 
	 * @时间:2017年5月23日 下午2:06:09
	 */
    long saveFile(byte[] content,String fileExt,String fileName,FileType fileType) throws FileServerException;


    /**
     * 可使用扩展名的文件系统保存接口，不需要传file id时使用
     * @param fileType 文件枚举类型
     * @param is 文件输入流
     * @param fileExt 文件扩展名
     * @return 文件系统生成的fileId，需调用方保存，以寻找对应url
     */
    long saveFile(InputStream is,String fileExt,String fileName,FileType fileType)throws FileServerException;

    
	/**
	 * @描述:列出用以读取文件的一组url
	 * @作者:lihai 
	 * @时间:2017年5月23日 下午2:07:04
	 */
	String findFileUrl(Long fileId,FileType fileType)throws FileServerException;
	
	/**
	 * 根据fileId获取文件 MD5
	 * @param fileId 对应文件的fileId
	 * @param fileType 对应的FileType枚举类型
	 * @return
	 */
	String findFileMd5(Long fileId, FileType fileType)throws FileServerException;
	
	/**
	 * @描述:删除文件
	 * @作者:lihai 
	 * @时间:2017年5月24日 下午2:15:38
	 */
	int deleteFile(Long fileId);
	
	/**
	 * @描述:查看文件信息
	 * @作者:lihai 
	 * @时间:2017年5月24日 下午2:15:45
	 */
	FileInfo findFileInfo(Long fileId,FileType fileType)throws FileServerException;
	
}
