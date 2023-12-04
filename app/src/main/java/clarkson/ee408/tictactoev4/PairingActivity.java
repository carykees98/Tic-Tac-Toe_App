package clarkson.ee408.tictactoev4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.net.Socket;
import java.util.List;

import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.model.Event;
import clarkson.ee408.tictactoev4.model.User;
import clarkson.ee408.tictactoev4.socket.PairingResponse;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class PairingActivity extends AppCompatActivity {

    private final String TAG = "PAIRING";

    private Gson gson;

    private TextView noAvailableUsersText;
    private RecyclerView recyclerView;
    private AvailableUsersAdapter adapter;

    private Handler handler;
    private Runnable refresh;

    private boolean shouldUpdatePairing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        Log.e(TAG, "App is now created");
        // TODO: setup Gson with null serialization option
        gson = new Gson();

        //Setting the username text
        TextView usernameText = findViewById(R.id.text_username);
        // TODO: set the usernameText to the username passed from LoginActivity (i.e from Intent)
        usernameText.setText(getIntent().getStringExtra("username"));
        //Getting UI Elements
        noAvailableUsersText = findViewById(R.id.text_no_available_users);
        recyclerView = findViewById(R.id.recycler_view_available_users);

        //Setting up recycler view adapter
        adapter = new AvailableUsersAdapter(this, this::sendGameInvitation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateAvailableUsers(null);

        handler = new Handler();
        refresh = () -> {
            // TODO: call getPairingUpdate if shouldUpdatePairing is true
            if(shouldUpdatePairing) {
                getPairingUpdate();
            }
            handler.postDelayed(refresh, 1000);
        };
        handler.post(refresh);
    }

    /**
     * Send UPDATE_PAIRING request to the server
     */
    private void getPairingUpdate() {
        // TODO:  Send an UPDATE_PAIRING request to the server. If SUCCESS call handlePairingUpdate(). Else, Toast the error
        SocketClient.getInstance().sendRequest(new Request(Request.RequestType.UPDATE_PAIRING), response -> {
            if (response.getType() == Response.ResponseStatus.PAIRING_RESPONSE) {
                PairingResponse pairingResponse = gson.fromJson(response.getData(), PairingResponse.class);
                handlePairingUpdate(pairingResponse);
            } else {
                showToast("Error updating pairing");
            }
        });
    }

    /**
     * Handle the PairingResponse received form the server
     * @param response PairingResponse from the server
     */
    private void handlePairingUpdate(PairingResponse response) {
        // TODO: handle availableUsers by calling updateAvailableUsers()
        updateAvailableUsers(response.getAvailableUsers());
        // TODO: handle invitationResponse. First by sending acknowledgement calling sendAcknowledgement()
        if(response.getInvitationResponse() != null) {

            // --TODO: If the invitationResponse is ACCEPTED, Toast an accept message and call beginGame
            if (response.getInvitationResponse().getResponse() == Event.InvitationResponse.ACCEPTED) {
                showToast("Invitation accepted!");
                beginGame(response.getInvitation(), response.getPlayer());
            }
            // --TODO: If the invitationResponse is DECLINED, Toast a decline message
            else if (response.getInvitationResponse().getResponse() == Event.InvitationResponse.DECLINED) {
                showToast("Invitation declined!");
            }
        }
            // TODO: handle invitation by calling createRespondAlertDialog()
         if (response.getInvitation() != null){
             createRespondAlertDialog(response.getInvitation());
        }
    }

    /**
     * Updates the list of available users
     * @param availableUsers list of users that are available for pairing
     */
    public void updateAvailableUsers(List<User> availableUsers) {
        adapter.setUsers(availableUsers);
        if (adapter.getItemCount() <= 0) {
            // TODO show noAvailableUsersText and hide recyclerView
            noAvailableUsersText.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            // TODO hide noAvailableUsersText and show recyclerView
            noAvailableUsersText.setVisibility(TextView.GONE);
            recyclerView.setVisibility(TextView.VISIBLE);
        }
    }

    /**
     * Sends game invitation to an
     * @param userOpponent the User to send invitation to
     */
    private void sendGameInvitation(User userOpponent) {
        // TODO:  Send an SEND_INVITATION request to the server. If SUCCESS Toast a success message. Else, Toast the error
        Event invitation = new Event(Event.EventStatus.SEND_INVITATION, userOpponent.getUsername());
        SocketClient.getInstance().sendRequest(new Request(Request.EventStatus.SEND_INVITATION, gson.toJson(invitation)), response -> {
            if (response.getType() == Response.ResponseStatus.SUCCESS) {
                showToast("Invitation sent successfully!");
            } else {
                showToast("Error sending invitation");
            }
        });
    }

    /**
     * Sends an ACKNOWLEDGE_RESPONSE request to the server
     * Tell server i have received accept or declined response from my opponent
      */
    private void sendAcknowledgement(Event invitationResponse) {
        // TODO:  Send an ACKNOWLEDGE_RESPONSE request to the server.
        Event acknowledge = new Event(Event.EventStatus.ACKNOWLEDGE_RESPONSE, invitationResponse.getSender());
        SocketClient.getInstance().sendRequest(new Request(Request.RequestType.ACKNOWLEDGE_RESPONSE, gson.toJson(acknowledge)), response -> {
            if (response.getType() == Response.ResponseStatus.SUCCESS) {
                shouldUpdatePairing = true;
            }
        });
    }

    /**
     * Create a dialog showing incoming invitation
     * @param invitation the Event of an invitation
     */
    private void createRespondAlertDialog(Event invitation) {
        // TODO: set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Game Invitation");
        builder.setMessage(invitation.getSender() + " has Requested to Play with You");
        builder.setPositiveButton("Accept", (dialogInterface, i) -> acceptInvitation(invitation));
        builder.setNegativeButton("Decline", (dialogInterface, i) -> declineInvitation(invitation));
        builder.show();
    }

    /**
     * Sends an ACCEPT_INVITATION to the server
     * @param invitation the Event invitation to accept
     */
    private void acceptInvitation(Event invitation) {
        // TODO:  Send an ACCEPT_INVITATION request to the server. If SUCCESS beginGame() as player 2. Else, Toast the error
        Event acceptInvitation = new Event(Event.EventStatus.ACCEPT_INVITATION, invitation.getSender());
        SocketClient.getInstance().sendRequest(new Request(Request.RequestType.ACCEPT_INVITATION, gson.toJson(acceptInvitation)), response -> {
            if (response.getType() == Response.ResponseStatus.SUCCESS) {
                showToast("Invitation accepted!");
                beginGame(invitation, 2); // Assuming player 2
            } else {
                showToast("Error accepting invitation");
            }
        });
    }

    /**
     * Sends an DECLINE_INVITATION to the server
     * @param invitation the Event invitation to decline
     */
    private void declineInvitation(Event invitation) {
        // TODO:  Send a DECLINE_INVITATION request to the server. If SUCCESS response, Toast a message, else, Toast the error
        Event declineInvitation = new Event(Event.EventStatus.DECLINE_INVITATION, invitation.getSender());
        SocketClient.getInstance().sendRequest(new Request(Request.RequestType.DECLINE_INVITATION, gson.toJson(declineInvitation)), response -> {
            if (response.getType() == Response.ResponseStatus.SUCCESS) {
                showToast("Invitation declined!");
                // TODO: set shouldUpdatePairing to true after DECLINE_INVITATION is sent.
                shouldUpdatePairing = true;
            } else {
                showToast("Error declining invitation");
            }
        });


    }

    /**
     *
     * @param pairing the Event of pairing
     * @param player either 1 or 2
     */
    private void beginGame(Event pairing, int player) {
        // TODO: set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        // TODO: start MainActivity and pass player as data
        Intent intent = new Intent(PairingActivity.this, MainActivity.class);
        intent.putExtra("PLAYER_KEY", player);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: set shouldUpdatePairing to true
        shouldUpdatePairing = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

        // TODO: set shouldUpdatePairing to false
        shouldUpdatePairing = false;
        // TODO: logout by calling close() function of SocketClient
        SocketClient.getInstance().close();
    }

}