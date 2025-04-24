package school.faang.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.user.CreateUserRequestDto;
import school.faang.user_service.dto.user.CreatedUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.filter.user.UserFilterDto;
import school.faang.user_service.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/premium")
    public ResponseEntity<Page<UserDto>> getPremiumUsers(@RequestBody UserFilterDto userFilterDto, Pageable pageable) {
        Page<UserDto> users = userService.getPremiumUsers(userFilterDto, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@RequestHeader("x-user-id")UUID currentUserId, @PathVariable UUID userId) {
        return userService.getUser(currentUserId, userId);
    }

    @PostMapping("/new")
    public CreatedUserDto createUser(@RequestBody @Valid CreateUserRequestDto request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserDto> getUsersByIds(@RequestBody List<UUID> ids) {
        return userService.getUsersByIds(ids);
    }

}
