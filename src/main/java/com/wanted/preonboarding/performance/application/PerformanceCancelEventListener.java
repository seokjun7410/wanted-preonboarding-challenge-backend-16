package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.domain.PerformanceShowingObserver;
import com.wanted.preonboarding.performance.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceShowingObserverRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PerformanceCancelEventListener {

	private final PerformanceShowingObserverRepository repository;
	private final ShowingRepository showingRepository;
	private final PerformanceCancelEventService performanceCancelEventService;

	@Async()
	@EventListener
	public void listenCancelEvent(ReservationCancelEvent event) {
		performanceCancelEventService.canceled(event);
	}


	@Transactional
	public void subscribe(UUID showingId, UUID userId) {
		PerformanceShowing performanceShowing = showingRepository.findById(showingId)
			.orElseThrow(EntityNotFoundException::new);

		PerformanceShowingObserver observer = PerformanceShowingObserver.create(
			performanceShowing, userId);

		repository.save(observer);
	}
}
