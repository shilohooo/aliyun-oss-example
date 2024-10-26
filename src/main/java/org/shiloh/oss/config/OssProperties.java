package org.shiloh.oss.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OSS 配置项
 *
 * @author shiloh
 * @date 2024/10/23 16:32
 */
@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * Endpoint
     */
    private String endpoint;

    /**
     * Access Key ID
     */
    private String accessKeyId;

    /**
     * Access Key Secret
     */
    private String accessKeySecret;

    /**
     * 存储空间名称
     */
    private String bucket;
}
