package hjp.shoppingmall.domain.shopping.controller;

import hjp.shoppingmall.domain.shopping.dto.ProductPageRequestDto;
import hjp.shoppingmall.domain.shopping.dto.ProductPageResponseDto;
import hjp.shoppingmall.domain.shopping.service.ShoppingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductPageResponseDto> createProductPage(@RequestBody ProductPageRequestDto productPageRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingService.createProductPage(productPageRequestDto));
    }

}
