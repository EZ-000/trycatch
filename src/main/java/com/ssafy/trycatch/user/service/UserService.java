package com.ssafy.trycatch.user.service;

import static com.ssafy.trycatch.common.infra.config.ConstValues.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trycatch.common.domain.BookmarkRepository;
import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.LikesRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.CrudService;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.user.controller.dto.SimpleUserInfo;
import com.ssafy.trycatch.user.controller.dto.UserAnswerDto;
import com.ssafy.trycatch.user.controller.dto.UserModifyDto;
import com.ssafy.trycatch.user.controller.dto.UserQuestionDto;
import com.ssafy.trycatch.user.controller.dto.UserFeedDto;
import com.ssafy.trycatch.user.controller.dto.UserSubscriptionDto;
import com.ssafy.trycatch.user.domain.Follow;
import com.ssafy.trycatch.user.domain.FollowRepository;
import com.ssafy.trycatch.user.domain.Subscription;
import com.ssafy.trycatch.user.domain.SubscriptionRepository;
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
	private final ESFeedRepository eSFeedRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final ReadRepository readRepository;
	private final BookmarkRepository bookmarkRepository;
	private final LikesRepository likesRepository;
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
		FollowRepository followRepository,
		LikesRepository likesRepository,
		BookmarkRepository bookmarkRepository,
		ReadRepository readRepository,
		SubscriptionRepository subscriptionRepository,
		ESFeedRepository eSFeedRepository) {
		super(repository);
		this.withdrawalRepository = withdrawalRepository;
		this.followRepository = followRepository;
		this.likesRepository = likesRepository;
		this.bookmarkRepository = bookmarkRepository;
		this.readRepository = readRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.eSFeedRepository = eSFeedRepository;
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

		Set<Follow> followSet = getFollowers(user, type);
		if (followSet.isEmpty()) {
			return Collections.emptyList();
		} else {
			return Objects.requireNonNull(followSet)
				.stream()
				.map(e -> repository.findById(e.getId())
					.orElseThrow(UserNotFoundException::new))
				.collect(Collectors.toList());
		}
	}

	private Set<Follow> getFollowers(User user, String type) {
		if (type.equals("follower")) {
			return user.getFollowees();
		} else if (type.equals("followee")) {
			return user.getFollowers();
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

	public List<UserAnswerDto> getUserAnswerDtoList(Long uid, Long id) {
		Set<Answer> answerList = repository.findById(uid)
			.orElseThrow(UserNotFoundException::new)
			.getAnswers();


		List<UserAnswerDto> result = new ArrayList<>();
		for (Answer iterAnswer : answerList) {
			Question question = iterAnswer.getQuestion();
			boolean flag = likesRepository.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(
				id, iterAnswer.getId(),
				TargetType.ANSWER).isPresent() ? true : false;

			UserAnswerDto userAnswerDto = UserAnswerDto.builder()
				.answerId(iterAnswer.getId())
				.questionId(question.getId())
				.questionTitle(question.getTitle())
				.questionContent(question.getContent())
				.timestamp(iterAnswer.getCreatedAt()
					.atZone(TZ_SEOUL)
					.toInstant()
					.toEpochMilli())
				.likeCount(iterAnswer.getLikes())
				.isLiked(flag)
				.answerContent(iterAnswer.getContent())
				.build();
			result.add(userAnswerDto);
		}
		return result.stream()
			.sorted(Comparator.comparing(UserAnswerDto::getAnswerId).reversed())
			.collect(Collectors.toList());
	}

	public List<UserQuestionDto> getUserQuestionDtoList(Long uid, Long id) {
		Set<Question> questionList = repository.findById(uid)
			.orElseThrow(UserNotFoundException::new)
			.getQuestions();

		User user = repository.findById(uid).orElseThrow(UserNotFoundException::new);
		SimpleUserInfo simpleUserInfo = SimpleUserInfo.from(user);
		List<UserQuestionDto> result = new ArrayList<>();
		for (Question iterQuestion : questionList) {
			boolean likeFlag = likesRepository.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(
				id, iterQuestion.getId(),
				TargetType.QUESTION).isPresent();

			boolean bookmarkFlag = bookmarkRepository.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(
				id, iterQuestion.getId(),
				TargetType.QUESTION).isPresent();

			String iterTag = iterQuestion.getTags();
			String[] iterTagList = {};
			if (!iterTag.isEmpty()) {
				iterTagList = iterTag.split(",");
			}

			UserQuestionDto userQuestionDto = UserQuestionDto.builder()
				.questionId(iterQuestion.getId())
				.author(simpleUserInfo)
				.category(iterQuestion.getCategoryName().getCategory())
				.title(iterQuestion.getTitle())
				.content(iterQuestion.getContent())
				.tags(iterTagList)
				.likeCount(iterQuestion.getLikes())
				.answerCount(iterQuestion.getAnswers().size())
				.viewCount(iterQuestion.getViewCount())
				.timeStamp(iterQuestion.getCreatedAt()
					.atZone(TZ_SEOUL)
					.toInstant()
					.toEpochMilli())
				.isLiked(likeFlag)
				.isSolved(iterQuestion.getChosen())
				.isBookmarked(bookmarkFlag)
				.build();
			result.add(userQuestionDto);
		}
		return result.stream()
			.sorted(Comparator.comparing(UserQuestionDto::getQuestionId).reversed())
			.collect(Collectors.toList());
	}

	public List<UserFeedDto> findRecentFeedList(Long id) {
		final List<Read> recentReadList = readRepository.findTop10ByUserIdOrderByIdDesc(id);

		List<UserFeedDto> result = new ArrayList<>();
		for(Read read : recentReadList){
			final String esId = read.getFeed().getEsId();
			final ESFeed esFeed = eSFeedRepository.findById(esId).orElse(new ESFeed());

			boolean isBookmarked = bookmarkRepository
				.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(read.getUser().getId(), read.getFeed().getId(),
					TargetType.FEED).isPresent();

			result.add(UserFeedDto.from(read.getFeed(), esFeed, isBookmarked));
		}

		return result.stream()
			.sorted(Comparator.comparing(UserFeedDto::getFeedId))
			.collect(Collectors.toList());
	}

	public List<UserSubscriptionDto> findSubscriptionList(Long userId, Long id) {
		Set<Subscription> subscriptionSet = repository.findById(userId)
			.orElseThrow(UserNotFoundException::new)
			.getSubscriptions();

		List<UserSubscriptionDto> result = new ArrayList<>();
		for (Subscription subscription : subscriptionSet) {
			Company company = subscription.getCompany();

			boolean flag = subscriptionRepository
				.findByUserIdAndCompanyId(id, company.getId())
				.isPresent();

			UserSubscriptionDto userSubscriptionDto = UserSubscriptionDto.builder()
				.companyId(company.getId())
				.companyName(company.getName())
				.isSubscribe(flag)
				.build();

			result.add(userSubscriptionDto);
		}
		return result.stream()
			.sorted(Comparator.comparing(UserSubscriptionDto::getCompanyId))
			.collect(Collectors.toList());
	}

	/**
	 * Param1 유저를 Param2 유저가 Follow 중인가?
	 * @param targetId
	 * @param id
	 * @return
	 */
	public Boolean getIsFollowed(Long targetId, Long id) {
		return followRepository.findByFollower_IdAndFollowee_Id(id, targetId).isPresent() ? true : false;
	}
}