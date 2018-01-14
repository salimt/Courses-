from tkinter.filedialog import askopenfile
import a3


def print_board(board):
    """ (list of str) -> NoneType

    Display the contents of board.
    """

    for row_idx in range(len(board)):
        print(a3.make_str_from_row(board, row_idx))


def get_players_list():
    """() -> list of [str, int] list

    Prompt the player(s) to enter their names and return a list of player info
    as a two-item list with name and score, respectively.
    """

    players = []
    player = input('Enter player 1 name: ')
    while player.strip() or not players:
        player = player.strip()

        if player in players:
            print("A player by that name is already playing.")

        if player:
            players.append([player, 0])

        if players:
            print('Leave a blank player name to begin playing.')
        player = input('Enter player {num} name: '.format(num=len(players) + 1))

    return players


def play_game(players, board, words):
    """ (list of [str, int] list, list of list of str, list of str) -> NoneType

    Play the game with players, board and words.
    """
    num_remaining = a3.num_words_on_board(board, words) - len(found_words)
    player_num = 0
    while num_remaining > 0:
        print_headers(players, board, found_words, num_remaining)

        guess = input("[{player}] Enter a word (or blank to pass): ".format(
            player=players[player_num % len(players)][0]))

        guess = guess.strip().upper()
        if a3.is_valid_word(words, guess) and a3.board_contains_word(board, guess) and \
            not guess in found_words:
            a3.update_score(players[player_num % len(players)], guess)
            found_words.append(guess)

        num_remaining = a3.num_words_on_board(board, words) - len(found_words)
        player_num += 1

    print("Game over!\n")


def print_headers(players, board, found_words, num_remaining):
    """ (list of [str, int] list, list of list of str, list of str, int) -> NoneType

    Play the score, board, and some other details.
    """

    print_score(players)
    print_board(board)
    print('Words remaining: {num} words left.'.format(num=num_remaining))
    print('Words found: ' + (' '.join(found_words) or 'No words found, yet.'))


def print_score(players):
    """ (list of [str, int] list) -> NoneType

    Print the scores for each of the players.
    """
    for name, score in players:
        print('  ' + str(score).rjust(3) + '\t' + name)


# Load the words list.
words_file = askopenfile(mode='r', title='Select word list file')
words = a3.read_words(words_file)
words_file.close()

# Load the board.
board_file = askopenfile(mode='r', title='Select board file')
board = a3.read_board(board_file)
board_file.close()

found_words = []

players = get_players_list()

play_game(players, board, words)
print_score(players)
