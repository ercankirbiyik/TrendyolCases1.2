Login And Sign Up Scenarios
===========================
Created by ercan kirbiyik


Scenario 1.1 - Login:
---------------------
* Login with "rcnkirbiyik@gmail.com" and "Ercan30."
* Check if element "link account-user" contains text "Hesabım"


Scenario 1.2 - Sign Up:
-----------------------
* Click to element "link account-user"
* Click to element "sign up button"
* Find element by "register email" clear and send keys "rcnkirbiyik@gmail.com"
* Find element by "register password" clear and send keys "Ek12345678."
* Click to element "personal checkbox"
* Click to element "recaptcha checkbox"
* Click to element "submit button"
* Find element by "code input" clear and send keys "123456"
* Click to element "code submit button"
* Check if element "link account-user" contains text "Hesabım"