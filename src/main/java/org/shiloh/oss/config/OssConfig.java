package org.shiloh.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 配置
 *
 * @author shiloh
 * @date 2024/10/23 16:41
 */
@Configuration
public class OssConfig {
    /**
     * OSS Client 配置
     *
     * @author shiloh
     * @date 2024/10/23 16:41
     */
    @Bean
    public OSS ossClient(OssProperties ossProperties) {
        final DefaultCredentialProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret()
        );
        return new OSSClientBuilder()
                .build(ossProperties.getEndpoint(), credentialsProvider);
    }
}
