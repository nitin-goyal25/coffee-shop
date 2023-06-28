# Bestseller Coffee shop Backend assessment 
The main use case for these Application is to create a back-end for coffee store. There are two CRUD operation, one for Product and other for topping.
And two other API has been exposed, one is /order for adding the product in cart and get the cart amount and second is finalize-order which will calculate the final amount of the cart after applying discount logic which will be explained further. There is one more API to fetch the most used toppings.

### The project has been developed using Java 17 and Spring boot 2.7.13
### I have used lombok dependency for logging. If the project is imported in any IDE e.g eclipse, IntelliJ the lombok jar needs to be run.
### I have added h2 dependency and have used h2 DB for storing the toppings and product.
### Created two tables product and toppings in the DB. The DDL(schmea.sql) is inside the project in src/main/resource folder.
### The postman collection has been attached and also present inside the project.
### The swagger has been implemented for the API documentation, same can be accessed by below URL after running the Project : http://localhost:8080/swagger-ui.html

#Following Functionality has been Implemented : 
## CRUD for Product(drinks) - Supports CREATE, UPDATE, DELETE , and GET API for Product.
## CRUD for toppings - Supports CREATE, UPDATE, DELETE , and GET API for toppin.
## /order API - This API will be called to add the product in cart with different toppings and calculate the amount of the cart.
## /finalized-order - This API will be called to calculate the final amount of the cart. After applying the discount logic.
## /mostUsedtoppings - This will be used to return the most ordered toppings. 

.

#Approach followed for POST /order 

1.The input request will support multiple product with multiple toppings for each product and will calculate the current total amount of the cart as well as amount for each individual product as well.
2. I have maintained a used_count column in the topping table which will be updated to current value+1 when a topping will be used,
   
### Post /order
Method : POST
URL : /order


Response Body:

{"products":[{"name":"Black Coffee","id":1,"toppings":[{"name":"Milk","id":5}]},{"name":"Latte","id":2,"toppings":[{"name":"Hazelnut syrup","id":6}]},{"name":"Mocha","id":3,"toppings":[{"name":"Chocolate sauce","id":7}]}]}

Response body : 
{"products":[{"name":"Black Coffee","id":1,"toppings":[{"name":"Milk","id":5}],"productAmount":6},{"name":"Latte","id":2,"toppings":[{"name":"Hazelnut syrup","id":6}],"productAmount":8},{"name":"Mocha","id":3,"toppings":[{"name":"Chocolate sauce","id":7}],"productAmount":11}],"cartAmount":25}

    

#Approach followed for POST /finalize-order  
1. As the initial amount of each product and the total amount of the cart is already calculated in the previous /order API, so this API will help to calculate the final amount after applying discount.
2. If there are 3 or more than 3 products in the cart , the product with lowest amount will not be added in the final price.
3. If the total cart value is greater than 12 $, there is a discount of 25% in the final order.
4. If the cart has more than 3 products and total cart value is greater than $, the cart value which will be lesser after applying both discount individually will be the final amout of the cart.  

### Post /finalize-order
Method : POST
URL : /finalize-order

Response Body:

{"products":[{"name":"Black Coffee","id":1,"toppings":[{"name":"Milk","id":5}],"productAmount":6},{"name":"Latte","id":2,"toppings":[{"name":"Hazelnut syrup","id":6}],"productAmount":8},{"name":"Mocha","id":3,"toppings":[{"name":"Chocolate sauce","id":7}],"productAmount":11}],"cartAmount":25}

Response body :
{"products":[{"name":"Black Coffee","id":1,"toppings":[{"name":"Milk","id":5}],"productAmount":6},{"name":"Latte","id":2,"toppings":[{"name":"Hazelnut syrup","id":6}],"productAmount":8},{"name":"Mocha","id":3,"toppings":[{"name":"Chocolate sauce","id":7}],"productAmount":11}],"cartAmount":25,"discountedAmount":18.75} 



## Error codes

Error Response:

    {
       "errorCode": 1001,
       "errorMessage": "Product with given Id does not exists"
    }

Error codes:

    1001 - Product with given Id does not exists.
    1002 - Topping with given Id does not exists
    9999 - Internal Server Error