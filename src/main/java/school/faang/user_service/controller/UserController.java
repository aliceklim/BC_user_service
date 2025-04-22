package school.faang.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.faang.user_service.dto.user.CreateUserRequestDto;
import school.faang.user_service.dto.user.CreatedUserDto;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.filter.user.UserFilterDto;
import school.faang.user_service.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/premium")
    public List<UserDto> getPremiumUsers(UserFilterDto userFilterDto) {
        return userService.getPremiumUsers(userFilterDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@RequestHeader("x-user-id")Long currentUserId, @PathVariable long userId) {
        return userService.getUser(currentUserId, userId);
    }

    @PostMapping("/new")
    public CreatedUserDto createUser(@RequestBody @Valid CreateUserRequestDto request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserDto> getUsersByIds(@RequestBody List<Long> ids) {
        return userService.getUsersByIds(ids);
    }

}
