package hjp.shoppingmall.domain.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductPageResponseDto {

    private String mainTitle;
    private LocalDate createdAt;
    private String description;
    private String category;
    private Double price;
    private Integer stock;
    private String productStatus;
    private List<String> imageUrls;
    private String detailedDescription;
    private List<String> productOptions;
    private Long createdBy;
}