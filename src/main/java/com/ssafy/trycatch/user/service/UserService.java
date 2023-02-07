package com.ssafy.trycatch.user.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.ssafy.trycatch.qna.domain.Answer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.user.controller.dto.UserModifyDto;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.FollowRepository;
import com.ssafy.trycatch.user.domain.User;
import com.ssafy.trycatch.user.domain.UserRepository;
import com.ssafy.trycatch.user.domain.Withdrawal;
import com.ssafy.trycatch.user.domain.WithdrawalRepository;
import com.ssafy.trycatch.user.service.exceptions.AlreadyExistException;
import com.ssafy.trycatch.user.service.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService extends CrudService<User, Long, UserRepository> {
    private static final User GUEST = User.builder()
            .id(-1L)
            .githubNodeId("")
            .username("guest")
            .gitAddress("https://github.com")
            .email("")
            .activated(false)
            .calendarMail("")
            .followees(Collections.emptySet())
            .followers(Collections.emptySet())
            .answers(Collections.emptySet())
            .subscriptions(Collections.emptySet())
            .questions(Collections.emptySet())
            .myBadges(Collections.emptySet())
            .myChallenges(Collections.emptySet())
            .histories(Collections.emptySet())
            .build();

    @SuppressWarnings("unsued")
    public static User getGuest() {
        return GUEST;
    }

    private final WithdrawalRepository withdrawalRepository;
    private final FollowRepository followRepository;

    public UserService(
            UserRepository repository,
            WithdrawalRepository withdrawalRepository,
            FollowRepository followRepository
    ) {
        super(repository);
        this.withdrawalRepository = withdrawalRepository;
        this.followRepository = followRepository;
    }

    public User findUserById(@NotNull Long userId) {
        return repository.findById(userId)
                         .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void inActivateUser(long uid, Withdrawal reason) {
        User savedUser = repository.findById(uid)
                                   .orElseThrow();
        savedUser.setActivated(false);
        repository.save(savedUser);
        withdrawalRepository.save(reason);
    }

    public Long findNameToId(String userName) {
        return repository.findByUsername(userName)
                         .orElseThrow(UserNotFoundException::new)
                         .getId();
    }

    public User findUserByName(String userName) {
        return repository.findByUsername(userName)
                         .orElseThrow(UserNotFoundException::new);
    }

    public User getDetailUserInfo(Long userId) {
        return repository.findById(userId)
                         .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void modifyUser(Long userId, UserModifyDto modifyDto) {
        User targetUser = repository.findById(userId)
                                    .orElseThrow(UserNotFoundException::new);
        if (null != modifyDto.introduction) {
            targetUser.setIntroduction(modifyDto.getIntroduction());
        }
        if (null != modifyDto.profileImage) {
            targetUser.setImageSrc(modifyDto.getProfileImage());
        }
        repository.save(targetUser);
    }

    public List<User> findFollowList(Long uid, String type) {
        User user = repository.findById(uid)
                              .orElseThrow(UserNotFoundException::new);

        return Objects.requireNonNull(getFollowers(user, type))
                      .stream()
                      .map(e -> repository.findById(e.getId())
                                          .orElseThrow(UserNotFoundException::new))
                      .collect(Collectors.toList());
    }

    private Set<Follow> getFollowers(User user, String type) {
        if (type.equals("follower")) {
            return user.getFollowers();
        } else if (type.equals("followee")) {
            return user.getFollowees();
        } else {
            return Collections.emptySet();
        }
    }

    public Boolean getVerification(Long userId) {
        return repository.findById(userId)
                         .orElseThrow(UserNotFoundException::new)
                         .getConfirmationCode() == 1;
    }

    /**
     * ID: src 인 유저가 ID: des 인 유저를 Follow
     *
     * @param src 기준 유저
     * @param des 타겟 유저
     */
    public void follow(Long src, Long des) {
        if (Objects.equals(src, des)) {
            throw new AlreadyExistException();
        }
        final User srcUser = repository.findById(src)
                                       .orElseThrow(UserNotFoundException::new);
        final User desUser = repository.findById(des)
                                       .orElseThrow(UserNotFoundException::new);

        if (followRepository.existsByFollowerIdAndFolloweeId(src, des)) {
            throw new AlreadyExistException();
        }

        followRepository.save(Follow.builder()
                                      .follower(srcUser)
                                      .followee(desUser)
                                      .build());
    }

    public void unfollow(Long src, Long des) {
        if (Objects.equals(src, des)) {
            throw new AlreadyExistException();
        }

        Follow follow = followRepository.findByFollower_IdAndFollowee_Id(src, des)
                                        .orElseThrow(AlreadyExistException::new);
        followRepository.delete(follow);
    }

    public boolean isExist(Long uid) {
        return repository.existsById(uid);
    }

    public List<Long> getAnswerIdListByUserId(Long uid) {
        return repository.findById(uid)
                         .orElseThrow(UserNotFoundException::new)
                         .getAnswers()
                         .stream()
                         .map(Answer::getId)
                         .collect(Collectors.toList());
    }
}