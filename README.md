# SPYFINDER_TNSKILLS
# Task
# TN Skills Development Document
Total Duration : 2 days
Test Description-based ASSESSMENT Criteria
1. Introduction
Skill Explained
Mobile application development skills encompass a diverse array of competencies essential for
creating software applications tailored for mobile devices like smartphones and tablets. At its
core, proficiency in programming languages lies at the foundation of mobile app development.
For native app development, developers typically rely on languages like Java or Kotlin for
Android and Swift or Objective-C for iOS platforms. Conversely, for cross-platform development,
languages such as JavaScript (with frameworks like React Native or NativeScript), Dart (with
Flutter), or C# (with Xamarin) are commonly employed.
A crucial aspect of mobile app development involves mastering the intricacies of development
environments. These environments, like Android Studio for Android development or Xcode for
iOS development, provide developers with the tools necessary to build, debug, and test their
applications effectively. Furthermore, understanding user interface (UI) design principles is
paramount. Proficiency in design tools such as Adobe XD, Sketch, or Figma enables developers
to craft visually appealing and intuitive interfaces that enhance the user experience.
Integration with various APIs (Application Programming Interfaces) is another critical skill in
mobile app development. APIs facilitate access to functionalities such as location-based
services, social media integration, and payment gateways, enhancing the app's capabilities.
Additionally, expertise in backend development is essential for managing data, user
authentication, and business logic. This entails familiarity with databases (SQL or NoSQL),
 
Module Assessment Device Marks
UI Design Adobe XD / Figma 20
Application Development Android Studio 30
server-side scripting languages (e.g., Node.js, Python, Ruby), and relevant frameworks like
Express.js or Django.
Security is a paramount concern in mobile app development. Developers must possess
knowledge of best practices to safeguard user data and mitigate common security threats.
Testing and debugging proficiency are also indispensable, encompassing various methodologies
like unit testing, integration testing, and UI testing. Leveraging debugging tools and platforms
such as Android Debug Bridge (ADB) and Xcode Instruments aids in identifying and resolving
issues effectively.
Moreover, expertise in version control systems like Git facilitates efficient codebase
management and collaboration among team members. Understanding the deployment process
and guidelines for app submission to platforms like the Google Play Store and Apple App Store is
crucial for reaching a wider audience. Continuous learning is imperative in this fast-evolving
field, enabling developers to stay abreast of emerging technologies, platforms, and
development practices.
In conclusion, mobile application development demands a multifaceted skill set encompassing
programming proficiency, design acumen, API integration, security expertise, testing proficiency,
and effective communication. Developers who master these skills can create high-quality
mobile applications that meet user needs and excel in today's dynamic mobile technology
landscape.
Purpose
The purpose of this document is to provide a guide for developing an mobile application that
fetches user details from an API using any third party API library and displays them in a list. This
application aims to demonstrate efficient data retrieval and presentation in Mobile Application
development.
Definitions and Acronyms
● API: Application Programming Interface
● Local Database: To store data in a local storage medium within the device for efficient
retrieval and management
● Background Worker: A background task scheduling library executing deferrable, reliable,
and battery-friendly tasks.
● Users Information List View: Presents detailed information about the users in a list.

2. System Overview
The Application will authenticate users and retrieve their details from a remote API endpoint,
displaying them in a user interface based on list. The application will seamlessly handle API
integration, data parsing, local database management and Background worker implementation
setup to ensure a smooth user experience.
3. Functional Requirements
User Interface Design Details
Login Screen Components:
● Inputs Required: Fields for the user to enter their email address and password.
● Action Button: A 'Login' button that the user can press to submit their
credentials.
● Loading Feedback: A visual indicator (like a spinning circle) should be displayed to
show that the application is processing or waiting for a response after the user
has pressed the login button.
Main Screen Functionality:
● Display of User Information: The main screen should present a list that displays
individual user details. This list acts as the central feature of the app once login is
successful.
● Design of List Items: Each entry in the list must be designed to clearly present
user information, such as name, email, etc., in an organized manner that is easy
to read at a glance.
UI Designing Task
● Design Deliverables:
● File Requirements: A design file created in Figma or Adobe XD showcasing the
app's user interface, specifically including the application logo, the login screen,
and the user list screen.
● Orientation and Elements: The designs should be in portrait orientation, focusing
on a clean and user-friendly presentation of the required elements.
API Integration Processes
HTTPS Requests:
● Capability Requirement: The application must be equipped to send secure
requests (HTTPS) to a specified API endpoint. This involves utilizing third-party
libraries designed for network communication.
 
● JSON Parsing: Automatically convert the JSON response from the API into usable
data within the app to display user details.
Error Handling:
● Network and Response Errors: Implement strategies to gracefully handle errors,
such as when there is no internet connection or when the API returns
unexpected data.
Local Database and Background Worker Tasks
Database Utilization:
● Offline Data Storage: User data should be stored in a local database to allow the
app to function without an internet connection and to retain data for later
access.
Background Data Refresh:
● Periodic Updates: Employ background tasks to periodically check for and retrieve
the latest user data from the API, looping through all available pages, updating
the local database with this new information.
● Data Cycling: Ensure that the app alternates between fetching data on all pages
of API, keeping the user information up to date.
User Information List View
● Efficient Data Display:
● List Implementation: The app must effectively display a list of user details, pulling
this information from the local database.
● User Details: Each list item should include key details such as the user's name
and email address.
● Performance Optimization: Implement the list view in a way that ensures smooth
scrolling and performance, especially with a large number of entries. This
includes optimizing data retrieval and view rendering.
5. Implementation and development
1. When someone first opens your app, they should see a screen where they can log in.
This screen asks for an email ID and password. After the user types these in, the app
checks to make sure the email and password are typed correctly. If everything looks
good, the app sends this information to a Login API to check if the user is who they say
they are. If the API says yes, the app takes the user to a new screen that shows a list of
users.

2. On this second screen, there will be a spinning icon or something similar showing while
the app is getting information about other users from the API in the background. After
the app gets this information, it saves it in a place on the phone or tablet so you can still
see the user list even if you're not connected to the internet. The app also sets up a way
to keep checking for new or updated user information regularly without you having to
do anything. It automatically saves any new information it finds.
3. The list of users you see on the screen will be easy to read and well-organized. There's
also a menu bar. In the menu, there are options to log out or refresh the list. If you
choose to log out, the app will take you back to the login screen and clear out the saved
user information to keep things private. If you hit the refresh button, the app looks for
any new or updated information about users, saves any changes, and updates what you
see on the screen.

6. Notes/Instructions
The media files are available in the ZIP file provided with the Test Project The images, logo, and
characters can be modified to increase the aesthetics /quality /design of the project. You can
modify the supplied files and save them in the assets folder of your application. Create new
media files to ensure the correct functionality and improve your application.
● Candidates will not get any additional time for completing the task.
● Make sure all tools available are in proper condition before starting the test.
● Candidates will receive more points for utilizing Jetpack Compose instead of Views.
● The application Package name must start with dev.dotworld
● Maintain your Figma/Adobe XD designs and Source code organized and clean to
facilitate future maintenance.
● Use correct indentation and comments. Use meaningful variable names and document
your code as much as possible so another developer will be able to modify your work in
the future.
● To export a Figma file, click on File > Save Local Copy, and a .fig file will be downloaded.
● Save your figma design file in a folder called “YourName_YourPhoneNumber/design”.
● Save application source files in a folder called “YourName_YourPhoneNumber/source”.
● Please include the APK file named tnskills_level2_eval.apk in the
"YourName_YourPhoneNumber" folder.
● Compress the YourName_YourPhoneNumber folder into .zip format.
● When using hybrid frameworks, it's crucial to adhere to the correct code syntax and
formatting standards.
 
File names:
Login Screen : login_screen.png
Main Screen : users_screen.png
Figma File : tnskills_level2_eval.fig
Application : tnskills_level2_eval.apk
ZIP : YourName_YourPhoneNumber
Application Compatibility
Your app should be designed to run on Android devices with version 8.0 (Oreo) or newer. Make
sure to consider the specific requirements and features of these platforms when developing
your application.
 
7. API Details
The following APIs provide a JSON response(s)
i) Login API
URL : https://reqres.in/api/login
Method : POST
Body : {
"email": "eve.holt@reqres.in",
"password": "test@as1234"
}
ii) Users List API
URL : https://reqres.in/api/users?page=2
Method : GET
Param : page=1 (use proper pagination and get till last page
available)
