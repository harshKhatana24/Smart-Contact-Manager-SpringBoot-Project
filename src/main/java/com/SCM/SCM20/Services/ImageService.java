package com.SCM.SCM20.Services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactImg, String  filename);

    String getURLFromPublicID(String  publicId);

}
