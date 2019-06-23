## Module #3, Assignment 1: Food 2 Fork HTTP API Interface

The purpose of this assignment is to implement a source of data -- for use in an MVC Model class implementation.

The functional goal is to implement a restful-flavored API client for `http://food2fork.com/about/api` to return JSON documents containing recipe information.

### Functional Requirements

1. Implement a Ruby class that will 

    * accept a keyword search term
    * forward the keyword search term according to the `http://food2fork.com/about/api` interface definition using the HTTParty API
    * return the JSON document containing recipe information provided by `http://food2fork.com/about/api`

### Getting Started

0. Sign up for a Food 2 Fork account at `http://food2fork.com/about/api`. The free plan will give you 500 requests per day. When you sign up, you will need to use an "API Key" available on your "API Admin" page.

    * define an environment variable (FOOD2FORK_KEY) to store your API Key

1. Download and extract the starter set of boostrap files from (URL).

    ```shell
    --- student-start
      |-- .rspec (important hidden file)
      |-- chocolate_recipes.json
      |-- module3_1_assignment.rb
      |-- solution.rb
      `-- spec
          |-- recipe_spec.rb
          `-- spec_helper.rb
    ```
    
    * .rspec - configuration file for unit tests. If you move your files you must take 
      care to also copy this file.
    * module3_lesson1_assignment.rb - contains the starting example.
    Your solution must be placed within this file.
    * spec - this directory contains tests to verify your solution. You should
    not modify anything in this directory
    * chocolate_recipes.json - used for off-line unit testing by rspec tests

2. Install the following gems used by the rspec unit tests. You may have some of these already installed.
The last gem is used for testing HTTP calls without using the live `http://food2fork.com/` site.

    ```shell
    $ gem install rspec
    $ gem install rspec-its
    $ gem install webmock
    ```

3. Read thru the Food2Fork and HTTParty documentation.

    * HTTParty API document is located at `https://github.com/jnunemaker/httparty`
    * Food2Fork interface definition is located at `http://food2fork.com/about/api`

4. Implement the Ruby class in a file called `module3_1_assignment.rb`.

5. Run the rspec test(s) to receive feedback.  If you copy/move them,
be sure to include the important .rspec hidden file. All tests will
(obviously) fail until you complete the specified solution.

    ```shell
    $ rspec

    Recipe
      should respond to #for (FAILED - 1)
      Environment variable FOOD2FORK_KEY is set (FAILED - 2)
      default_params[:key] equals Environment variable FOOD2FORK_KEY (FAILED - 3)
      default_params
        example at ./spec/recipe_spec.rb:18 (FAILED - 4)
      base_uri
        example at ./spec/recipe_spec.rb:26 (FAILED - 5)
      Chocolate Search
        example at ./spec/recipe_spec.rb:39 (FAILED - 6)
        size
          example at ./spec/recipe_spec.rb:42 (FAILED - 7)
        sample
          example at ./spec/recipe_spec.rb:45 (FAILED - 8)
        sample
          example at ./spec/recipe_spec.rb:46 (FAILED - 9)
        sample
          example at ./spec/recipe_spec.rb:47 (FAILED - 10)
        sample
          example at ./spec/recipe_spec.rb:48 (FAILED - 11)

    Failures:

      1) Recipe should respond to #for
         Failure/Error: it { is_expected.to respond_to(:for) }
           expected Recipe to respond to :for
         # ./spec/recipe_spec.rb:11:in `block (2 levels) in <top (required)>'

      2) Recipe Environment variable FOOD2FORK_KEY is set
         Failure/Error: expect(ENV["FOOD2FORK_KEY"]).to_not be_nil
           expected: not nil
                got: nil
         # ./spec/recipe_spec.rb:14:in `block (2 levels) in <top (required)>'

      3) Recipe default_params[:key] equals Environment variable FOOD2FORK_KEY
         Failure/Error: expect(subject.default_params[:key]).to eq ENV["FOOD2FORK_KEY"]
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:22:in `block (2 levels) in <top (required)>'

      4) Recipe default_params
         Failure/Error: its(:default_params) { is_expected.to include :key }
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:18:in `block (2 levels) in <top (required)>'

      5) Recipe base_uri
         Failure/Error: its(:base_uri) { is_expected.to include "http://food2fork.com/api" }
         NoMethodError:
           undefined method `base_uri' for Recipe:Class
         # ./spec/recipe_spec.rb:26:in `block (2 levels) in <top (required)>'

      6) Recipe Chocolate Search
         Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

      7) Recipe Chocolate Search size
         Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

      8) Recipe Chocolate Search sample
         Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

      9) Recipe Chocolate Search sample
         Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
         NoMethodError:
           undefined method `default_params' for Recipe:Class
         # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

      10) Recipe Chocolate Search sample
          Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
          NoMethodError:
            undefined method `default_params' for Recipe:Class
          # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

      11) Recipe Chocolate Search sample
          Failure/Error: query = Recipe.default_params.merge({"q" => "chocolate"})
          NoMethodError:
            undefined method `default_params' for Recipe:Class
          # ./spec/recipe_spec.rb:31:in `block (3 levels) in <top (required)>'

    Finished in 0.01817 seconds (files took 0.39671 seconds to load)
    11 examples, 11 failures

    Failed examples:

    rspec ./spec/recipe_spec.rb:11 # Recipe should respond to #for
    rspec ./spec/recipe_spec.rb:13 # Recipe Environment variable FOOD2FORK_KEY is set
    rspec ./spec/recipe_spec.rb:21 # Recipe default_params[:key] equals Environment variable FOOD2FORK_KEY
    rspec ./spec/recipe_spec.rb:18 # Recipe default_params
    rspec ./spec/recipe_spec.rb:26 # Recipe base_uri
    rspec ./spec/recipe_spec.rb:39 # Recipe Chocolate Search
    rspec ./spec/recipe_spec.rb:42 # Recipe Chocolate Search size
    rspec ./spec/recipe_spec.rb:45 # Recipe Chocolate Search sample
    rspec ./spec/recipe_spec.rb:46 # Recipe Chocolate Search sample
    rspec ./spec/recipe_spec.rb:47 # Recipe Chocolate Search sample
    rspec ./spec/recipe_spec.rb:48 # Recipe Chocolate Search sample
    ```

6. Run the `solution.rb` Ruby script to execute a sample call.

    ```ruby
    require_relative "module3_1_assignment"

    puts Recipe.for("chocolate")
    ```

### Technical Requirements

1. Define an environment variable called FOOD2FORK_KEY that stores the value of your API Key from the `food2fork.com` admin page when you signed up for an account.

2. Implement a `Recipe` class that will implement the HTTP API to `http://food2fork.com/about/api`.
The unit tests will expect a class by that exact name.

3. The `Recipe` class should

    * be implemented in a file called `module3_1_assignment.rb`. The unit tests will expect a file by that name.
    * import the HTTParty mixin
    * define a base_uri to use `http://food2fork.com/api`
    * define a default query param of `key` for all HTTP GET
    requests whose value is equal to value of the environment variable FOOD2FORK_KEY.
    * specify the desired format as `json`
    * specify all the above using legal Ruby syntax

4. The `Recipe` class must have a `for` class method that 

    * accepts a keyword for a search term
    * issues an HTTP GET request using the HTTParty gem
    * the HTTP GET request must have the "q=keyword" query argument and query "/search" route
    * returns the JSON payload document supplied in the `recipes` element of the hash returned by HTTParty

### Self Grading/Feedback

Some unit tests have been provided in the bootstrap files that can be
used to evaluate your solution. They must be run from the same directory
as your solution.

```shell
$ rspec

Recipe
  should respond to #for
  Environment variable FOOD2FORK_KEY is set
  default_params[:key] equals Environment variable FOOD2FORK_KEY
  default_params
    should include :key
  base_uri
    should include "http://food2fork.com/api"
  Chocolate Search
    should be a kind of Array
    size
      should eq 30
    sample
      should have key "title"
    sample
      should have key "f2f_url"
    sample
      should have key "social_rank"
    sample
      should have key "image_url"

Finished in 0.02369 seconds (files took 0.40009 seconds to load)
```

A client script (`solution.rb`) is also provided in the bootstrap
and can be used to issue a sample client request.


```ruby
$ ruby solution.rb 
{"publisher"=>"BBC Good Food", "f2f_url"=>"http://food2fork.com/view/9089e3", 
"title"=>"Cookie Monster cupcakes",
"source_url"=>"http://www.bbcgoodfood.com/recipes/873655/cookie-monster-cupcakes", 
"recipe_id"=>"9089e3", "image_url"=>"http://static.food2fork.com/604133_mediumd392.jpg",
"social_rank"=>100.0, "publisher_url"=>"http://www.bbcgoodfood.com"}
...
{"publisher"=>"Elana's Pantry", "f2f_url"=>"http://food2fork.com/view/22d607", 
"title"=>"Brownies", 
"source_url"=>"http://www.elanaspantry.com/brownies/", 
"recipe_id"=>"22d607", "image_url"=>"http://static.food2fork.com/dsc_8204brownies7a41.jpg", 
"social_rank"=>99.99999999990415, "publisher_url"=>"http://www.elanaspantry.com"}
```

### Submission

There is no submission required for this assignment but the 
implementation will be part of a follow-on assignment so 
please complete this to the requirements of the unit test.

Your final directory contents should look as follows:

```shell
|-- module3_1_assignment.rb
|-- chocolate_recipes.json
|-- solution.rb
|-- .rspec (important hidden file)
`-- spec
    |-- recipe_spec.rb
    `-- spec_helper.rb
```

#### Updated: 2015-09-22
