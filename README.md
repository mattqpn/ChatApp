For the 450A Mobile Application Project there were a list of functionalities that were not not implemented for each aspect of the project such as chat and notifications, contacts, weather, and login components. The Raindrop App can change between two different color palettes with future colors coming soon. The goal, is for some of the students to finish the project during break to put together how the project should be.
 
Starting with the chat aspects, we did not complete adding or subtracting chatrooms. Additionally, modifying states of the chatrooms including  adding and subtracting new users and modifying the chatroom names was not completed. However, users are able to see their current chat rooms that they are involved in and cycle through what chatrooms they are invovled in and enter and leave those chat rooms. Moreover, for notifications the basic notifications to receive a message while in the app is the only aspect that works. Unfortunately users are not able to see notifications outside of the app or notifications that aren't related to messages.

Moving on to the contact portion of the project, you cannot search a contact or send a friend request. At most you can view your contacts.

Futhermore on the weather service, a user is able to select Weather on the bottom navigation ribbon. The user is then able to choose between: Search by City, Search by Zipcode, and Search by Location. Search by City will change to a window where the user can type in a city and optionally a country. The user can then select current weather, hourly forecast, and 5-day forecast. Each of the buttons will cause weather information to be displayed for the: temperature, feels like (referring to how the temperature actually feels), humidity, description (of what the weather is), wind speed, cloudiness, and air pressure. Search by Zipcode is the exact same as Search by City except the user should enter a zipcode instead of a city and optionally a country. The same information and buttons are present. Search by Location is slightly different. The user does not have to enter any text and instead just has the same button options as the other Search by options: Current Weather, Hourly Forecast, and 5-day Forecast. The information is obtained through GPS to get the latitude and longitude. All information is obtained through https GET requests and parsed in the Fragments. Maps is not working. The JWTs are obtained directly from the weather API and do not go through our own API.

Finally the registration/login performance, a user is able to register a new account with an email and log in to the app with that account. When a user registers an account an email is sent by the web service welcoming them and providing a verification code. The web server does check on login for if an account's email is verified or not, and sends a different response message for both. However the functionality to update the verification status in the web service database is currently unfunctional, so while ui elements for entering verification code are with in the application, they are not called and the user is signed in regardless of email verification status as long as their credentials are correct. A user will stay logged in even when closing the app, and will be able to log out with a item on the drop down menu. A user can change their password by opening the "change password" item on the drop down menu, they will need to enter their email address, their current password, and a new password twice. When the password is changed they will be sent an email and will return to previous fragment. An option to recover a lost password was not implemented.


There are currently two guest for users to try out if they hope to try out the app without signing up and
the credentials include the following.

	Test1
		Email: test1@test.com
		Password: test12345

	Test2
		Email: test2@test.com
		Password: test12345

For any further questions feel free to reach out to any of the following emails.
Elijah Reyes: elibreyes@gmail.com

Google drive meeting notes:
https://drive.google.com/drive/folders/1AMUpROnKqJwxlZrll0TVeKfNOo46Grvp?usp=sharing

Android Github repository:
https://github.com/CKojiro/Team_6_TCSS_450

Server-side Github repository:
https://github.com/cfb3/Team_6_TCSS_450_Web_Service


