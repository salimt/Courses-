import a1
import unittest


class TestSwapK(unittest.TestCase):
    """ Test class for function a1.swap_k. """

    # Add your test methods for a1.swap_k here.
    def test_swap_k_example_1(self):
        """Test nswap_k with [] and 2."""

        actual = a1.swap_k([], 2)
        expected = []
        self.assertEqual(expected, actual)
        
    def test_swap_k_example_2(self):
        """Test nswap_k with [1] and 5."""

        actual = a1.swap_k([1], 5)
        expected = [1]
        self.assertEqual(expected, actual)
        
    def test_swap_k_example_3(self):
        """Test nswap_k with [1,2] and 1."""

        actual = a1.swap_k([1,2] ,1)
        expected = [2,1]
        self.assertEqual(expected, actual)

    def test_swap_k_example_4(self):
        """Test nswap_k with [1,2], 2."""

        actual = a1.swap_k([1,2], 2)
        expected = [1,2]
        self.assertEqual(expected, actual)
       
    def test_swap_k_example_5(self):
        """Test nswap_k with [1,2,3,4,5,6,7,8,9] and 4."""

        actual = a1.swap_k([1,2,3,4,5,6,7,8,9], 4)
        expected = [6,7,8,9,5,1,2,3,4]
        self.assertEqual(expected, actual)

    def test_swap_k_example_6(self):
        """Test nswap_k with [1,2,3,4,5,6,7,8,9] and 9."""

        actual = a1.swap_k([1,2,3,4,5,6,7,8,9], 9)
        expected = [1,2,3,4,5,6,7,8,9]
        self.assertEqual(expected, actual)

if __name__ == '__main__':
    unittest.main(exit=False)
