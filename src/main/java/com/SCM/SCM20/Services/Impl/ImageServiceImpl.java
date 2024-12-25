package com.SCM.SCM20.Services.Impl;

import com.SCM.SCM20.Helper.AppConstants;
import com.SCM.SCM20.Services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {


    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImg, String  filename) {

        if (contactImg == null || contactImg.isEmpty()){
            throw new IllegalArgumentException("File is empty or not provided.");
        }


        //code likhna hai jo image ko upload kar rha ho server pe

        try {
            byte[] data = new byte[contactImg.getInputStream().available()];

            contactImg.getInputStream().read(data);
            Map params1 = ObjectUtils.asMap(
                    "public_id",filename
            );

            cloudinary.uploader().upload(data, params1);

            return this.getURLFromPublicID(filename);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //and return kar rha hoga : url
    }

    @Override
    public String getURLFromPublicID(String publicId) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstants.CONTACT_IMAGE_CROP)
                )
                .generate(publicId);


    }


}
