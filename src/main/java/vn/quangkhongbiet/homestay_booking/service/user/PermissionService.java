package vn.quangkhongbiet.homestay_booking.service.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.quangkhongbiet.homestay_booking.domain.user.entity.Permission;
import vn.quangkhongbiet.homestay_booking.web.dto.response.ResultPaginationDTO;

public interface PermissionService {
    public boolean isExistsbyId(Long id);

    public boolean isPermissionExist(Permission permission);

    public Permission createPermission(Permission permission);

    public Permission getById(Long id);

    public ResultPaginationDTO getAll(Specification<Permission> spec, Pageable pageable);

    public Permission updatePartialPermission(Permission permission);

    public void deleteById(Long id);
}
