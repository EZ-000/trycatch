package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.CompanyRepository;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.ESUser;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESUserRepository;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.domain.FeedRepository;
import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.feed.domain.ReadRepository;
import com.ssafy.trycatch.feed.service.exception.FeedNotFoundException;
import com.ssafy.trycatch.user.domain.Subscription;
import com.ssafy.trycatch.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FeedService {
	private final FeedRepository feedRepository;
	private final ESFeedRepository esFeedRepository;
	private final CompanyRepository companyRepository;
	private final ReadRepository readRepository;
    private final ESUserRepository esUserRepository;

    @Autowired
    public FeedService(
            FeedRepository feedRepository,
            ESFeedRepository esFeedRepository,
            CompanyRepository companyRepository,
            ESUserRepository esUserRepository,
		ReadRepository readRepository
    ) {
        this.feedRepository = feedRepository;
        this.esFeedRepository = esFeedRepository;
        this.companyRepository = companyRepository;
        this.esUserRepository = esUserRepository;
		this.readRepository = readRepository;
    }

    public String findIconByCompany(Long companyId) {
        return this.companyRepository.findById(companyId)
                                     .orElse(Company.builder().icon("").build())
                                     .getIcon();
    }

	public Feed findById(Long feedId) {
		return feedRepository.findById(feedId)
			.orElseThrow(FeedNotFoundException::new);
	}

    public Feed findByESId(String esId) {
        return feedRepository.findByEsId(esId)
                .orElseThrow(FeedNotFoundException::new);
    }

	public void readFeed(User requestUser, Long feedId) {
		Feed feed = feedRepository.findById(feedId).orElseThrow(FeedNotFoundException::new);
		readRepository.save(Read.builder()
			.user(requestUser)
			.feed(feed)
			.readAt(Instant.now())
			.build());
	}

	public List<Company> getTop5CompanyList() {
		List<Long> top5PopularCompanyId = companyRepository.findTop5PopularCompany();
		return top5PopularCompanyId.stream()
			.map(e -> companyRepository.findById(e).get())
			.collect(Collectors.toList());
	}

    public Long findFeedIdByIdOrNull(Long feedId) {
        return feedRepository.findById(feedId)
                .orElse(new Feed())
                .getId();
    }

    public Page<ESFeed> TrueQueryTrueVectorTrueSubscribeTrueAdvanced(User requestUser, String query, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.TrueQueryTrueVectorTrueSubscribeTrueAdvanced(query, esUser.getVector(), subscribes, pageable);
    }

    public Page<ESFeed> TrueQueryTrueVectorTrueSubscribeFalseAdvanced(User requestUser, String query, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.TrueQueryTrueVectorTrueSubscribeFalseAdvanced(query, esUser.getVector(), subscribes, pageable);
    }

    public Page<ESFeed> TrueQueryTrueVectorFalseSubscribeTrueAdvanced(User requestUser, String query, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        return esFeedRepository.TrueQueryTrueVectorFalseSubscribeTrueAdvanced(query, esUser.getVector(), pageable);
    }

    public Page<ESFeed> TrueQueryTrueVectorFalseSubscribeFalseAdvanced(User requestUser, String query, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        return esFeedRepository.TrueQueryTrueVectorFalseSubscribeFalseAdvanced(query, esUser.getVector(), pageable);
    }

    public Page<ESFeed> TrueQueryFalseVectorTrueSubscribeTrueAdvanced(User requestUser, String query, Pageable pageable) {
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.TrueQueryFalseVectorTrueSubscribeTrueAdvanced(query, subscribes, pageable);
    }

    public Page<ESFeed> TrueQueryFalseVectorTrueSubscribeFalseAdvanced(User requestUser, String query, Pageable pageable) {
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.TrueQueryFalseVectorTrueSubscribeFalseAdvanced(query, subscribes, pageable);
    }

    public Page<ESFeed> TrueQueryFalseVectorFalseSubscribeTrueAdvanced(String query, Pageable pageable) {
        return esFeedRepository.TrueQueryFalseVectorFalseSubscribeTrueAdvanced(query, pageable);
    }

    public Page<ESFeed> TrueQueryFalseVectorFalseSubscribeFalseAdvanced(String query, Pageable pageable) {
        return esFeedRepository.TrueQueryFalseVectorFalseSubscribeFalseAdvanced(query, pageable);
    }

    public Page<ESFeed> FalseQueryTrueVectorTrueSubscribeTrueAdvanced(User requestUser, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.FalseQueryTrueVectorTrueSubscribeTrueAdvanced(esUser.getVector(), subscribes, pageable);
    }

    public Page<ESFeed> FalseQueryTrueVectorTrueSubscribeFalseAdvanced(User requestUser, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.FalseQueryTrueVectorTrueSubscribeFalseAdvanced(esUser.getVector(), subscribes, pageable);
    }

    public Page<ESFeed> FalseQueryTrueVectorFalseSubscribeTrueAdvanced(User requestUser, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        return esFeedRepository.FalseQueryTrueVectorFalseSubscribeTrueAdvanced(esUser.getVector(), pageable);
    }

    public Page<ESFeed> FalseQueryTrueVectorFalseSubscribeFalseAdvanced(User requestUser, Pageable pageable) {
        final ESUser esUser = esUserRepository.findByUid(requestUser.getId()).orElseThrow();
        return esFeedRepository.FalseQueryTrueVectorFalseSubscribeFalseAdvanced(esUser.getVector(), pageable);
    }

    public Page<ESFeed> FalseQueryFalseVectorTrueSubscribeTrueAdvanced(User requestUser, Pageable pageable) {
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.FalseQueryFalseVectorTrueSubscribeTrueAdvanced(subscribes, pageable);
    }

    public Page<ESFeed> FalseQueryFalseVectorTrueSubscribeFalseAdvanced(User requestUser, Pageable pageable) {
        final List<Long> subscribes = requestUser.getSubscriptions()
                .stream()
                .map(Subscription::getId)
                .collect(Collectors.toList());
        return esFeedRepository.FalseQueryFalseVectorTrueSubscribeFalseAdvanced(subscribes, pageable);
    }

    public Page<ESFeed> FalseQueryFalseVectorFalseSubscribeTrueAdvanced(Pageable pageable) {
        return esFeedRepository.FalseQueryFalseVectorFalseSubscribeTrueAdvanced(pageable);
    }

    public Page<ESFeed> FalseQueryFalseVectorFalseSubscribeFalseAdvanced(Pageable pageable) {
        return esFeedRepository.FalseQueryFalseVectorFalseSubscribeFalseAdvanced(pageable);
    }
}
