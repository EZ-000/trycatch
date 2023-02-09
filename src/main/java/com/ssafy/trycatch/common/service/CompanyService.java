package com.ssafy.trycatch.common.service;

import static com.ssafy.trycatch.common.infra.config.ConstValues.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trycatch.common.domain.BookmarkRepository;
import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.CompanyRepository;
import com.ssafy.trycatch.common.domain.TargetType;
import com.ssafy.trycatch.common.service.exceptions.CompanyNotFoundException;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.service.exception.ESFeedNotFoundException;
import com.ssafy.trycatch.user.controller.dto.UserFeedDto;
import com.ssafy.trycatch.user.domain.User;

@Service
public class CompanyService extends CrudService<Company, Long, CompanyRepository> {

	private final ESFeedRepository eSFeedRepository;
	private final BookmarkRepository bookmarkRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository,
		ESFeedRepository eSFeedRepository,
		BookmarkRepository bookmarkRepository) {
		super(companyRepository);
		this.eSFeedRepository = eSFeedRepository;
		this.bookmarkRepository = bookmarkRepository;
	}

	public List<String> findCompanyLogos() {
		return repository.findAllByLogoIsNotNull()
			.stream()
			.map(Company::getLogo)
			.collect(Collectors.toList());
	}

	public Long findCompanyIdByCompanyName(String companyName) {
		return repository.findCompanyIdByName(companyName)
			.orElseThrow(CompanyNotFoundException::new);
	}

	public List<UserFeedDto> findFeedList(Company company, User requestUser) {
		Set<Feed> feeds = company.getFeeds();
		List<UserFeedDto> result = new ArrayList<>();

		for (Feed feed : feeds) {
			boolean flag = bookmarkRepository
				.findFirstByUserIdAndTargetIdAndTargetTypeOrderByIdDesc(requestUser.getId(), feed.getId(),
					TargetType.FEED).isPresent();
			ESFeed esFeed = eSFeedRepository.findById(feed.getEsId()).orElseThrow(ESFeedNotFoundException::new);
			UserFeedDto userFeedDto = UserFeedDto.builder()
				.feedId(feed.getId())
				.title(feed.getTitle())
				.summary(esFeed.getSummary())
				.companyName(company.getName())
				.createdAt(feed.getCreatedAt().atStartOfDay()
					.atZone(TZ_SEOUL)
					.toInstant()
					.toEpochMilli())
				.tags(esFeed.getTags())
				.isBookmarked(flag)
				.url(esFeed.getUrl())
				.thumbnailImage(esFeed.getThumbnailUrl())
				.keywords(esFeed.getKeywords())
				.build();
			result.add(userFeedDto);
		}

		return result;
	}
}
