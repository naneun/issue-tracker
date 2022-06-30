package com.team03.issuetracker.common.api;

import com.team03.issuetracker.common.component.ImageUploader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

	private final ImageUploader s3Uploader;

	@ApiOperation(
		value = "AWS S3 버킷에 이미지 업로드",
		notes = "AWS S3 버킷에 이미지를 업로드하고 url 을 받아온다.",
		produces = "application/json",
		response = String.class
	)
	@PostMapping
	public ResponseEntity<String> upload(@RequestPart("image") MultipartFile multipartFile)
		throws FileUploadException {

		return ResponseEntity.ok(s3Uploader.uploadImage(multipartFile));
	}

	@ApiOperation(
		value = "AWS S3 버킷에 업로드한 이미지 삭제",
		notes = "AWS S3 버킷에 업로드한 이미지를 삭제한다.",
		produces = "application/json"
	)
	@DeleteMapping
	public ResponseEntity<Void> delete(String imageKey) {
		s3Uploader.deleteImage(imageKey);
		return ResponseEntity.ok().build();
	}
}
