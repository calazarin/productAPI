package com.test.productapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.productapi.exception.ResourceNotFoundException;
import com.test.productapi.exception.ServiceException;
import com.test.productapi.service.ImageService;
import com.test.productapi.vo.ImageVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * Image resource rest controller
 * 
 * @author Lazarin,Carlos
 * 
 *         POST -> /images 
 *         PUT -> /images 
 *         DELETE -> /images/{id} 
 *         GET ->  /images/{parentProductId}
 */
@RestController
public class ImageRestController {

	private ImageService imageService;

	private static final String IMAGE_CREATED_SUCCESSFULLY_MSG = "New image created successfully";
	private static final String IMAGE_DELETED_SUCCESSFULLY_MSG = "Image deleted successfully";
	private static final String IMAGE_UPDATED_SUCCESSFULLY_MSG = "Image updated successfully";

	@Autowired
	public ImageRestController(ImageService imageService) {
		this.imageService = imageService;
	}

	@ApiOperation(value = "Creates a new image", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = IMAGE_CREATED_SUCCESSFULLY_MSG), })
	@PostMapping(value = "/images", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createImage(@Valid @RequestBody ImageVO imageVO) throws ServiceException {
		this.imageService.createImage(imageVO);
		return new ResponseEntity<String>(IMAGE_CREATED_SUCCESSFULLY_MSG, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Updates image for given payload", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = IMAGE_UPDATED_SUCCESSFULLY_MSG), })
	@PutMapping(value = "/images", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateImage(@Valid @RequestBody ImageVO imageVO) {
		this.imageService.updateImage(imageVO);
		return new ResponseEntity<String>(IMAGE_UPDATED_SUCCESSFULLY_MSG, HttpStatus.OK);
	}

	@ApiOperation(value = "Deletes image for given id", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = IMAGE_DELETED_SUCCESSFULLY_MSG), })
	@DeleteMapping("/images/{imageId}")
	public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
		try {
			this.imageService.deleteImage(imageId);
			return new ResponseEntity<String>(IMAGE_DELETED_SUCCESSFULLY_MSG, HttpStatus.NO_CONTENT);
		}catch(ServiceException sex) {
			throw new ResourceNotFoundException(sex.getMessage());
		}
	}

	@ApiOperation(value = "List of images for given product id", responseContainer = "List", response = ImageVO.class)
	@GetMapping("/images/{parentProductId}")
	public ResponseEntity<List<ImageVO>> getProductImages(@PathVariable Long parentProductId) throws ServiceException {
		try {
		return new ResponseEntity<List<ImageVO>>(this.imageService.findImagesForProductId(parentProductId),
				HttpStatus.OK);
		}catch(ServiceException sex) {
			throw new ResourceNotFoundException(sex.getMessage());
		}
	}
	
	@ApiOperation(value = "List all images", responseContainer = "List", response = ImageVO.class)
	@GetMapping("/images")
	public ResponseEntity<List<ImageVO>> getAllImages() throws ServiceException {
		return new ResponseEntity<List<ImageVO>>(this.imageService.findAllImagesAndReturnVOS(),
				HttpStatus.OK);
	}
}