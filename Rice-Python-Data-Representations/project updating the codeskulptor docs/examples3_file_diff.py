"""
Code that opens two files and compares their contents
"""

WINDOW_SIZE = 10

def compare_files(file1_name, file2_name):
    """
    Given two files (whose paths are specified as strings),
    find the first location in the files that differ and
    print a small portion of both files around this location
    """

    # open and read both files
    file1 = open(file1_name)
    file2 = open(file2_name)
    file1_text = file1.read()
    file2_text = file2.read()

    smaller_length = min(len(file1_text), len(file2_text))

    for idx in range(smaller_length):
        if file1_text[idx] != file2_text[idx]:
            start_window = max(0, idx - WINDOW_SIZE)
            end_window = min(smaller_length, idx + WINDOW_SIZE)
            print("Found difference at position", idx)
            print(file1_name, "has the characters", file1_text[start_window : end_window])
            print(file2_name, "has the characters", file2_text[start_window : end_window])
            return
        
    if len(file1_text) < len(file2_text):
        print(file1_name, "is a prefix of", file2_name)
    elif len(file2_text) < len(file1_text):
        print(file2_name, "is a prefix of", file1_name)
    else:
        print(file1_name, "and", file2_name, "are the same")

            