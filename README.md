1. Provide evidence that you have followed the “Test Procedure”. Evidence can
be screenshots or a recorded video. If you prefer a recorded video, you are
advised to upload the video to your Google Drive and include the public link
in your report. Ensure it is accessible via the link until noon on Dec. 12 when
grading is completed.
If your app is not working as expected, explain your progress and your
challenges in each test case.


2. Explain why we must send a REQUEST_MOVE request even if it is the
current user’s turn to move.

The request move method is responsible for sending a REQUEST_MOVE request to the server even if it it’s the current user’s turn. This is the case in order to ensure synchronization between the client and server. By constantly sending move requests at known intervals, the client stays updated with latest game state.

3. If two users A and B are in the middle of a game, what happens when user
B's device suddenly goes off (i.e., shuts down); either it crashes or the battery
drains? Explain what will happen, from how the server will know user B is
offline to how user A will know the game is inactive.
You can simulate device shutdown in the emulator by Stopping it in “Device
Manager”

When B’s device goes offline the server, detects the absence of responses and recognizes B is offline. User A periodically updates PairingActivity. If B declined an invitation or went offline the UI adjusts and displays an updated list of available users. The PairingActivity handles its lifecycle appropriately, setting shouldUpdatePairing to false when paused or destroyed. Upon destruction, the app logs out user A by closing the SocketClient. This comprehensive approach ensures effective communication between the client and server, providing a robust user experience even in scenarios of unexpected device shutdown or disconnection.

4. Can the grid in the MainActivity class (i.e., the TicTacToe board) be designed
using XML layout instead of by code? Support your answer.

Yes. The MainActivity class can be designed using XML layout instead of by code. Android allows for this to be achieved fairly easily via the use of XML layout system. To achieve this, you must define GridLayout, buttons, and other UI elements  in the xml file, specifying the width, height, and margins. In onCreate method you need to add setContentView(R.layout.your_xml_layout) to use the XML layout. 
