# Restaurant Server

## What is this for?

In the previous course [HTML, CSS, and Javascript for Web Developers](https://www.coursera.org/learn/html-css-javascript-for-web-developers), since our web site did not have a feature to update the menu of the restaurant, we could get away with having a central server to request menu data for all the students in the class. However, in this course ([Single Page Web Applications with AngularJS](https://www.coursera.org/learn/single-page-web-apps-with-angularjs)), we are adding a feature for the owners of the restaurant to be able to update the menu. Since each deployed application will be able to update the data on the server, we can no longer get away with having a central one. Each student will have to set up their own server with their copy of the data to mess with.

**The setup, however, is super easy! Just closely follow these steps.**

## Prerequisites Before You Start

The _only_ prerequisite is that you have Git installed on your machine. If you don't, see the Development Environment Setup video in Module 1 of [Single Page Web Applications with AngularJS](https://www.coursera.org/learn/single-page-web-apps-with-angularjs).

You do NOT need to install Ruby on Rails on your local machine in order to deploy and use this application.

## Step 1 - Clone This Repository

Open up the Command Prompt or cmd (on Windows) or Terminal app (if you on a Mac). Navigate (use `cd some_dir`) to a directory of your choice, preferably located in your home directory. and type following `git` command which will create a subdirectory in the directory you're in now called `restaurant-server` and download the repository code into that subdirectory.

```bash
git clone https://github.com/jhu-ep-coursera/restaurant-server.git
```

## Step 2 - Sign Up With Heroku

Now, that you have the repo, we need to deploy it somewhere on the internet. We will be using Heroku. Heroku is a cloud Platform-as-a-Service (PaaS) and it's free (they of course have paid plans, but we will be using the free plan).

Go to [Heroku.com](http://www.heroku.com) and click the "Sign Up" button at the top right corner. After you sign up, the most important thing to remember is your username and password.

## Step 3 - Download and Install Heroku Command Line Interface (CLI)

Heroku Command Line Interface (CLI) is a command-line tool that will allow us to deploy our server application directly to Heroku servers. (This tool used to be called Heroku Toolbelt, so don't get confused if you see this name somewhere.)

Go to https://devcenter.heroku.com/articles/heroku-command-line and install Heroku CLI by following the instructions for the Operating System you're using.

## Step 4 - Log Into Heroku Using Heroku CLI

Run the following in your terminal (you only actually type the FIRST line below - Heroku prompts you for the rest):

```bash
heroku login
Enter your Heroku credentials.
Email: name@example.com
Password (typing will be hidden):
Authentication successful.
```

You might not get the exact text above, but it should be something very similar.

## Step 5 - Create Heroku Domain

The web application you deploy to Heroku will have the URL in the form of http://YOUR-CHOSEN-SUBDOMAIN.herokuapp.com/. Note the `YOUR-CHOSEN-SUBDOMAIN` subdomain part of the URL. In this step, we'll use the Heroku Toolbelt to allow us to choose that subdomain name. You can be the one to decide on the exact name or you can let Heroku decide for you.

**NOTE! From this step forward, make sure you are located inside the `restaurant-server` directory! If not, navigate to that directory using `cd restaurant-server`.**

Type the following command to have Heroku generate a random subdomain name for your application:

```bash
heroku create
```

Alternatively, type the name you would like Heroku to use (as your chosen subdomain name) as follows:

```bash
heroku create ychaikin-course5
Creating ⬢ ychaikin-course5... done
https://ychaikin-course5.herokuapp.com/ | https://git.heroku.com/ychaikin-course5.git
```

## Step 6 - Deploy The App Using Git

When you created your application, Heroku created an empty remote git repository for you. You can verify that this is indeed so by typing the following `git` command:

```bash
git remote -v
heroku	https://git.heroku.com/ychaikin-course5.git (fetch)
heroku	https://git.heroku.com/ychaikin-course5.git (push)
origin	https://github.com/jhu-ep-coursera/restaurant-server.git (fetch)
origin	https://github.com/jhu-ep-coursera/restaurant-server.git (push)
```

You can see that there are 2 git remotes: One is `origin` (which is the place from which you originally cloned the repository, i.e., GitHub.com), and second one called `heroku`.

Now, we need to upload this repository to Heroku through `git`. Type the following command to deploy the application (which basically just takes what you have locally and pushes/uploads it out to Heroku's repository).

```bash
git push heroku master
```

At this point Heroku is looking at the contents of the repository you pushed to it and once it realizes that it's a Ruby on Rails application (based on the directory structure and some other obvious signs), it gladly DEPLOYS the application for you somewhere in the cloud _under the domain name you chose before_. You should see a lot of `remote` logs scroll by as Heroku deploys your application.

We are not finished yet, but if you go to http://YOUR-CHOSEN-SUBDOMAIN.herokuapp.com/menu_items.json, you should at least see an "Internal Server Error" with a 500 code. The reason you would be seeing an error at this point is because the application is trying to retrieve data from a database which has not been set up as of yet.

If, after going to the URL above, you see "Heroku | No such app" - you have missed a step somewhere or are entering a wrong URL.

## Step 7 - Deploy The Database

In this step, you'll initialize the database and populate it with data.

Type the following command to create the database tables:

```bash
heroku run rake db:migrate
```

Then, type the following to populate the database tables with the initial data:

```bash
heroku run rake db:seed
```

You will see lots of text scrolling by and not all of it will be legible. That's OK. We are storing images for the restaurant menu items as base64-encoded strings, so some of the data may _appear_ garbled.

## Step 8 - Set Your Username/Password For Admin Portion Of The Restaurant Site

In order to modify data in the application, you will need to set up username/password.

Type the following command:

```bash
heroku run rails console
```

Then, when you get a prompt, type in the following:

```bash
Login.create! identification: "SOME_USERNAME", password: "SOME_PASSWORD",  password_confirmation: "SOME_PASSWORD"
```

You can copy/paste this line and then replace SOME_USERNAME and SOME_PASSWORD with whatever username/password you'd like to use when you log into the administrative portion of our restaurant web application.

After the username/password is set, type `exit` to quit the rails console.

**That's it! DONE! You are now ready to use this remote server for your copy of the restaurant web application.**

## Short Summary of the APIs

### All categories
[https://davids-restaurant.herokuapp.com/categories.json](https://davids-restaurant.herokuapp.com/categories.json)

### Menu items for a particular category
The format of the endpoint is `https://davids-restaurant.herokuapp.com/menu_items.json?category=CATETORY_NAME`.

For example, "Lunch" category, i.e., "L":
[https://davids-restaurant.herokuapp.com/menu_items.json?category=L](https://davids-restaurant.herokuapp.com/menu_items.json?category=L)

### Single menu item
The format of the endpoint is `https://www.davidchuschinabistro.com/menu_items/SHORT_NAME.json`

For example, for "L16" as the `SHORT_NAME`
[https://www.davidchuschinabistro.com/menu_items/L16.json](https://www.davidchuschinabistro.com/menu_items/L16.json)

## Want To Learn How To Make This Ruby on Rails Application Yourself?

In this class, we are not covering any server-side development. However, if you'd like to acquire the skills necessary to produce an application like this on your own, check out the first 2 courses in this specialization: [Ruby on Rails: An Introduction](https://www.coursera.org/learn/ruby-on-rails-intro) and [Rails with Active Record and Action Pack](https://www.coursera.org/learn/rails-with-active-record). Those courses are taught by [Kalman Hazins](https://www.coursera.org/instructor/kalman-hazins), who developed this restaurant-server app.
