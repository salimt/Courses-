def decrypt_story():
    """
    Using the methods you created in this problem set,
    decrypt the story given by the function getStoryString().
    Use the functions getStoryString and loadWords to get the
    raw data you need.
    returns: string - story in plain text
    """
    story = CiphertextMessage(get_story_string())
    return story.decrypt_message()
