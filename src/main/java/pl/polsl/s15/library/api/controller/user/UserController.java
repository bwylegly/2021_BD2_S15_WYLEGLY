package pl.polsl.s15.library.api.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.polsl.s15.library.api.controller.base.BaseController;
import pl.polsl.s15.library.api.controller.base.response.ResponseDTO;
import pl.polsl.s15.library.api.controller.user.request.AddUserRoleRequest;
import pl.polsl.s15.library.api.controller.user.request.DeleteUserRoleRequest;
import pl.polsl.s15.library.api.controller.user.request.UserCreateOrUpdateRequestDTO;
import pl.polsl.s15.library.api.controller.user.response.GetAccountMetaDataResponse;
import pl.polsl.s15.library.api.controller.user.response.GetAllUsersResponse;
import pl.polsl.s15.library.domain.user.User;
import pl.polsl.s15.library.dtos.users.UserDTO;
import pl.polsl.s15.library.dtos.users.meta.AccountMetaData;
import pl.polsl.s15.library.dtos.users.permissions.AccountPermissionsDTO;
import pl.polsl.s15.library.dtos.users.permissions.PermissionsDTOMapper;
import pl.polsl.s15.library.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    private UserService userService;
    private UserReqRepMapper reqRepMapper;

    @Autowired
    public UserController(UserService userService,
                          @Qualifier("userReqRepMapper") UserReqRepMapper reqRepMapper) {
        this.userService = userService;
        this.reqRepMapper = reqRepMapper;
    }

    @GetMapping("/meta")
    public ResponseEntity<GetAccountMetaDataResponse> getAccountMeta() {
        Authentication user = getCurrentUser();
        AccountMetaData accountMetaData = prepareAccountMetaData((String) user.getPrincipal());
        return ResponseEntity.ok()
                .body(reqRepMapper.getAccountMetaDataResponse(accountMetaData));
    }

    private AccountMetaData prepareAccountMetaData(String principal) {
        User user = (User) userService.loadUserByUsername(principal);
        long userId = user.getId();
        AccountPermissionsDTO permissions = PermissionsDTOMapper.toDTO(user.getPermissions());
        return AccountMetaData.builder()
                .userId(userId)
                .permissionsDTO(permissions)
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok()
                .body(reqRepMapper.getAllUsersResponse(users));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserCreateOrUpdateRequestDTO updateRequestDTO) {
        UserDTO userDTO = reqRepMapper.mapRequestToUser(updateRequestDTO);
        userService.updateUser(userDTO);
        return ResponseEntity.ok()
                .body(reqRepMapper.userUpdateSuccessfulResponse());
    }

    @PatchMapping("/role/add")
    public ResponseEntity<ResponseDTO> addNewRoleForUser(@RequestBody AddUserRoleRequest userRoleRequest) {
        userService.addUserRole(userRoleRequest.getUserId(),
                userRoleRequest.getRoleName());
        return ResponseEntity.ok()
                .body(reqRepMapper.userRoleAddedSuccessfullyResponse());
    }

    @DeleteMapping("/role/delete")
    public ResponseEntity<ResponseDTO> deleteRoleForUser(@RequestBody DeleteUserRoleRequest deleteUserRoleRequest) {
        userService.deleteUserRole(deleteUserRoleRequest.getUserId(),
                deleteUserRoleRequest.getRoleName());
        return ResponseEntity.ok()
                .body(reqRepMapper.userRoleDeletedSuccessfullyResponse());
    }

}
