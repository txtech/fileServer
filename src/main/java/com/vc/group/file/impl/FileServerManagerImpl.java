package com.vc.group.file.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vc.group.file.constant.FileType;
import com.vc.group.file.entity.FileInfo;
import com.vc.group.file.entity.FileNode;
import com.vc.group.file.exception.FileServerException;
import com.vc.group.file.service.FileServerManager;
import com.vc.group.file.service.FileService;
import com.vc.group.file.util.FileServerUtil;
import com.vc.group.file.util.FileServerUtil.BytesWrapper;
import com.vc.group.file.util.MD5Util;

/**
 * @类名称:FileServerManagerImpl.java
 * @时间:2017年5月23日下午10:29:00
 * @作者:lihai 
 * @版权:公司 Copyright (c) 2017
 */
@Service
public class FileServerManagerImpl implements FileServerManager {
    private Random random = new Random();
    
    @Autowired
    private FileService fileServer;

    @Override
    public long saveFile(InputStream is,String fileExt,String fileName,FileType fileType) throws FileServerException{
        long fileId = fileServer.getNextFileId();
        saveFile(fileId, is, fileExt,fileName,fileType);
        return fileId;
    }
	    
	@Override
	public long saveFile(byte[] content,String fileExt,String fileName,FileType fileType) throws FileServerException{
	    long fileId =  fileServer.getNextFileId();
	    saveFile(fileId, new BytesWrapper(content), fileExt,fileName,fileType);
	    return fileId;
	}

    @Override
    public String findFileUrl(Long fileId,FileType fileType) throws FileServerException{
        return findReadUrl(fileId, fileType);
    }

    @Override
    public String findFileMd5(Long fileId, FileType fileType) throws FileServerException{
        FileInfo mapping = fileServer.findFileByFileId(new FileInfo(fileId, fileType.name()));
        if (mapping == null) {
            return null;
        }
        return mapping.getFileMd5();
    }

    //保存文件
    @SuppressWarnings("all")
    public String saveFile(Long fileId,  InputStream is, String fileExt,String fileName,FileType fileType) throws FileServerException{
        if (fileId == null || fileId < 0) {
            throw new IllegalArgumentException("fileId must>0");
        }
        if (is == null) {
            throw new IllegalArgumentException("inputStream must not null!");
        }
        FileNode writeNode = findWriteNode(fileType);
        String rootDir = writeNode.getMountPath();
        String fileDir = buildFilePath(fileId, fileType);
        File filePath = new File(rootDir, fileDir);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        
        String ext = StringUtils.isBlank(fileExt)?fileType.getExtName():(fileExt.startsWith(".")?fileExt:"."+fileExt);
        File saveFile = new File(filePath, fileId + ext);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFile);
        } catch (FileNotFoundException e) {
            throw new FileServerException(saveFile.getAbsolutePath() + "not found");
        }
        int total = 0;
        try {
            if (is instanceof BytesWrapper) {
                byte[] fileContent = ((BytesWrapper) is).getBytes();
                fos.write(fileContent);
                total = fileContent.length;
            } else {
                byte[] buf = null;
                if (fileType == FileType.FILE) {
                    // 500k buffer for apk file
                    buf = new byte[1024 * 500];
                } else {
                    // 10k buffer for image file
                    buf = new byte[1024 * 10];
                }
                int c = 0;
                while ((c = is.read(buf)) > -1) {
                    fos.write(buf, 0, c);
                    total += c;
                }
            }
            fos.close();
        } catch (Exception e) {
            throw new FileServerException("error save file " + saveFile.getAbsolutePath());
        }
        String fileMd5 = null;
        try {
            fileMd5 = MD5Util.getFileMD5(saveFile);
        } catch (IOException e) {
        }
        fileServer.decreaseNodeFreeSpace(new FileNode(writeNode.getNodeId(), total));
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setFileSize(total);
        fileInfo.setType(fileType.name());
        fileInfo.setNodeId(writeNode.getNodeId());
        fileInfo.setFileMd5(fileMd5);
        fileInfo.setFileFormat(ext);
        fileInfo.setName(fileName);
        String url = buildFileUrl(writeNode, fileId, fileType, ext);
        fileInfo.setDownloadUrl(url);
        fileServer.saveFileInfo(fileInfo);
        return url;
    }
    
    //找到写入服务节点
	private FileNode findWriteNode(FileType fileType) throws FileServerException{
		List<FileNode> nodes = fileServer.findAllFileNodeList(fileType.name());
		if (nodes == null || nodes.size() == 0) {//存储已满
			throw new FileServerException("no space left");
		}
		//随机选择一台存储节点
		int index = random.nextInt(nodes.size());
		FileNode writeNode = nodes.get(index);
		return writeNode;
	}

	private String buildFileUrl(FileNode node, Long fileId, FileType fileType, String fileExt) throws FileServerException{
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(FileServerUtil.buildNodeUrl(node))
				.append(buildFilePath(fileId, fileType)).append(fileId)
				.append(StringUtils.isBlank(fileExt)?fileType.getExtName():fileExt);
		return urlBuilder.toString();
	}

	private String buildFilePath(Long fileId, FileType fileType) throws FileServerException{
		StringBuilder builder = new StringBuilder("/").append(fileType.name());
		builder.append(FileServerUtil.splitLong(fileId));
		return builder.toString();
	}

	//读取文件URL
	public String findReadUrl(Long fileId, FileType fileType) throws FileServerException{
		FileInfo fileinfo = fileServer.findFileByFileId(new FileInfo(fileId, fileType.name()));
		if (fileinfo == null) {
			return null;
		}
		String url = fileServer.listReadUrls(fileinfo, fileType.name());
		if (url == null || url.equals("")) {
			// 很罕见,一定是哪个刁民错误地操作了文件系统的底层数据库
			return null;
		}
		//随机选择一个存储节点
		url += buildFilePath(fileId, fileType) + fileId + (StringUtils.isBlank(fileinfo.getFileFormat())?fileType.getExtName():fileinfo.getFileFormat());
		return url;
	}

    @Override
    public int deleteFile(Long fileId)throws FileServerException {
        return fileServer.deleteFile(fileId);
    }

    @Override
    public FileInfo findFileInfo(Long fileId, FileType fileType) throws FileServerException{
        return fileServer.findFileByFileId(new FileInfo(fileId, fileType.name()));
    }

}
