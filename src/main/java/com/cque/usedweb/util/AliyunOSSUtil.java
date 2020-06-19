package com.cque.usedweb.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.cque.usedweb.entity.AliConfEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Chenge on 2020.5.18 11:17
 */
@Component
public class AliyunOSSUtil {

    @Autowired
    private AliConfEntity aliConfEntity;

    public String upload(MultipartFile file, String fileName){
        if(file == null){
            return null;
        }
        OSSClient client = new OSSClient(aliConfEntity.getEndpoint(),aliConfEntity.getKeyid(),aliConfEntity.getKeysecret());
        if (!client.doesBucketExist(aliConfEntity.getBucketname())){
            // 判断容器是否存在,不存在就创建
            client.createBucket(aliConfEntity.getBucketname());
            CreateBucketRequest request = new CreateBucketRequest(aliConfEntity.getBucketname());
            // 设置权限(公开读)
            request.setCannedACL(CannedAccessControlList.PublicRead);
            client.createBucket(request);
        }
        //开始上传文件
        String basePath = aliConfEntity.getFilehost()+fileName;
        PutObjectResult result = null;
        try {
            result = client.putObject(new PutObjectRequest(aliConfEntity.getBucketname(),basePath,new ByteArrayInputStream(file.getBytes())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.setBucketAcl(aliConfEntity.getBucketname(),CannedAccessControlList.PublicRead);
        if (result != null){
            return "success";
        }
        return "error";
    }
    /**
     * 删除oss上面的文件
     * @param fileName 文件名，应该时文件路径，从filehost开始
     */
    public void dealFile(String fileName){
        OSSClient client = new OSSClient(aliConfEntity.getEndpoint(),aliConfEntity.getKeyid(),aliConfEntity.getKeysecret());
        client.deleteObject(aliConfEntity.getBucketname(),fileName);
    }
}
