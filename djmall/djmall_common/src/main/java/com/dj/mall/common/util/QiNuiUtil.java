package com.dj.mall.common.util;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiNuiUtil {

    /**
     * AK
     */
    private static final String AK = "s6CWg77dYqudkc1ijuo7IbnF_e0qdhtGxHawy295";

    /**
     * SK
     */
    private static final String SK = "ID5OapKkW03aMGta_01U_HWwmFNR0JngP6qC3Ekn";

    /**
     * 外链域名
     */
    private static final String URL = "qs0q2d1mv.hn-bkt.clouddn.com";

    /**
     * 存储空间名称
     */
    private static final String BUCKET = "songjialiang";

    /**
     * 密钥配置
     * private static Auth auth = Auth.create(AK, SK);
     */
    private static Auth auth = Auth.create(AK, SK);

    /**
     * 生成token
     * private static String token = auth.uploadToken(BUCKET);
     */
    private static String token = auth.uploadToken(BUCKET);

    /**
     * 创建地区
     * private static Configuration cnf = new Configuration(Region.autoRegion());
     */
    private static Configuration cnf = new Configuration(Region.autoRegion());

    /**
     * 七牛封装的上传工厂
     * private static UploadManager uploadManager = new UploadManager(cnf);
     */
    private static UploadManager uploadManager = new UploadManager(cnf);

    /**
     * 牛封装的删除工厂
     * private static  BucketManager bucketManager =  new BucketManager(auth, cnf);
     */
    private static BucketManager bucketManager = new BucketManager(auth, cnf);

    /**
     * byte[] 上传图片
     */
    public static void uploadFile(byte[] img, String key) {
        try {
            uploadManager.put(img, key, token);
            System.out.println("upload success");
        } catch (QiniuException e) {
            System.out.println("upload shibai");
            e.printStackTrace();
        }
    }

    /**
     * 删除图片
     */
    public static void delFile(String key) {
        try {
            bucketManager.delete(BUCKET, key);
            System.out.println("del success");
        } catch (QiniuException e) {
            System.out.println("del shibai");
            e.printStackTrace();
        }
    }
}