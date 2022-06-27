package com.team03.issuetracker.common.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageUploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public String uploadImage(MultipartFile multipartFile) throws FileUploadException {
		String fileName = makeUniqueName(multipartFile.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3Client.putObject(
				new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));

		} catch (IOException e) {
			throw new FileUploadException();
		}

		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}

	public static String makeUniqueName(String fileName) {
		return fileName + UUID.randomUUID();
	}

	public void deleteImage(String imageKey) {
		amazonS3Client.deleteObject(bucketName, imageKey);
	}
}
