package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.core.exception.ReservationSoldOutException;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceShowing {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performenc_id",nullable = false)
	private Performance performance;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "showroom_id", nullable = false)
	private PerformanceShowroom performanceShowroom;

	@OneToMany(mappedBy = "performanceShowing",orphanRemoval = true)
	private List<PerformanceReservation> reservationList = new ArrayList<>();

	@OneToMany(mappedBy = "showing",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<PerformanceShowingObserver> observers;

	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false, name = "reservation_available")
	private boolean reservationAvailable;

	private PerformanceShowing(Performance performance, PerformanceShowroom performanceShowroom,  int round,
		LocalDate startDate, boolean reservationAvailable) {
		this.performance = performance;
		this.performanceShowroom = performanceShowroom;
		this.round = round;
		this.startDate = startDate;
		this.reservationAvailable = reservationAvailable;
	}

	public static PerformanceShowing create(Performance performance, PerformanceShowroom performanceShowroom, int round,
		LocalDate startDate, boolean reservationAvailable) {
		return new PerformanceShowing(performance, performanceShowroom,round,startDate,reservationAvailable);
	}


	public PerformanceReservation reserve(ReservationRequest request) {
		if(!reservationAvailable)
			throw new ReservationSoldOutException();
		int fee = performance.calculateFee();
		return PerformanceReservation.of(this, request, fee);
	}

	public void soldOut() {
		this.reservationAvailable = false;
	}

	public void addReservation(PerformanceReservation reservation) {
		this.reservationList.add(reservation);
	}
}
