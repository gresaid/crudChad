package com.artem.crudchad.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(1)
@RequiredArgsConstructor
@Configuration
public class ImageBucketConfig implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(ImageBucketConfig.class);

  private final MinioClient minioClient;

  @Value("${app.minio.bucketname}")
  private String imageBucketName;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(imageBucketName).build())) {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(imageBucketName).build());
      logger.info("Bucket '%s' created".formatted(imageBucketName));
    } else {
      logger.warn("Bucket '%s' already exists".formatted(imageBucketName));
    }
  }
}
