import a1
import unittest


class TestStockPriceSummary(unittest.TestCase):
    """ Test class for function a1.stock_price_summary. """

    # Add your test methods for a1.stock_price_summary here.
    def test_stock_price_example_1(self):
        """Test stock_price with []."""

        actual = a1.stock_price_summary([])
        expected = (0, 0)
        self.assertEqual(expected, actual)
        
    def test_stock_price_example_2(self):
        """Test stock_price with [0.01]."""

        actual = a1.stock_price_summary([0.01])
        expected = (0.01, 0)
        self.assertEqual(expected, actual)

    def test_stock_price_example_3(self):
        """Test stock_price with [-0.01]."""

        actual = a1.stock_price_summary([-0.01])
        expected = (0, -0.01)
        self.assertEqual(expected, actual)
        
    def test_stock_price_example_4(self):
        """Test stock_price with [0.01, -0.01]."""

        actual = a1.stock_price_summary([0.01, -0.01])
        expected = (0.01, -0.01)
        self.assertEqual(expected, actual)
        
    def test_stock_price_example_5(self):
        """Test stock_price with [0.01, 0.03, 0.02, 0.14, 0, 0, 0.10, 0.01]."""

        actual = a1.stock_price_summary([0.01, 0.03, 0.02, 0.14, 0, 0, 0.10, 0.01])
        expected = (0.31, 0)
        self.assertEqual(expected, actual)
        
    def test_stock_price_example_6(self):
        """Test stock_price with [-0.01, -0.03, -0.02, -0.14, 0, 0, -0.10, -0.01]."""

        actual = a1.stock_price_summary([-0.01, -0.03, -0.02, -0.14, 0, 0, -0.10, -0.01])
        expected = (0, -0.31)
        self.assertEqual(expected, actual)
        
    def test_stock_price_example_7(self):
        """Test stock_price with [10, 0.01, 0.03, -0.02, -0.14, 0, 0, 0.10, -0.01, 12]."""

        actual = a1.stock_price_summary([10, 0.01, 0.03, -0.02, -0.14, 0, 0, 0.10, -0.01, -12])
        expected = (10.14, -12.17)
        self.assertEqual(expected, actual)


if __name__ == '__main__':
    unittest.main(exit=False)
