package org.shiloh;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.shiloh.oss.config.OssProperties;
import org.shiloh.oss.constant.OssDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * OSS 单元测试
 *
 * @author shiloh
 * @date 2024/10/23 16:38
 */
@Slf4j
@SpringBootTest
class OssTests {
    @Autowired
    private OSS ossClient;
    @Autowired
    private OssProperties ossProperties;

    /**
     * 测试创建 Bucket
     *
     * @author shiloh
     * @date 2024/10/23 16:38
     */
    @Test
    void testCreateBucket() {
        try {
            // 填写Bucket名称，例如：example-bucket，注意要全局唯一
            final Bucket bucket = this.ossClient.createBucket(this.ossProperties.getBucket());
            Assertions.assertThat(bucket).isNotNull();
            Assertions.assertThat(bucket.getName()).isEqualTo(this.ossProperties.getBucket());
            log.info("bucket: {} 创建成功", bucket);
        } catch (OSSException | ClientException oe) {
            Fail.fail("oss bucket 创建失败", oe);
        }
    }

    /**
     * 测试文件上传
     *
     * @author shiloh
     * @date 2024/10/23 16:57
     */
    @Test
    void testUploadFile() {
        final String msg = "Hell Aliyun OSS，这是使用 Java SDK 上传的文件哦~";
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
        try {
            final PutObjectResult putObjectResult = this.ossClient.putObject(
                    this.ossProperties.getBucket(), OssDir.TEST.createObjectName("test.txt"), inputStream
            );
            Assertions.assertThat(putObjectResult).isNotNull();
            final ResponseMessage response = putObjectResult.getResponse();
            Assertions.assertThat(response).isNotNull();
            Assertions.assertThat(response.isSuccessful()).isTrue();
        } catch (OSSException | ClientException e) {
            Fail.fail("OSS 文件上传失败", e);
        }
    }

    /**
     * 测试文件下载
     *
     * @author shiloh
     * @date 2024/10/23 17:26
     */
    @Test
    void testDownloadFile() {
        try {
            final String objectName = "test/20241023/test-98c5626b42da43ec81acf8c9dc8eeff7.txt";
            final OSSObject ossObject = this.ossClient.getObject(this.ossProperties.getBucket(), objectName);
            Assertions.assertThat(ossObject).isNotNull();
            try (final InputStream objectContent = ossObject.getObjectContent()) {
                Assertions.assertThat(objectContent).isNotNull();
                final Path path = Paths.get("D://", FilenameUtils.getName(objectName));
                Files.copy(objectContent, path);
                Assertions.assertThat(path.toFile()).exists();
            }
        } catch (OSSException | ClientException | IOException e) {
            Fail.fail("OSS 文件下载失败", e);
        }
    }

    /**
     * 释放资源
     *
     * @author shiloh
     * @date 2024/10/23 16:59
     */
    @AfterEach
    public void clear() {
        this.ossClient.shutdown();
    }
}
