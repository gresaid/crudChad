package com.artem.crudchad.controllers.image;

import com.artem.crudchad.dto.image.ImageUploadServiceRequest;
import com.artem.crudchad.dto.image.controller.ImageUploadRequest;
import com.artem.crudchad.dto.image.controller.ImageUploadResponse;
import com.artem.crudchad.service.image.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class FileUploadController {

  private final MinioService minioService;

  @PostMapping("/images/upload")
  public ResponseEntity<ImageUploadResponse> uploadImage(
      ImageUploadRequest imageUploadRequest, BindingResult bindingResult)
      throws BindException {
    if (bindingResult.hasErrors()) {
      throw new BindException(bindingResult);
    }
    return ResponseEntity.ok(
        new ImageUploadResponse(
            minioService.uploadImage(new ImageUploadServiceRequest(imageUploadRequest))));
  }
}
