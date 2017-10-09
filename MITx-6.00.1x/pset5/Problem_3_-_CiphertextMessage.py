class CiphertextMessage(Message):
    def __init__(self, text):
        '''
        Initializes a CiphertextMessage object
                
        text (string): the message's text

        a CiphertextMessage object has two attributes:
            self.message_text (string, determined by input text)
            self.valid_words (list, determined using helper function load_words)
        '''
        self.message_text = text
        self.valid_words= (load_words(WORDLIST_FILENAME))

    def decrypt_message(self):
        '''
        Decrypt self.message_text by trying every possible shift value
        and find the "best" one. We will define "best" as the shift that
        creates the maximum number of real words when we use apply_shift(shift)
        on the message text. If s is the original shift value used to encrypt
        the message, then we would expect 26 - s to be the best shift value 
        for decrypting it.

        Note: if multiple shifts are  equally good such that they all create 
        the maximum number of you may choose any of those shifts (and their
        corresponding decrypted messages) to return

        Returns: a tuple of the best shift value used to decrypt the message
        and the decrypted message text using that shift value
        '''
#        pass #delete this line and replace with your code here
        text = self.message_text.split(' ')

        shift_value = 0
        for shifter in range(26):
            for i in list(super(CiphertextMessage, self).apply_shift(shifter).split(' ')):            
                if is_word(self.valid_words, i):
                        shift_value = shifter
                        listo = super(CiphertextMessage, self).apply_shift(shift_value)
        return (shift_value, listo)
