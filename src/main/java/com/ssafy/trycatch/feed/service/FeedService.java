package com.ssafy.trycatch.feed.service;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.common.domain.CompanyRepository;
import com.ssafy.trycatch.elasticsearch.domain.ESFeed;
import com.ssafy.trycatch.elasticsearch.domain.repository.ESFeedRepository;
import com.ssafy.trycatch.feed.domain.Feed;
import com.ssafy.trycatch.feed.domain.FeedRepository;
import com.ssafy.trycatch.feed.service.exception.ESFeedNotFoundException;
import com.ssafy.trycatch.feed.service.exception.FeedNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeedService {
	private final FeedRepository feedRepository;
	private final ESFeedRepository esFeedRepository;
	private final CompanyRepository companyRepository;

	@Autowired
	public FeedService(
		FeedRepository feedRepository,
		ESFeedRepository esFeedRepository,
		CompanyRepository companyRepository
	) {
		this.feedRepository = feedRepository;
		this.esFeedRepository = esFeedRepository;
		this.companyRepository = companyRepository;
	}

	public Page<ESFeed> findAll(Pageable pageable) {
		return esFeedRepository.findAll(pageable);
	}

	public Page<ESFeed> commonSearch(String query, Pageable pageable) {
		log.info(query);
		return esFeedRepository.searchByTitleOrContent(query, pageable);
	}

	public Page<ESFeed> advanceSearch(String queryString, Pageable pageable) {
		return esFeedRepository.searchByQueryString(queryString, pageable);
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

	public Feed findByESId(String feedId) {
		return feedRepository.findByEsId(feedId)
			.orElseThrow(FeedNotFoundException::new);
	}

	public ESFeed findESFeedByESId(String feedId) {
		return esFeedRepository.findById(feedId)
			.orElseThrow(ESFeedNotFoundException::new);
	}
}
