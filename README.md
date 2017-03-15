# Encoding_Decoding_Files

This program allows the user to encode a text file using either MonoCipher or VigenereCipher. 
The user can also decode the same text file to retrieve the original message back.

This works by first asking the user to enter a UNIQUE keyword with no repeating letters (in uppercase) and then providing
the name of the text file with the letter 'P' at the end of the filename. This is to ensure the program knows it has to encode the file.

After encoding the file using monocipher or vigenerecipher, the program outputs a text file with the same filename but with letter 'C' at
the end to let the user know that this is the encoded file.

When this encoded file is provided as the filename in the program, it checks for the ending letter, which in this case is 'C' therefore it
knows to decode the file. However, the decoded file will only output the original message if entered with the same UNIQUE keyword
which was used to encode the file. The decoded file is then outputted with the same file name with letter 'D' at the end.

When encoding files, the program also outputs a letter frequency file which displays to the user the most used letter to encode the file.
This can be found in a text file with the same file name with letter 'F' at the end.

Please Note: You have to ensure the text file is in the same directory as your project for it to detect the file.

Before running the program:
1) Create a plain text file with letter 'P' at the end of the file name e.g. mytextP.txt
2) Run the program.
3) Enter a unique keyword e.g. LION
4) Encode using either MonoCipher/VigenerCipher
5) View your encoded file named mytextC.txt
6) To decode, run the program again.
7) Enter the text file you want to decode - must end with letter 'C' e.g. mytextC.txt
8) Enter the same keyword used to encode the file e.g. LION
9) Decode the text file using the same cipher used to encode the file.
10) The decoded text file is now mytextD.txt which will show the original message.
