Capybara.default_driver = :poltergeist

Capybara.app_host = "http://localhost:3000"

describe "Recipes App" do
  it "displays 'Kahlúa-Spiked' when request parameter 'search' is mocha" do
    visit "?search=mocha"
    expect(page).to have_content 'Kahlúa-Spiked'
  end

  it "utilizes the FOOD2FORK_SERVER_AND_PORT environment variable" do
    testval = text_in_dirs("./app", "FOOD2FORK_SERVER_AND_PORT")
    expect(testval).to be > 0
  end

 it "utilizes the FOOD2FORK_KEY environment variable" do
    testval = text_in_dirs("./app", "FOOD2FORK_KEY")
    expect(testval).to be > 0
  end

  context "visit root" do
    before { visit '/' }
    
    it "displays chocolate (default)" do
      expect(page).to have_content 'Chocolate'
    end

    it "displays 'Powered By Food2Fork.com'" do
      expect(page).to have_css("p", text: 'Powered By Food2Fork.com')
    end

    it "displays table element that has a row with 3 columns" do
      expect(page).to have_selector(:xpath, "//table//tr[count(td)=3]")
    end

    it "column 1 should have the thumbnail inside img tag inside a link tag" do
      expect(page).to have_selector(:xpath, "//table//tr/td[1]//a//img")
    end

    it "title should be inside a second column inside a link tag" do
      expect(page).to have_selector(:xpath, "//table//tr/td[2]//a")
    end

  end

end

def text_in_dirs(filepath, text)
  dcount = 0;
 #Dir.chdir(filepath)
  Dir["#{filepath}/**/*.rb"].each do |fname|
      dcount = dcount + text_in_file(fname, text)
    end
  return dcount
end

def text_in_file(fileName, text)
  count = 0;
  file = File.open(fileName, "r")
  file.each_line { |line|
    if line.include? text then
      count = count + 1
    end
  }
  return count
end
