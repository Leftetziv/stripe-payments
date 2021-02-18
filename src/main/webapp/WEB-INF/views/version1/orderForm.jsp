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
    </head>
    <body>
        <h1>Hello World!</h1>
        <h1>Order Form</h1>
        <form action='${pageContext.request.contextPath}/version1/charge' method='POST' id='checkout-form'>
            <input type='hidden' value='${amount/100}' name='amount' />
            <h1>Price: ${amount/100}</h1>
            <script
                src='https://checkout.stripe.com/checkout.js'
                class='stripe-button'
                data-key=${stripePublicKey}
                data-amount=${amount}
                data-name='Titlos 1'
                data-description='titlos 2'
                data-image='${pageContext.request.contextPath}/duck.jpg'
                data-locale='auto'
                data-zip-code='false'
                data-email='a@b.com'>
            </script>
        </form>
    </body>
</html>
