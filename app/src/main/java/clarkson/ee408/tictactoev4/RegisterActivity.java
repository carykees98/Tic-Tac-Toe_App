package clarkson.ee408.tictactoev4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import clarkson.ee408.tictactoev4.client.AppExecutors;
import clarkson.ee408.tictactoev4.client.SocketClient;
import clarkson.ee408.tictactoev4.model.User;
import clarkson.ee408.tictactoev4.socket.Request;
import clarkson.ee408.tictactoev4.socket.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText displayNameField;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Getting Inputs
        Button registerButton = findViewById(R.id.buttonRegister);
        Button loginButton = findViewById(R.id.buttonLogin);
        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);
        confirmPasswordField = findViewById(R.id.editTextConfirmPassword);
        displayNameField = findViewById(R.id.editTextDisplayName);

        // TODO: Initialize Gson with null serialization option

        //Adding Handlers
        //TODO: set an onclick listener to registerButton to call handleRegister()

        //TODO: set an onclick listener to loginButton to call goBackLogin()
    }

    /**
     * Process registration input and pass it to {@link #submitRegistration(User)}
     */
    public void handleRegister() {
        // TODO: declare local variables for username, password, confirmPassword and displayName. Initialize their values with their corresponding EditText

        // TODO: verify that all fields are not empty before proceeding. Toast with the error message

        // TODO: verify that password is the same af confirm password. Toast with the error message

        // TODO: Create User object with username, display name and password and call submitRegistration()
    }

    /**
     * Sends REGISTER request to the server
     * @param user the User to register
     */
    void submitRegistration(User user) {
        //TODO: Send a REGISTER request to the server, if SUCCESS reponse, call goBackLogin(). Else, Toast the error message
    }

    /**
     * Change the activity to LoginActivity
     */
    private void goBackLogin() {
        //TODO: Close this activity by calling finish(), it will automatically go back to its parent (i.e,. LoginActivity)
    }

}