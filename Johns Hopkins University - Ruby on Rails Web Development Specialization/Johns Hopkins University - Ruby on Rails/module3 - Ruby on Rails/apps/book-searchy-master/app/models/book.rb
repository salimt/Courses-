class Book
  include HTTParty

  default_options.update(verify: false) # Turn off SSL verification
  base_uri 'https://www.googleapis.com/books/v1/volumes'

  format :json

  def self.about term
    # replace 1 or more spaces in the term with a plus
    cleaned_up_term = term.gsub /\s+/, '+'

    get("", query: { q: "intitle:#{cleaned_up_term}"})["items"] || []
  end
end