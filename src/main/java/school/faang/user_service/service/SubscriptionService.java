package school.faang.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.common_messages.ErrorMessages;
import school.faang.user_service.dto.user.UserDto;
import school.faang.user_service.entity.User;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.filter.user.UserFilterDto;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.filter.user.UserFilter;
import school.faang.user_service.repository.SubscriptionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final List<UserFilter> userFilters;
    private final UserMapper userMapper;

    public void followUser(UUID followerId, UUID followeeId) {
        validateFollower(followerId, followeeId);
        subscriptionRepository.followUser(followerId, followeeId);
    }

    public void unfollowUser(UUID followerId, UUID followeeId) {
        validateFollower(followerId, followeeId);
        subscriptionRepository.unfollowUser(followerId, followeeId);
    }

    public List<UserDto> getFollowers(UUID followeeId, UserFilterDto filter) {
        validateUserId(followeeId);
        return applyFilter(subscriptionRepository.findByFolloweeId(followeeId), filter);
    }

    public int getFollowersCount(UUID followeeId) {
        validateUserId(followeeId);
        return subscriptionRepository.findFollowersAmountByFolloweeId(followeeId);
    }

    public List<UserDto> getFollowing(UUID followeeId, UserFilterDto filter) {
        validateUserId(followeeId);
        return applyFilter(subscriptionRepository.findByFolloweeId(followeeId), filter);
    }

    public int getFollowingCount(UUID followerId) {
        validateUserId(followerId);
        return subscriptionRepository.findFolloweesAmountByFollowerId(followerId);
    }

    private List<UserDto> applyFilter(Stream<User> users, UserFilterDto dtoFilters) {
        List<UserFilter> requiredFilters = userFilters.stream()
                .filter(filter -> filter.isApplicable(dtoFilters))
                .toList();
        for (UserFilter requiredFilter : requiredFilters) {
            users = requiredFilter.apply(users, dtoFilters);
        }
        return users.map(userMapper::toDto).toList();
    }

    private void validateFollower(UUID followerId, UUID followeeId) {
        validateUserId(followerId);
        validateUserId(followeeId);
        if (followerId == followeeId) {
            throw new DataValidationException(ErrorMessages.SAME_ID);
        }
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_IS_NULL);
        }
    }
}