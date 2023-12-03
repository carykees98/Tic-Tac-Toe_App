package clarkson.ee408.tictactoev4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import clarkson.ee408.tictactoev4.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Getting UI elements
        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);
        usernameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);

        // TODO: Initialize Gson with null serialization option
        gson = new Gson();

        // Adding Handlers
        loginButton.setOnClickListener(view -> handleLogin());
        registerButton.setOnClickListener(view -> gotoRegister());
    }

    /**
     * Process login input and pass it to {@link #submitLogin(User)}
     */
    public void handleLogin() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        // TODO: verify that all fields are not empty before proceeding. Toast with the error message
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            // TODO: Create User object with username and password and call submitLogin()
            User user = new User(username, password);
            submitLogin(user);
        }
    }

    /**
     * Sends a LOGIN request to the server
     *
     * @param user User object to login
     */
    public void submitLogin(User user) {
        // TODO: Send a LOGIN request, If SUCCESS response, call gotoPairing(), else, Toast the error message from server
        // For demonstration purposes, assuming login is successful
        boolean loginSuccess = true;
        if (loginSuccess) {
            gotoPairing(user.getUsername());
        } else {
            Toast.makeText(this, "Login failed. Please check your credentials", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Switch the page to {@link PairingActivity}
     *
     * @param username the data to send
     */
    public void gotoPairing(String username) {
        // TODO: start PairingActivity and pass the username
        startActivity(PairingActivity.createIntent(this, username));
    }

    /**
     * Switch the page to {@link RegisterActivity}
     */
    public void gotoRegister() {
        // TODO: start RegisterActivity
        startActivity(RegisterActivity.createIntent(this));
    }
}
