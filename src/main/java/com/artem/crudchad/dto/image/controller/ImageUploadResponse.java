package com.artem.crudchad.dto.image.controller;

import com.artem.crudchad.dto.image.ImageUploadServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageUploadResponse {

  private final String presignedUrl;
  private final String fileKey;

  public ImageUploadResponse(ImageUploadServiceResponse uploadServiceResponse) {
    this.presignedUrl = uploadServiceResponse.getPreUrl();
    this.fileKey = uploadServiceResponse.getKey();
  }
}
