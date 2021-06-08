package pl.polsl.s15.library.api.controller.roles;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.polsl.s15.library.domain.user.account.roles.RoleType;
import pl.polsl.s15.library.dtos.users.permissions.AccountPermissionsDTO;
import pl.polsl.s15.library.api.response.PermissionsResponseDTO;
import pl.polsl.s15.library.dtos.users.permissions.AuthorityDTO;
import pl.polsl.s15.library.dtos.users.permissions.RoleDTO;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissionDTOMapper {

    public PermissionsResponseDTO permissionsResponse(AccountPermissionsDTO permissions) {
        Set<String> plainAuthorities = getPlainAuthorities(permissions.getAuthorities());
        Set<String> plainRoles = getPlainRoles(permissions.getRoles());

        return PermissionsResponseDTO.permissionsResponseBuilder()
                .status(HttpStatus.OK)
                .message("Successfully retrieved permissions")
                .timestamp(new Date())
                .authorities(plainAuthorities)
                .roles(plainRoles)
                .build();
    }

    public Set<String> getPlainAuthorities(Set<AuthorityDTO> authorityDTOS) {
        return authorityDTOS.stream()
                .map(AuthorityDTO::getAuthority)
                .collect(Collectors.toSet());
    }

    public Set<String> getPlainRoles(Set<RoleDTO> roleDTOS) {
        return roleDTOS.stream()
                .map(RoleDTO::getRoleType)
                .map(RoleType::getValue)
                .collect(Collectors.toSet());
    }
}
