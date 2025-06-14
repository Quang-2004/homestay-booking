package vn.quangkhongbiet.homestay_booking.web.rest.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.quangkhongbiet.homestay_booking.domain.booking.dto.request.ReqBooking;
import vn.quangkhongbiet.homestay_booking.domain.booking.dto.request.UpdateBookingDTO;
import vn.quangkhongbiet.homestay_booking.domain.booking.dto.response.ResBookingDTO;
import vn.quangkhongbiet.homestay_booking.domain.booking.entity.Booking;
import vn.quangkhongbiet.homestay_booking.service.booking.BookingService;
import vn.quangkhongbiet.homestay_booking.utils.anotation.ApiMessage;
import vn.quangkhongbiet.homestay_booking.web.dto.response.ResultPaginationDTO;
import vn.quangkhongbiet.homestay_booking.web.rest.errors.BadRequestAlertException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookingController {

    private static final String ENTITY_NAME = "booking";

    private final BookingService bookingService;

    @PostMapping("/bookings")
    @ApiMessage("Đặt phòng thành công")
    public ResponseEntity<ResBookingDTO> createBooking(@Valid @RequestBody ReqBooking request) {

        Booking createdBooking = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.bookingService.convertToResBookingDTO(createdBooking));
    }

    @GetMapping("/bookings/{id}")
    @ApiMessage("Lấy thông tin đặt phòng thành công")
    public ResponseEntity<ResBookingDTO> getBookingById(@PathVariable("id") Long id) {

        if(id <= 0){
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
        }
        return ResponseEntity.ok(this.bookingService.convertToResBookingDTO(bookingService.findBookingById(id)));
    }

    @GetMapping("/bookings")
    @ApiMessage("Lấy danh sách đặt phòng thành công")
    public ResponseEntity<ResultPaginationDTO> getAllBookings(@Filter Specification<Booking> spec, Pageable pageable) {
        return ResponseEntity.ok(bookingService.findAllBookings(spec, pageable));
    }

    @PatchMapping("/bookings/{id}")
    @ApiMessage("Cập nhật thông tin đặt phòng thành công")
    public ResponseEntity<?> updatePartialBooking(@PathVariable("id") Long id, @Valid @RequestBody UpdateBookingDTO dto) {

        if(id <= 0){
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
        }
        if (!id.equals(dto.getId())) {
            throw new BadRequestAlertException("ID in URL not match content", ENTITY_NAME, "idmismatch");
        }
        Booking updatedBooking = bookingService.updatePartialBooking(dto);
        return ResponseEntity.ok(this.bookingService.convertToResBookingDTO(updatedBooking));
    }
}
