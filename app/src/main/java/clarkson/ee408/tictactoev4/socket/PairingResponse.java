package clarkson.ee408.tictactoev4.socket;

import clarkson.ee408.tictactoev4.model.Event;
import clarkson.ee408.tictactoev4.model.User;

import java.util.ArrayList;
import java.util.List;

public class PairingResponse extends Response {
    private List<User> m_availableUsers;
    private Event m_invitation;
    private Event m_invitationResponse;

    public PairingResponse() {
        super(null, null);
        m_availableUsers = null;
        m_invitation = null;
        m_invitationResponse = null;
    }

    public PairingResponse(ResponseStatus status, String message, List<User> availableUsers, Event invitation, Event invitationResponse) {
        super(status, message);
        m_availableUsers = availableUsers;
        m_invitation = invitation;
        m_invitationResponse = invitationResponse;
    }


    public List<User> getAvailableUsers() {
        return m_availableUsers;
    }

    public void setAvailableUsers(List<User> availableUsers) {
        m_availableUsers = availableUsers;
    }

    public Event getInvitation() {
        return m_invitation;
    }

    public void setInvitation(Event invitation) {
        m_invitation = invitation;
    }

    public Event getInvitationResponse() {
        return m_invitationResponse;
    }

    public void setInvitationResponse(Event invitationResponse) {
        m_invitationResponse = invitationResponse;
    }
}
