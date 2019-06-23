class MyAlgorithm
  private
    def test1
      "Private"
    end
  protected
    def test2
      "Protected"
    end
  public
    def public_again
      "Public"
    end
end

class Another
  def test1
    "Private, as declated later on"
  end
  private :test1
end