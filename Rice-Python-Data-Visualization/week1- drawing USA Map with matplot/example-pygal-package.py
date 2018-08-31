"""
Simple example code for pygal, use to test installation
From http://www.pygal.org/en/latest/index.html
"""


import pygal

# Saves to SVG file - http://www.pygal.org/en/stable/documentation/output.html#file
pygal.Bar()(1, 3, 3, 7)(1, 6, 6, 4).render_to_file("pygal_test.svg")

# Render in browser - needs packages lxml installed - http://www.pygal.org/en/stable/documentation/output.html#browser
#pygal.Bar()(1, 3, 3, 7)(1, 6, 6, 4).render_in_browser()
