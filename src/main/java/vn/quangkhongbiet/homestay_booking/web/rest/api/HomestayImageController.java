package vn.quangkhongbiet.homestay_booking.web.rest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.quangkhongbiet.homestay_booking.domain.homestay.entity.HomestayImage;
import vn.quangkhongbiet.homestay_booking.service.homestay.HomestayImageService;
import vn.quangkhongbiet.homestay_booking.utils.anotation.ApiMessage;
import vn.quangkhongbiet.homestay_booking.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "HomestayImage", description = "Quản lý hình ảnh homestay")
public class HomestayImageController {
    
    private static final String ENTITY_NAME = "HomestayImage";

    private final HomestayImageService homestayImageService;

    @PostMapping("/homestay/{homestayId}/images")
    @ApiMessage("Upload homestay images thành công")
    @Operation(summary = "Upload hình ảnh homestay", description = "Upload nhiều hình ảnh cho một homestay")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Upload thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    })
    public ResponseEntity<List<HomestayImage>> uploadHomestayImages(
            @PathVariable("homestayId") Long homestayId,
            @RequestPart("files") MultipartFile[] files,
            @RequestPart("folder") String folder) {
        log.info("REST request to upload HomestayImage for homestayId: {}, folder: {}, files count: {}", homestayId, folder, files != null ? files.length : 0);
        if (files == null || files.length == 0) {
            throw new BadRequestAlertException("No files uploaded", ENTITY_NAME, "nofiles");
        }
        if (folder == null || folder.trim().isEmpty()) {
            throw new BadRequestAlertException("Folder name cannot be empty", ENTITY_NAME, "folderempty");
        }
        // Gọi Service để xử lý upload
        List<HomestayImage> savedImages = homestayImageService.createHomestayImages(files, homestayId, folder);
        return ResponseEntity.ok(savedImages);
    }

    @GetMapping("/homestay/{homestayId}/images")
    @ApiMessage("Lấy hình ảnh theo homestayID thành công")
    @Operation(summary = "Lấy hình ảnh theo homestay", description = "Lấy danh sách hình ảnh của một homestay")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công"),
        @ApiResponse(responseCode = "404", description = "Homestay không tồn tại")
    })
    public ResponseEntity<List<HomestayImage>> findHomestayImageByHomestayId(
            @PathVariable("homestayId") Long homestayId) {
        log.info("REST request to get HomestayImage by homestayId: {}", homestayId);
        List<HomestayImage> images = homestayImageService.findHomestayImageByHomestayId(homestayId);
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/homestay-images/{id}")
    @ApiMessage("Xoá hình ảnh theo ID thành công")
    @Operation(summary = "Xoá hình ảnh", description = "Xoá hình ảnh theo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Xoá thành công"),
        @ApiResponse(responseCode = "400", description = "ID không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy hình ảnh")
    })
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id) {
        log.info("REST request to delete HomestayImage by id: {}", id);
        if(id <= 0){
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
        }
        homestayImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}