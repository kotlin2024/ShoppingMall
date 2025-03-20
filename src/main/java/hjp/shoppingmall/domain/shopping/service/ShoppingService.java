package hjp.shoppingmall.domain.shopping.service;

import hjp.shoppingmall.domain.shopping.dto.ProductPageRequestDto;
import hjp.shoppingmall.domain.shopping.dto.ProductPageResponseDto;
import hjp.shoppingmall.domain.shopping.entity.ProductPageEntity;
import hjp.shoppingmall.domain.shopping.repository.ProductPageRepository;
import hjp.shoppingmall.security.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {

    public final ProductPageRepository productPageRepository;

    public ShoppingService(ProductPageRepository productPageRepository) {
        this.productPageRepository = productPageRepository;
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    public ProductPageResponseDto createProductPage(ProductPageRequestDto productPageRequestDto) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductPageEntity productPageEntity = new ProductPageEntity();
        productPageEntity.setMainTitle(productPageRequestDto.getMainTitle());
        productPageEntity.setDescription(productPageRequestDto.getDescription());
        productPageEntity.setCategory(productPageRequestDto.getCategory());
        productPageEntity.setPrice(productPageRequestDto.getPrice());
        productPageEntity.setStock(productPageRequestDto.getStock());
        productPageEntity.setProductStatus(productPageRequestDto.getProductStatus());
        productPageEntity.setImageUrls(productPageRequestDto.getImageUrls());
        productPageEntity.setDetailedDescription(productPageRequestDto.getDetailedDescription());
        productPageEntity.setProductOptions(productPageRequestDto.getProductOptions());
        productPageEntity.setCreatedById(userPrincipal.getMemberId());

        productPageEntity = productPageRepository.save(productPageEntity);

        return productPageEntity.toResponse();
    }
}
