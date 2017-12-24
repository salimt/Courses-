import timeZonesGui

def seconds_difference(time_1, time_2):
    """ (number, number) -> number

    Return the number of seconds later that a time in seconds
    time_2 is than a time in seconds time_1.
        
    >>> seconds_difference(1800.0, 3600.0)
    1800.0
    >>> seconds_difference(3600.0, 1800.0)
    -1800.0
    >>> seconds_difference(1800.0, 2160.0)
    360.0
    >>> seconds_difference(1800.0, 1800.0)
    0.0
    """
    return float(time_2) - float(time_1)
#print(seconds_difference(1800.0, 2160.0))
    


def hours_difference(time_1, time_2):
    """ (number, number) -> float

    Return the number of hours later that a time in seconds
    time_2 is than a time in seconds time_1.
        
    >>> hours_difference(1800.0, 3600.0)
    0.5
    >>> hours_difference(3600.0, 1800.0)
    -0.5
    >>> hours_difference(1800.0, 2160.0)
    0.1
    >>> hours_difference(1800.0, 1800.0)
    0.0
    """
    return seconds_difference(time_1, time_2) / 3600
#print(hours_difference(1800.0, 2160.0), hours_difference(3600.0, 1800.0))



def to_float_hours(hours, minutes, seconds):
    """ (int, int, int) -> float

    Return the total number of hours in the specified number
    of hours, minutes, and seconds.

    Precondition: 0 <= minutes < 60  and  0 <= seconds < 60

    >>> to_float_hours(0, 15, 0)
    0.25
    >>> to_float_hours(2, 45, 9)
    2.7525
    >>> to_float_hours(1, 0, 36)
    1.01
    """
    return hours + (minutes / 60) + (seconds / 3600)



def to_24_hour_clock(hours):
    """ (number) -> number

    hours is a number of hours since midnight. Return the
    hour as seen on a 24-hour clock.

    Precondition: hours >= 0

    >>> to_24_hour_clock(24)
    0
    >>> to_24_hour_clock(48)
    0
    >>> to_24_hour_clock(25)
    1
    >>> to_24_hour_clock(4)
    4
    >>> to_24_hour_clock(28.5)
    4.5
    """

    return hours % 24



### Write your get_hours function definition here:
def get_hours(hours):
    """ (number) -> number

    Return hours left as after converting it into hh:mm:ss format

    Precondition: hours >= 0

    >>> get_hours(3800)
    1
    """
    return hours // 3600
#print(get_hours(3800))

### Write your get_minutes function definition here:
def get_minutes(minutes):
    """ (number) -> number

    Return minutes left as after converting it into hh:mm:ss format

    Precondition: minutes >= 0

    >>> get_minutes(3800)
    3
    """
    return (minutes // 60) % 60  
#print(get_minutes(3800))

### Write your get_seconds function definition here:
def get_seconds(seconds):
    """ (number) -> number

    Return seconds left as after converting it into hh:mm:ss format

    Precondition: seconds >= 0

    >>> get_seconds(3800)
    20
    """
    return seconds - get_hours(seconds)*3600 - get_minutes(seconds)*60
#print(get_seconds(3800))


def time_to_utc(utc_offset, time):
    """ (number, float) -> float

    Return time at UTC+0, where utc_offset is the number of hours away from
    UTC+0.

    >>> time_to_utc(+0, 12.0)
    12.0
    >>> time_to_utc(+1, 12.0)
    11.0
    >>> time_to_utc(-1, 12.0)
    13.0
    >>> time_to_utc(-11, 18.0)
    5.0
    >>> time_to_utc(-1, 0.0)
    1.0
    >>> time_to_utc(-1, 23.0)
    0.0
    """
    return (time - utc_offset) % 24
#print(time_to_utc(-11, 18.0), time_to_utc(+1, 12.0))


def time_from_utc(utc_offset, time):
    """ (number, float) -> float

    Return UTC time in time zone utc_offset.

    >>> time_from_utc(+0, 12.0)
    12.0
    >>> time_from_utc(+1, 12.0)
    13.0
    >>> time_from_utc(-1, 12.0)
    11.0
    >>> time_from_utc(+6, 6.0)
    12.0
    >>> time_from_utc(-7, 6.0)
    23.0
    >>> time_from_utc(-1, 0.0)
    23.0
    >>> time_from_utc(-1, 23.0)
    22.0
    >>> time_from_utc(+1, 23.0)
    0.0
    """
    return (time + utc_offset) % 24
#print(time_from_utc(+6, 6.0), time_from_utc(-7, 6.0))


