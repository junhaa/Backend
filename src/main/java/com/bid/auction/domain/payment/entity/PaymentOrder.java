package com.bid.auction.domain.payment.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.bid.auction.domain.payment.enums.PaymentOrderStatus;
import com.bid.auction.domain.user.domain.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_order_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String pgUid;
	@Column(nullable = false, unique = true)
	private String merchantUid;
	@Column(nullable = false)
	private Integer paymentOrderAmount;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PaymentOrderStatus orderStatus;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime requestedAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User orderingUser;

	@Builder
	public PaymentOrder(String pgUid, String merchantUid, Integer paymentOrderAmount,
		PaymentOrderStatus orderStatus, LocalDateTime requestedAt,
		User orderingUser) {
		this.pgUid = pgUid;
		this.merchantUid = merchantUid;
		this.paymentOrderAmount = paymentOrderAmount;
		this.requestedAt = requestedAt;
		this.orderingUser = orderingUser;

		if (orderStatus.equals(PaymentOrderStatus._PAID)
			|| orderStatus.equals(PaymentOrderStatus._ALL)) {
			this.orderStatus = PaymentOrderStatus._READY;
		} else {
			this.orderStatus = orderStatus;
		}
	}

	public void orderComplete() {
		orderStatus = PaymentOrderStatus._PAID;
	}
}
