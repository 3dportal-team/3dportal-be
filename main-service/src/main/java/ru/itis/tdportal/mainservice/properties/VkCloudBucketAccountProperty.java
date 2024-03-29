package ru.itis.tdportal.mainservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "vk.cloud.account")
public class VkCloudBucketAccountProperty {

    private String name;
    private String accessKey;
    private String secretKey;
}
