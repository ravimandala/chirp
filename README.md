# Project 4: **Chirp v2.0**
Mandala's own version of Twitter Client version 2.0


**Chirp** is an android app that allows a user to view home and mentions timelines, view user profiles with user timelines, as well as compose and post a new tweet.
The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **2** hours spent in total

Here's a walkthrough of implemented user stories:

![Video Walkthrough of implemented user stories: ](app/src/main/res/drawable-hdpi/app_demo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Week#4 User Stories

The following **required** functionality is completed:

* [x] The app includes **all required user stories** from Week 3 Twitter Client
* [ ] User can **switch between Timeline and Mention views using tabs**
  * [ ] User can view their home timeline tweets.
  * [ ] User can view the recent mentions of their username.
* [ ] User can navigate to **view their own profile**
  * [ ] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [ ] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [ ] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [ ] Profile view includes that user's timeline
* [ ] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom

The following **optional** features are implemented:

* [ ] User can view following / followers list through the profile
* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **"reply" to any tweet on their home timeline**
  * [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] Improve the user interface and theme the app to feel twitter branded
* [ ] User can **search for tweets matching a particular query** and see results

The following **bonus** features are implemented:

* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] User can view their direct messages (or send new ones)


## Week#3 User Stories

The following **required** functionality is completed:

* [x]	User can **sign in to Twitter** using OAuth login
* [x]	User can **view tweets from their home timeline**
  * [x] User is displayed the username, name, and body for each tweet
  * [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
  * [x] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [x] User can **compose and post a new tweet**
  * [x] User can click a “Compose” icon in the Action Bar on the top right
  * [x] User can then enter a new tweet and post this to twitter
  * [x] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [x] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [x] User can **pull down to refresh tweets timeline**
* [ ] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [ ] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in offline mode.
* [ ] User can tap a tweet to **open a detailed tweet view**
* [ ] User can **select "reply" from detail view to respond to a tweet**
* [ ] Improve the user interface and theme the app to feel "twitter branded"

The following **bonus** features are implemented:

* [x] [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Move the "Compose" action to a [FloatingActionButton](https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons) instead of on the AppBar.
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [ ] User can see embedded image media within the tweet detail view
* [ ] User can watch embedded video within the tweet
* [ ] Compose tweet functionality is build using modal overlay

The following **additional** features are implemented:

* [ ] Excellent error handling
* [ ] Unit tests

## Notes

All izzz well!!!

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide/) - Image loading and caching library recommended for Android

## License

    Copyright [2016] Ravi Kumar Mandala

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
