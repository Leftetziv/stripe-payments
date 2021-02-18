/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.stripe.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Leyteris
 */
@Service
public class StripeService2 {
    
    @Value("${STRIPE_SECRET_KEY}")
    private String API_SECRET_KEY;

    public Session getSession() throws StripeException {
        SessionCreateParams params
                = SessionCreateParams.builder()
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Pro Package")
                                                                        .build())
                                                        .setUnitAmount(1500L)
                                                        .setCurrency("eur")
                                                        .build())
                                        .setQuantity(1L)
                                        .build())
                        .setMode(SessionCreateParams.Mode.PAYMENT)
//                        .setCustomer()
                        .setCustomerEmail("a@b.com")
                        .setSuccessUrl("http://localhost:8080/Stripe/version2/success")
                        .setCancelUrl("http://localhost:8080/Stripe/version2/failed")
                        .build();
        Stripe.apiKey = API_SECRET_KEY;
        Session session = Session.create(params);
        return session;
    }
}
