package vn.quangkhongbiet.homestay_booking.web.rest.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.quangkhongbiet.homestay_booking.domain.user.dto.request.UpdateUserDTO;
import vn.quangkhongbiet.homestay_booking.domain.user.dto.response.ResUserCreateDTO;
import vn.quangkhongbiet.homestay_booking.domain.user.dto.response.ResUserUpdatedDTO;
import vn.quangkhongbiet.homestay_booking.domain.user.entity.User;
import vn.quangkhongbiet.homestay_booking.service.user.UserService;
import vn.quangkhongbiet.homestay_booking.utils.anotation.ApiMessage;
import vn.quangkhongbiet.homestay_booking.web.dto.response.ResultPaginationDTO;
import vn.quangkhongbiet.homestay_booking.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final String ENTITY_NAME = "user";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiMessage("Tạo người dùng thành công")
    public ResponseEntity<ResUserCreateDTO> createUser(@Valid @RequestBody User user) {

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(createdUser));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Lấy thông tin người dùng thành công")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {

        if (id <= 0 ){
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idnull");
        }
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/users")
    @ApiMessage("Lấy tất cả người dùng thành công")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(Pageable pageable, @Filter Specification<User> spec) {
        return ResponseEntity.ok(userService.findAllUsers(spec, pageable));
    }

    @PatchMapping("/users/{id}")
    @ApiMessage("Cập nhật người dùng thành công")
    public ResponseEntity<ResUserUpdatedDTO> updatePartialUser(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserDTO dto) {

        if (id <= 0 ){
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idnull");
        }
        if (!id.equals(dto.getId())) {
            throw new BadRequestAlertException("ID in URL not match content", ENTITY_NAME, "idnull");
        }
        User updatedUser = this.userService.updatePartialUser(dto);
        return ResponseEntity.ok(this.userService.convertToResUpdatedUserDTO(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Xóa người dùng thành công")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {

        if (id <= 0) {
            throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "invalidid");
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
