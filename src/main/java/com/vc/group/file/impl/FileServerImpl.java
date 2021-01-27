package com.vc.group.file.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vc.group.file.dao.FileServerDao;
import com.vc.group.file.entity.FileInfo;
import com.vc.group.file.entity.FileNode;
import com.vc.group.file.exception.FileServerException;
import com.vc.group.file.service.FileService;
import com.vc.group.file.util.FileServerUtil;

@Service
@Transactional(readOnly = true)
public class FileServerImpl implements FileService {
    
    @Autowired
	private FileServerDao fileServerDao;

	@Override
	public List<FileNode> findAllFileNodeList(String fileType) throws FileServerException{
	    return fileServerDao.findAllFileNodeList(fileType);
	}

	@Override
	public String listReadUrls(FileInfo fileInfo, String fileType) throws FileServerException{
        return FileServerUtil.buildNodeUrl(findFileNode(new FileNode(fileInfo.getNodeId(),fileType)));
	}
	
	@Override
	@Transactional(readOnly = false)
	public void decreaseNodeFreeSpace(FileNode fileNode) throws FileServerException{
		fileServerDao.decreaseNodeFreeSpace(fileNode);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveFileInfo(FileInfo info) throws FileServerException{
		fileServerDao.saveFileInfo(info);
	}

	@Override
	public FileInfo findFileByFileId(FileInfo fileInfo) throws FileServerException{
		return fileServerDao.findFileByFileId(fileInfo);
	}

    @Override
    public long getNextFileId() throws FileServerException{
        return fileServerDao.getNextFileId();
    }

    @Override
    public FileNode findFileNode(FileNode fileNode) throws FileServerException{
        return fileServerDao.findFileNode(fileNode);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteFile(Long fileId) throws FileServerException{
        return fileServerDao.deleteFile(fileId);
    }
}
