package com.bid.auction.domain.payment.entity;

import java.time.LocalDateTime;

import com.bid.auction.domain.payment.enums.PaymentMethod;
import com.bid.auction.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long id;

	@Column(nullable = false)
	private Integer amount;
	@Column(nullable = false)
	private LocalDateTime requestedAt;
	@Column(nullable = false)
	private LocalDateTime approvedAt;
	@Column(nullable = false, unique = true)
	private String merchantPaymentUid;
	@Column(nullable = false, unique = true)
	private String pgPaymentUid;
	@Enumerated(value = EnumType.STRING)
	private PaymentMethod paymentMethod;
	@OneToOne
	@JoinColumn(name = "payment_order_id")
	private PaymentOrder paymentOrder;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User paidUser;

	@Builder
	public Payment(Integer amount, LocalDateTime requestedAt, LocalDateTime approvedAt, String merchantPaymentUid,
		String pgPaymentUid, PaymentMethod paymentMethod, PaymentOrder paymentOrder, User paidUser) {
		this.amount = amount;
		this.requestedAt = requestedAt;
		this.approvedAt = approvedAt;
		this.merchantPaymentUid = merchantPaymentUid;
		this.pgPaymentUid = pgPaymentUid;
		this.paymentMethod = paymentMethod;
		this.paymentOrder = paymentOrder;
		this.paidUser = paidUser;
	}
}
