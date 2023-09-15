Add Product Scenarios
=====================
Created by ercan kirbiyik


Scenario 2.1 - Add Product to Basket
------------------------------------
* Login with "rcnkirbiyik@gmail.com" and "Ercan30."
* Check if element "link account-user" contains text "Hesabım"
* Find element by "suggestion" clear and send keys "Oyuncu Bilgisayarı"
* Send ENTER key to element "suggestion"
* Wait "2" seconds
* Click to element "monster filter checkbox"
* Click to element "price filter head"
* Find element by "price filter min input" clear and send keys "3000"
* Find element by "price filter max input" clear and send keys "10000"
* Click to element "price filter search button"
* Wait "2" seconds
* Random click in "products images container list" list
* Wait "3" seconds
* Click to element "add to cart button"
* Check if element "product preview status text" contains text "Ürün Sepete Eklendi!"


Scenario 2.2 - Add Product to Favorites
---------------------------------------
* Login with "rcnkirbiyik@gmail.com" and "Ercan30."
* Check if element "link account-user" contains text "Hesabım"
* Find element by "suggestion" clear and send keys "Gömlek"
* Send ENTER key to element "suggestion"
* Wait "2" seconds
* Random click in "add to favorite button list" list
* Click to element "favorites container button"
* Wait "5" seconds
* Click to element "favorites add to basket button"
