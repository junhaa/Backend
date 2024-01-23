package com.bid.auction.domain.payment.entity;

import com.bid.auction.domain.payment.enums.PaymentOrderStatus;
import com.bid.auction.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_payment_order_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String pgPaymentOrderUid;
    @Column(nullable = false, unique = true)
    private String merchantPaymentOrderUid;
    @Column(nullable = false)
    private Integer paymentOrderAmount;
    @Column(nullable = false)
    private PaymentOrderStatus orderStatus;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User orderingUser;

    @Builder
    public PaymentOrder(String pgPaymentOrderUid, String merchantPaymentOrderUid, Integer paymentOrderAmount,
                        PaymentOrderStatus orderStatus, LocalDateTime requestedAt, User orderingUser) {
        this.pgPaymentOrderUid = pgPaymentOrderUid;
        this.merchantPaymentOrderUid = merchantPaymentOrderUid;
        this.paymentOrderAmount = paymentOrderAmount;
        this.orderStatus = orderStatus;
        this.requestedAt = requestedAt;
        this.orderingUser = orderingUser;
    }
}
