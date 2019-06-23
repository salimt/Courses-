class BooksController < ApplicationController
  def index
    @search_term = params[:looking_for] || 'cat in the hat'
    @books = Book.about(@search_term)

    # Substitute your amazon tag here
    @amazon_tag = 'jhu-ruby-coursera-20'

    map_isbn(@books)
    sort_books(@books)
  end

  private
    def map_isbn(books)
        books.each do |book|
          book['isbn10'] = nil
          isbn10_item = book["volumeInfo"]["industryIdentifiers"].find { |it| it['type'] == 'ISBN_10' } if book["volumeInfo"]["industryIdentifiers"]
          book['isbn10'] = isbn10_item['identifier'] if isbn10_item
          book['isbn10'] = nil if book["saleInfo"]["isEbook"] || book['isbn10'].to_s.downcase.end_with?('x')
        end
    end

    def sort_books(books)
      books.sort! do |book1, book2|

        # The books that don't have an isbn10 field should go last...

        if (book1['isbn10'].nil? && book2['isbn10'].nil?) ||
           (!book1['isbn10'].nil? && !book2['isbn10'].nil?)
          0
        elsif book2['isbn10'].nil?
          -1
        elsif book1['isbn10'].nil?
          1
        end
      end
    end
end