package vn.quangkhongbiet.homestay_booking.web.rest.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.quangkhongbiet.homestay_booking.domain.homestay.dto.request.ReqReviewDTO;
import vn.quangkhongbiet.homestay_booking.domain.homestay.dto.response.ResReviewDTO;
import vn.quangkhongbiet.homestay_booking.domain.homestay.entity.Review;
import vn.quangkhongbiet.homestay_booking.service.homestay.ReviewService;
import vn.quangkhongbiet.homestay_booking.utils.anotation.ApiMessage;
import vn.quangkhongbiet.homestay_booking.web.dto.response.PagedResponse;
import vn.quangkhongbiet.homestay_booking.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Quản lý đánh giá homestay")
public class ReviewController {

    private static final String ENTITY_NAME = "Review";

    private final ReviewService reviewService;

    private final FilterBuilder fb;

    private final FilterSpecificationConverter fsc;

    @PostMapping("/reviews")
    @ApiMessage("Tạo review thành công")
    @Operation(summary = "Tạo review", description = "Tạo mới một review cho homestay")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tạo thành công"),
        @ApiResponse(responseCode = "404", description = "Người dùng không tồn tại"),
        @ApiResponse(responseCode = "409", description = "Dữ liệu đã tồn tại")
    })
    public ResponseEntity<ResReviewDTO> createReview(@RequestBody @Valid ReqReviewDTO dto) {
        log.info("REST request to create Review by request: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(dto));
    }

    @GetMapping("/reviews/homestay/{homestayId}")
    @ApiMessage("Lấy danh sách review theo homestay thành công")
    @Operation(summary = "Lấy danh sách review theo homestay", description = "Trả về danh sách review của một homestay")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Thành công"),
        @ApiResponse(responseCode = "400", description = "ID không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy homestay")
    })
    public ResponseEntity<PagedResponse> getReviews(
            @PathVariable("homestayId") Long homestayId,
            @Filter Specification<Review> spec,
            Pageable pageable) {
        
        log.info("REST request to get all Reviews with homestayId: {}", homestayId);
        if(homestayId == null || homestayId <= 0){
            throw new BadRequestAlertException("Homestay ID cannot be null or invalid", ENTITY_NAME, "invalidid");
        }
        Specification<Review> reviewInHomestay = fsc.convert(fb.field("homestay").equal(fb.input(homestayId)).get());
        Specification<Review> finalSpec = reviewInHomestay.and(spec);
        return ResponseEntity.ok(this.reviewService.getReviewsForHomestay(finalSpec, pageable));
    }
}
