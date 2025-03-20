package hjp.shoppingmall.domain.shopping.entity;

import hjp.shoppingmall.domain.member.entity.MemberEntity;
import hjp.shoppingmall.domain.shopping.dto.ProductPageResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ProductPage" )
public class ProductPageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long createdById;
    private String mainTitle;       // 상품의 제목
    private LocalDate createdAt;    // 생성일
    private String description;     // 간단한 설명
    private String category;        // 카테고리
    private Double price;           // 가격
    private Integer stock;          // 재고 수량
    private String productStatus;   // 상품 상태 (판매중, 품절 등)

    @ElementCollection
    private List<String> imageUrls; // 이미지 URL 목록

    @Lob
    private String detailedDescription; // 상세 설명

    @ElementCollection
    private List<String> productOptions; // 상품 옵션 목록 (예: 색상, 사이즈)

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    public ProductPageResponseDto toResponse() {
        return new ProductPageResponseDto(
                this.mainTitle,
                this.createdAt,
                this.description,
                this.category,
                this.price,
                this.stock,
                this.productStatus,
                this.imageUrls,
                this.detailedDescription,
                this.productOptions,
                this.createdById
        );
    }
}

