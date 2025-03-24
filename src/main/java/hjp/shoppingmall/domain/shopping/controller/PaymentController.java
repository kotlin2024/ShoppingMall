package hjp.shoppingmall.domain.shopping.controller;


import com.stripe.model.PaymentIntent;
import hjp.shoppingmall.domain.shopping.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestParam long amount) {
        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount);

        if (paymentIntent != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("id", paymentIntent.getId());
            response.put("amount", paymentIntent.getAmount());
            response.put("currency", paymentIntent.getCurrency());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(500).body(Collections.singletonMap("error", "Failed to create payment intent"));
        }
    }
}