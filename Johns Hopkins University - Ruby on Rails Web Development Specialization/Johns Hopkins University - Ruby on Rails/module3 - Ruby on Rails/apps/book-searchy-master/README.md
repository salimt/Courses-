# Book Searching App

This is a sample app to demonstrate the concept of building a Rails application by ingesting a third-party API.

## Google Books API

The API used by this app can be found at <https://developers.google.com/books/docs/v1/using#api_params>. Before digging into the app, feel free to browse the [documentation](https://developers.google.com/books/docs/v1/using#PerformingSearch) and playing around with the API. For example, you can type the following into the browser URL address bar - <https://www.googleapis.com/books/v1/volumes?q=intitle:rails>. If the resulting JSON feels overwhelming, I recommend using the [JSONView Chrome Extension](https://chrome.google.com/webstore/detail/jsonview/chklaanhfefbnpoihckbnefhakgolnmc?hl=en). All you have to do is add this extension to the Chrome browser and enable it, and your JSON will become that much more readable.

## What information does the app display?

The app lets you search books by title and displays a list of books with their title, author, image and description. You are also able to browse some of the book content (COOL - really? REALLY!) and purchase the book from [Amazon](https://www.amazon.com) (You can learn more about how to become an Amazon associate and link to products on amazon.com [here](https://affiliate-program.amazon.com/))

## Is this thing deployed somewhere?

Sure. <https://book-search-sample.herokuapp.com/> You start out with an exciting selection of books and are able to search for books of your own. 

## How was this app created?

Basically, this is very similar to how the Coursera Courses Listing app was created in [Lecture 9](https://github.com/jhu-ep-coursera/fullstack-course1-module3/blob/master/Slides/Lecture9.pdf) and [Lecture 10](https://github.com/jhu-ep-coursera/fullstack-course1-module3/blob/master/Slides/Lecture10.pdf) of [Module 3](https://github.com/jhu-ep-coursera/fullstack-course1-module3). And, of course, you would substitute the word `books` instead of the word `courses`. So, for example,

`rails g controller courses index`

becomes

`rails g controller books index`

## And then? Can you explain which files I should look at to get a better idea how the app works?

1. [config/routes.rb](https://github.com/jhu-ep-coursera/book-searchy/blob/master/config/routes.rb) handles the routing of your app. In this case, it maps the `root` route to go to the `BooksController`.
2. [app/controllers/books_controller.rb](https://github.com/jhu-ep-coursera/book-searchy/blob/master/app/controllers/books_controller.rb) handles the request by querying the `Book` model (described next) and forwarding the results (implicitly) to `app/views/books/index.html.erb`
3. [app/models/book.rb](https://github.com/jhu-ep-coursera/book-searchy/blob/master/app/models/book.rb) uses HTTParty gem to query the Google Books API.
4. [app/views/books/index.html.erb](https://github.com/jhu-ep-coursera/book-searchy/blob/master/app/views/books/index.html.erb) display the results and contains a search form to keep searching for more books.

## Any gotchas?

Take a close look at the `Gemfile`. For example, you need to specify `gem 'pg', '~> 0.20'` since for whatever reason the typical `gem 'pg'` (the latest) is not compatible with Rails, at least not version 4.2.8. (See the [following](https://stackoverflow.com/a/48265234/908842) for more info)