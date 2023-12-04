package clarkson.ee408.tictactoev4;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import clarkson.ee408.tictactoev4.client.AppExecutors;
import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.socket.GamingResponse;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class MainActivity extends AppCompatActivity {
    private TicTacToe tttGame;
    private Button[][] buttons;
    private TextView status;
    private Gson gson;
    private int player = 2;
    private boolean shouldRequestMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        player = getIntent().getIntExtra("PLAYER_KEY", 1);
        tttGame = new TicTacToe(player);
        buildGuiByCode();

        shouldRequestMove = true;

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                requestMove();
                handler.postDelayed(this, 1000);
            }
        });

        updateTurnStatus();
    }

    public void requestMove() {
        if (!shouldRequestMove) return;

        Request moveRequest = new Request(Request.RequestType.REQUEST_MOVE, "");
        AtomicReference<GamingResponse> response = new AtomicReference<>(new GamingResponse());

        synchronized (response) {
            AppExecutors.getInstance().networkIO().execute(() -> {
                try {
                    response.set(SocketClient.getInstance().sendRequest(moveRequest, GamingResponse.class));
                    if (response.get() == null) return;

                    AppExecutors.getInstance().mainThread().execute(() -> {
                        GamingResponse gamingResponse = response.get();

                        // Check if the game is still active
                        if (!gamingResponse.isActive()) {
                            shouldRequestMove = false;
                            tttGame = null;

                            // Disable all buttons
                            for (int row = 0; row < buttons.length; row++) {
                                for (int col = 0; col < buttons[row].length; col++) {
                                    buttons[row][col].setEnabled(false);
                                }
                            }

                            status.setBackgroundColor(Color.RED);
                            status.setText(tttGame.result());

                            return;
                        }

                        // Continue with the move if the game is active
                        int move = gamingResponse.getMove();
                        if (move == -1) return;

                        Log.d("moveIndex", Integer.toString(move));
                        int row = move / 3; // Dividing by 3 gives the row index
                        int col = move % 3; // Modulo 3 gives the column index
                        Log.d("row col", row + ", " + col);
                        update(row, col);
                    });
                } catch (IOException ioe) {
                    Log.e("GameClient", "Error in requestMove: " + ioe.getMessage());
                    // Handle exception
                }
            });
        }
    }

    public void sendMove(int move) {
        // Convert the move to a string
        String moveData = Integer.toString(move);

        // Create a request with the move data
        Request moveRequest = new Request(Request.RequestType.SEND_MOVE, moveData);

        // Send the request to the server
        AppExecutors.getInstance().networkIO().execute(() -> {
            try {
                // Sending the move to the server
                SocketClient.getInstance().sendRequest(moveRequest, Response.class);
                // Note: Assuming you don't need a response from the server here.
            } catch (IOException e) {
                Log.e("GameClient", "IOException when sending move: " + e.getMessage());
                // Handle exception, possibly with a user notification or a retry mechanism
            }
        });

        Log.d("move sent", "move sent");
    }

    public void buildGuiByCode() {
        // Get width of the screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / TicTacToe.SIDE;

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE + 2);

        // Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        ButtonHandler bh = new ButtonHandler();

//        GridLayout.LayoutParams bParams = new GridLayout.LayoutParams();
//        bParams.width = w - 10;
//        bParams.height = w -10;
//        bParams.bottomMargin = 15;
//        bParams.rightMargin = 15;

        gridLayout.setUseDefaultMargins(true);

        for (int row = 0; row < TicTacToe.SIDE; row++) {
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setTextSize((int) (w * .2));
                buttons[row][col].setOnClickListener(bh);
                GridLayout.LayoutParams bParams = new GridLayout.LayoutParams();
//                bParams.width = w - 10;
//                bParams.height = w -40;

                bParams.topMargin = 0;
                bParams.bottomMargin = 10;
                bParams.leftMargin = 0;
                bParams.rightMargin = 10;
                bParams.width = w - 10;
                bParams.height = w - 10;
                buttons[row][col].setLayoutParams(bParams);
                gridLayout.addView(buttons[row][col]);
//                gridLayout.addView( buttons[row][col], bParams );
            }
        }

        // set up layout parameters of 4th row of gridLayout
        status = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 2);
        GridLayout.Spec columnSpec = GridLayout.spec(0, TicTacToe.SIDE);
        GridLayout.LayoutParams lpStatus
                = new GridLayout.LayoutParams(rowSpec, columnSpec);
        status.setLayoutParams(lpStatus);

        // set up status' characteristics
        status.setWidth(TicTacToe.SIDE * w);
        status.setHeight(w);
        status.setGravity(Gravity.CENTER);
        status.setBackgroundColor(Color.GREEN);
        status.setTextSize((int) (w * .15));
        status.setText(tttGame.result());

        gridLayout.addView(status);

        // Set gridLayout as the View of this Activity
        setContentView(gridLayout);
    }

    public void update(int row, int col) {

        Log.d("update", "update");
        int play = tttGame.play(row, col);
        updateTurnStatus();
        if (play == 1)
            buttons[row][col].setText("X");
        else if (play == 2)
            buttons[row][col].setText("O");
        if (tttGame.isGameOver()) {
            status.setBackgroundColor(Color.RED);
            enableButtons(false);
            status.setText(tttGame.result());
            showNewGameDialog();    // offer to play again
        }
    }

    public void updateTurnStatus() {
        if (tttGame.getTurn() == player) {
            enableButtons(true);
            status.setText(tttGame.result());
        } else {
            enableButtons(false);
            status.setText(tttGame.result());
        }
    }


    public void enableButtons(boolean enabled) {
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setEnabled(enabled);
    }

    public void resetButtons() {
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for (int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setText("");
    }

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(tttGame.result());
        alert.setMessage("Do you want to play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("Yes", playAgain);
        alert.setNegativeButton("No", playAgain);

        // Create and show the AlertDialog
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            Log.d("button clicked", "button clicked");

            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int column = 0; column < TicTacToe.SIDE; column++)
                    if (v == buttons[row][column]) {
                        int moveIndex = row * TicTacToe.SIDE + column;
                        sendMove(moveIndex);
                        update(row, column);
                    }
        }
    }

    public void abortGame() {
        // Create an ABORT_GAME request
        Request abortRequest = new Request(Request.RequestType.ABORT_GAME, "");

        // Use SocketClient to send the request
        AppExecutors.getInstance().networkIO().execute(() -> {
            try {
                Response response = SocketClient.getInstance().sendRequest(abortRequest, Response.class);

                // Handle the response on the main thread
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (response.getStatus() == Response.ResponseStatus.SUCCESS) {
                        Toast.makeText(MainActivity.this, "Game aborted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to abort the game", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioe) {
                Log.e("MainActivity", "Error in abortGame: " + ioe.getMessage());
            }
        });
    }

    public void completeGame() {
        // Create an COMPLETE_GAME request
        Request completeRequest = new Request(Request.RequestType.COMPLETE_GAME, "");

        // Use SocketClient to send the request
        AppExecutors.getInstance().networkIO().execute(() -> {
            try {
                Response response = SocketClient.getInstance().sendRequest(completeRequest, Response.class);

                // Handle the response on the main thread
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (response.getStatus() == Response.ResponseStatus.SUCCESS) {
                        Toast.makeText(MainActivity.this, "Game completed successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to complete the game", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException ioe) {
                Log.e("MainActivity", "Error in completeGame: " + ioe.getMessage());
            }
        });
    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) /* YES button */ {
                tttGame.resetGame();
                updateTurnStatus();
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText(tttGame.result());
                shouldRequestMove = true;
            } else if (id == -2) // NO button
                MainActivity.this.finish();
        }
    }
}