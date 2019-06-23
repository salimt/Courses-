# Random Countries Info App

This is a sample app to demonstrate the concept of building a Rails application by ingesting a third-party API.

## Countries API

The API used by this app can be found at <http://restcountries.eu/#api-endpoints-all>. The specific endpoint that the app consumes from is <https://restcountries.eu/rest/v2/all>. Before digging into the app, try hitting the endpoint (type the endpoint address into a browser URL bar). If the resulting JSON feels overwhelming, I recommend using the [JSONView Chrome Extension](https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc?hl=en). All you have to do is add this extension to the Chrome browser and enable it, and your JSON will become that much more manageable.

## What information does the app display?

The app displays the following information about a random list of countries - country flag, capital, population numbers, languages spoken, which countries are on the border and Lat/Long. (There is more information that can be extracted out from the API (see the section above), but this is what I decided to extract for this example.)

## Is this thing deployed somewhere?

Sure. <https://random-countries-info.herokuapp.com/> Every time you refresh the URL a different random list of countries will be displayed. If you would like to control how many random countries will be displayed, you can pass in a `limit` parameter, for example <https://random-countries-info.herokuapp.com/?limit=5>


## How was this app created?

Basically, this is very similar to how the Coursera Courses Listing app was created in [Lecture 9](https://github.com/jhu-ep-coursera/fullstack-course1-module3/blob/master/Slides/Lecture9.pdf) and [Lecture 10](https://github.com/jhu-ep-coursera/fullstack-course1-module3/blob/master/Slides/Lecture10.pdf) of [Module 3](https://github.com/jhu-ep-coursera/fullstack-course1-module3). And, of course, you would substitute the word `countries` instead of the word `courses`. So, for example,

`rails g controller courses index`

becomes

`rails g controller countries index`

## Any gotchas?

Take a close look at the `Gemfile`. For example, you need to specify `gem 'pg', '~> 0.20'` since for whatever reason the typical `gem 'pg'` (the latest) is not compatible with Rails, at least not version 4.2.8. (See the [following](https://stackoverflow.com/a/48265234/908842) for more info)






