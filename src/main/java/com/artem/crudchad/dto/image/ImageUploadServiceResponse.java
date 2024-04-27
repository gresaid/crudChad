package com.artem.crudchad.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageUploadServiceResponse {

  String preUrl;
  String key;
}
