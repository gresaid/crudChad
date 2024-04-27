package com.artem.crudchad.dto.image;

import com.artem.crudchad.dto.image.controller.ImageUploadRequest;
import lombok.Data;

@Data
public class ImageUploadServiceRequest {

  private final String filename;

  public ImageUploadServiceRequest(ImageUploadRequest imageUploadRequest) {
    this.filename = imageUploadRequest.getFileName();
  }
}
