/**
 * @类名称:FileServerTest1.java
 * @时间:2017年5月23日下午3:21:43
 * @作者:lihai 
 * @版权:公司 Copyright (c) 2017 
 */
package com.vc.group.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vc.group.file.constant.FileType;
import com.vc.group.file.entity.FileInfo;
import com.vc.group.file.service.FileServerManager;

/**
 * @描述:TODO
 * @作者:lihai 
 * @时间:2017年5月23日 下午3:21:43 
 */
@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"}) 
public class FileServerTest {
    private String filePath = "F:\\1.jpg";
    
    private static Logger logger = Logger.getLogger(FileServerTest.class);
    
    @Autowired
    private FileServerManager fileServerManager;
    
   @Test
    public void test(){
        try {
            logger.info("hello");
            System.out.println("hello world!");
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
   @Ignore
    public void deleteFile(){
        try {
            int isOk =  fileServerManager.deleteFile(71L);
            System.out.println(isOk);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    
    @Ignore
    public void findFileInfo(){
        try {
            FileInfo fileInfo =  fileServerManager.findFileInfo(71L, FileType.IMAGE);
            System.out.println(fileInfo.getDownloadUrl());
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    
    @Ignore
    public void findFileUrl(){
        try {
            String url =  fileServerManager.findFileUrl(335L, FileType.IMAGE);
            System.out.println(url);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    @Ignore
    public void findFileMd5(){
        try {
            String url =  fileServerManager.findFileMd5(71L, FileType.IMAGE);
            System.out.println(url);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    
    @Ignore
    public void saveFile(){
       try {
           File file = new File(filePath);
           InputStream in = new FileInputStream(file);
           long fileId =  fileServerManager.saveFile(in, ".jpg", "test.jpg", FileType.IMAGE);
           System.out.println(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

