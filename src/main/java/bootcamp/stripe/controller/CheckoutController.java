/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bootcamp.stripe.controller;

import bootcamp.stripe.service.StripeService1;
import bootcamp.stripe.service.StripeService2;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Leyteris
 */
//todo split controllers
@Controller
public class CheckoutController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~version1~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private StripeService1 stripeService1;

    @GetMapping("/version1")
    public String showV1(Model model) {
        model.addAttribute("amount", 50 * 100); // In cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "version1/orderForm";
    }

    @PostMapping(value = "/version1/charge")
    public String chargeCard(HttpServletRequest request) throws Exception {
        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService1.chargeNewCard(token, amount);

        System.out.println("Stripe auth succesfull");
        return "version1/succesfullTransaction";
    }
    
    @ExceptionHandler(com.stripe.exception.CardException.class)
    public String handleError() {
        System.out.println("Error during Stripe auth");
        return "version1/failedTransaction";
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~version2~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    @Autowired
    private StripeService2 stripeService2;
    
    @GetMapping(value = "/version2")
    public String chargeCard2(Model model) throws StripeException {
        Session session = stripeService2.getSession();   
        
        model.addAttribute("stripeSessionId", session.getId());
        System.out.println("V2 session>>>>>>>>>>"+session.toJson());
        return "version2/orderForm";
    }
    
    @GetMapping("/version2/success")
    public String showSuccess() {
        return "version2/succesfullTransaction";
    }
    @GetMapping("/version2/failed")
    public String showFailed() {
        return "version2/failedTransaction";
    }
    
    
    @PostMapping("/hooks")
    @ResponseBody
    public ResponseEntity<String> getHook(@RequestBody Event e) {
//        if (e.getType().equals("payment_intent.succeeded")) {
//            System.out.println("Hook event.id>>>>>>>>>>"+e.toJson());
//        }

        System.out.println(e.toJson());
        return ResponseEntity.ok().build();
    }

}
