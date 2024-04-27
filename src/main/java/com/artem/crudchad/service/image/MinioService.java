package com.artem.crudchad.service.image;

import com.artem.crudchad.dto.image.ImageUploadServiceRequest;
import com.artem.crudchad.dto.image.ImageUploadServiceResponse;
import com.artem.crudchad.exception.image.ImageUploadException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinioService implements IMinioService {
  private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

  @Value("${app.minio.bucketname}")
  private String imageBucketName;

  @Value("${app.minio.expiry}")
  private Integer expiry;

  private final MinioClient minioClient;

  @Override
  public ImageUploadServiceResponse uploadImage(ImageUploadServiceRequest request) {
    Map<String, String> reqParams = new HashMap<>();
    reqParams.put("response-content-type", "application/json");
    String fileKey =
        request.getFilename().replaceAll("(.+?)(\\.[^.]*$|$)", UUID.randomUUID() + "$2");
    try {
      String presignedUrl =
          minioClient.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                  .method(Method.PUT)
                  .bucket(imageBucketName)
                  .object(fileKey)
                  .expiry(expiry)
                  .extraQueryParams(reqParams)
                  .build());
      logger.info("File uploaded");
      return new ImageUploadServiceResponse(presignedUrl, fileKey);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ImageUploadException("Cannot upload file %s".formatted(request.getFilename()));
    }
  }
}
