import a1
import unittest


class TestNumBuses(unittest.TestCase):
    """ Test class for function a1.num_buses. """

    # Add your test methods for a1.num_buses here.
    def test_num_buses_example_1(self):
        """Test num_buses with 0."""

        actual = a1.num_buses(0)
        expected = 0
        self.assertEqual(expected, actual)
        
    def test_num_buses_example_2(self):
        """Test num_buses with 1."""

        actual = a1.num_buses(1)
        expected = 1
        self.assertEqual(expected, actual)

    def test_num_buses_example_3(self):
        """Test num_buses with 50."""

        actual = a1.num_buses(50)
        expected = 1
        self.assertEqual(expected, actual)
        
    def test_num_buses_example_4(self):
        """Test num_buses with 100."""

        actual = a1.num_buses(101)
        expected = 3
        self.assertEqual(expected, actual)

    def test_num_buses_example_5(self):
        """Test num_buses with -3."""

        actual = a1.num_buses(-3)
        expected = False
        self.assertEqual(expected, actual)

if __name__ == '__main__':
    unittest.main(exit=False)
