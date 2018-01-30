class Song:
    """A song."""

    def __init__(self, artist, title, minutes, seconds):
        """ (Song, str, str, int, int) -> NoneType

        A Song with an artist, title, minutes, and seconds.

        >>> song = Song('Neil Young', 'Harvest Moon', 5, 3)
        >>> song.artist
        'Neil Young'
        >>> song.title
        'Harvest Moon'
        >>> song.minutes
        5
        >>> song.seconds
        3
        """

        self.title = title
        self.artist = artist
        self.minutes = minutes
        self.seconds = seconds

    def __str__(self):
        """ (Song) -> str

        Return a string representation of this song.

        >>> song = Song('Neil Young', 'Harvest Moon', 5, 3)
        >>> str(song)
        'Neil Young, Harvest Moon (5:03)'
        """

        return self.artist + ', ' + self.title + ' (' + str(self.minutes) \
            + ':' + str(self.seconds).rjust(2, '0') + ')'

if __name__ == '__main__':

    s1 = Song("Neil Young", "Harvest Moon", 5, 3)
    s2 = Song("Serena Ryder", "Stompa", 3, 15)

    print(s1)
    print(s2)
