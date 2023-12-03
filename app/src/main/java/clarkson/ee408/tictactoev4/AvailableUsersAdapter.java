package clarkson.ee408.tictactoev4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import clarkson.ee408.tictactoev4.model.User;

public class AvailableUsersAdapter extends RecyclerView.Adapter<AvailableUsersAdapter.UserViewHolder> {

    private final UserClickListener mUserClickListener;
    private List<User> users;
    private final Context mContext;

    /**
     * Constructor for the adapter
     * @param context the activity Context
     * @param listener click listener
     */
    public AvailableUsersAdapter(Context context, UserClickListener listener) {
        mContext = context;
        mUserClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.view_holder_user, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.userButton.setText(String.format("%s (%s)", user.getDisplayName(), user.getUsername()));
        holder.userButton.setOnClickListener(view -> mUserClickListener.onUserClicked(user));
    }

    @Override
    public int getItemCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    /**
     * Set the list of users and update the recyclerview
     * @param users list of User
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    /**
     * An interface for the adapter click listener
     */
    public interface UserClickListener {
        void onUserClicked(User user);
    }

    //View Holder class
    static class UserViewHolder extends RecyclerView.ViewHolder {

        Button userButton;

        private UserViewHolder(View basicViewView) {
            super(basicViewView);

            userButton = basicViewView.findViewById(R.id.button_available_user);
        }
    }
}