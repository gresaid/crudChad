package com.artem.crudchad.service.image;

import com.artem.crudchad.dto.image.ImageUploadServiceRequest;
import com.artem.crudchad.dto.image.ImageUploadServiceResponse;

public interface IMinioService {
  ImageUploadServiceResponse uploadImage(ImageUploadServiceRequest request);
}
