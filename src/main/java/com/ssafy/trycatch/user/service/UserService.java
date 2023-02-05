package com.ssafy.trycatch.user.service;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import com.ssafy.trycatch.user.controller.dto.UserModifytDto;
import com.ssafy.trycatch.user.domain.*;
import com.ssafy.trycatch.user.service.exceptions.AlreadyExistException;
import com.ssafy.trycatch.user.service.exceptions.TypeNotFoundException;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends CrudService<User, Long, UserRepository> {
    private static final User GUEST = User.builder().id(-1L).githubNodeId("").username("guest").gitAddress("https://github.com").email("").activated(false).calendarMail("").followees(Collections.emptySet()).followers(Collections.emptySet()).answers(Collections.emptySet()).subscriptions(Collections.emptySet()).questions(Collections.emptySet()).myBadges(Collections.emptySet()).myChallenges(Collections.emptySet()).histories(Collections.emptySet()).build();
    private final ReadRepository readRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final FollowRepository followRepository;

    public UserService(
            UserRepository repository, ReadRepository readRepository, WithdrawalRepository withdrawalRepository,
            FollowRepository followRepository
    ) {
        super(repository);
        this.readRepository = readRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.followRepository = followRepository;
    }

    @SuppressWarnings("unsued")
    public static User getGuest() {
        return GUEST;
    }

    public User findUserById(@NotNull Long userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void inActivateUser(long uid, Withdrawal reason) {
        User savedUser = repository.findById(uid).orElseThrow();
        savedUser.setActivated(false);
        repository.save(savedUser);
        withdrawalRepository.save(reason);
    }

    public Long findNameToId(String userName) {
        return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new).getId();
    }

    public User findUserByName(String userName) {
        return repository.findByUsername(userName).orElseThrow(UserNotFoundException::new);
    }

    public User getDetailUserInfo(Long userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void modifyUser(Long userId, UserModifytDto modifytDto) {
        User targetUser = repository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (null != modifytDto.introduction) {
            targetUser.setIntroduction(modifytDto.getIntroduction());
        }
        if (null != modifytDto.profileImage) {
            targetUser.setImageSrc(modifytDto.getProfileImage());
        }
        repository.save(targetUser);
    }

    public List<User> findFollowList(Long uid, String type) {
        User user = repository.findById(uid).orElseThrow(UserNotFoundException::new);

        return getFollowset(user, type).orElseThrow(TypeNotFoundException::new).stream().map(e -> repository.findById(e.getId()).orElseThrow(UserNotFoundException::new)).collect(Collectors.toList());
    }

    private Optional<Set<Follow>> getFollowset(User user, String type) {
        if (type.equals("follower")) {
            return Optional.of(user.getFollowers());
        } else if (type.equals("followee")) {
            return Optional.of(user.getFollowees());
        } else {
            return null;
        }
    }

    public Boolean getVerification(Long userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new).getConfirmationCode() == 1;
    }

    /**
     * ID: src 인 유저가 ID: des 인 유저를 Follow
     *
     * @param src
     * @param des
     */
    public void follow(Long src, Long des) {
        if (src == des) {
            throw new AlreadyExistException();
        }
        final User srcUser = repository.findById(src).orElseThrow(UserNotFoundException::new);
        final User desUser = repository.findById(des).orElseThrow(UserNotFoundException::new);

        if (followRepository.existsByFollowerIdAndFolloweeId(src, des)) {
            throw new AlreadyExistException();
        }

        followRepository.save(Follow.builder().follower(srcUser).followee(desUser).build());
    }

    public void unfollow(Long src, Long des) {
        if (src == des) {
            throw new AlreadyExistException();
        }

        Follow follow = followRepository.findByFollower_IdAndFollowee_Id(src, des).orElseThrow(AlreadyExistException::new);
        followRepository.delete(follow);
    }

    public boolean isExist(Long uid) {
        return repository.existsById(uid);
    }

    public List<Long> getAnswerIdListByUserId(Long uid) {
        return repository.findById(uid).get().getAnswers().stream().map(e -> e.getId()).collect(Collectors.toList());
    }
}