<%-- 
    Document   : checkout
    Created on : Feb 18, 2021, 1:39:39 PM
    Author     : Leyteris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://js.stripe.com/v3/"></script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1>Order Form</h1>

        <button id="checkout-button">Checkout</button>

        <script>
            var stripe = Stripe('pk_test_51IMGIODKnKruXoAojAtP2eTHmE9IImfv8zbWI0jdmYWHWM1cIAhkuOiQiQwGgUA9u4efQI0ePMyLpzMYLUnMeGCv00LR7GH2Bq');

            var checkoutButton = document.getElementById('checkout-button');

            checkoutButton.addEventListener('click', function () {
                stripe.redirectToCheckout({
                    // Make the id field from the Checkout Session creation API response
                    // available to this file, so you can provide it as argument here
                    // instead of the {{CHECKOUT_SESSION_ID}} placeholder.
                    sessionId: '${stripeSessionId}'
                }).then(function (result) {
                    console.log(result)
                    if (result.error) {
                        alert(result.error.message);
                    }
                    // If `redirectToCheckout` fails due to a browser or network
                    // error, display the localized error message to your customer
                    // using `result.error.message`.

                });
            });

        </script>
    </body>
</html>
