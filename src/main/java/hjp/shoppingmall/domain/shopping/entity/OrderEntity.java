package hjp.shoppingmall.domain.shopping.entity;

import hjp.shoppingmall.domain.shopping.dto.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "Order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주문 ID

    private Long memberId;  // 소비자 (구매자) ID
    private Long productId;  // 상품 ID
    private Integer quantity;  // 구매 수량
    private Double totalPrice;  // 총 가격

    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // 주문 상태 (예: 결제 대기, 결제 완료, 배송 중 등)

    private LocalDateTime createdAt;  // 주문 생성일
    private LocalDateTime updatedAt;  // 주문 상태 변경일

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}