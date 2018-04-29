# Shop-O-Holic

Shop-O-Holic is an e-commerce android application which is designed and developed by Vyom Patel as a self starter project. 

## Getting Started

In order to clone this app you can go to https://github.com/vyomhpatel/ShopOHolic.git

### Prerequisites
In order to understand the concepts used in the project user should be familiar with below mentioned technologies.
1) Android Material Design
2) Animations
3) Braintree Payment Gateway Testing
4) Crashlytics
5) EventBus
6) Firebase Cloud Messaging
7) MVC Architecture
8) SQLite Database
9) Picasso
10) Volley

## Project Details

This app is end result of combination of technologies like android material design, braintree payment, firebase cloud messaging, sqlite database and volley library using MVC application architecture.
Features:
1) Cloud message notifications using firebase to provide coupons to
users
2) android material design components like recycler view to provide look
and feel to the app
3) braintree payment gateway to test the payment mechanism using
credit card as well paypal
4) Restful data management using volley library to consume JSON data
fetched from the server and make the view react based on the data
consumed and manipulate data based on used inputs.
5) different kinds of views like card views, recycler view, list views to
display products in various formats
6) sqlite database to maintain cart status for particular user
7) app has been specifically designed for logged in users.
8) for testing the app crashlytics has been used in order to maintain the
bug reports after user installs the app.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Git](https://git-scm.com) - Version Control
* [Gradle](https://gradle.org) - Build tool


## Libraries
* [Volley](compile 'com.android.volley:volley:1.1.0') - Network Calls
* [Braintree](implementation 'com.braintreepayments.api:braintree:2.+') - Payment Gateway Testing
             (implementation 'com.braintreepayments.api:drop-in:3.+')
* [Picasso](api 'com.squareup.picasso:picasso:2.5.0') - Image Loading from URL
* [Lottie](api 'com.airbnb.android:lottie:2.5.1') - Custom Animation
* [Eventbus](api 'org.greenrobot:eventbus:3.0.0') - Data passing between activities and Fragments
* [Awesome Validations](api 'com.basgeekball:awesome-validation:1.3') - Validation Error styling
* [Firebase](api 'com.google.firebase:firebase-messaging:15.0.0') - Cloud Messaging
* [Crashlytics]( api('com.crashlytics.sdk.android:crashlytics:2.9.1@aar')) - App Crashes reporting


## Authors

* **VYOM PATEL** - *Initial work* - [Vyom Patel](https://github.com/vyomhpatel)

