package clarkson.ee408.tictactoev4.model;

//import org.jetbrains.annotations.NotNull;

/**
 * Class containing information related to a game / connection event
 */
public class Event {

    private final int m_EventID;
    private final String m_Sender;
    private final String m_Opponent;
    private EventStatus m_Status;
    private String m_Turn;
    private int m_LastMove;

    /**
     * Default Constructor for Event
     */
    public Event() {
        m_Sender = m_Opponent = m_Turn = "Uninitialized";
        m_EventID = m_LastMove = -1;
        m_Status = null;
    }

    /**
     * Parameterized constructor for Event
     *
     * @param eventID  A global unique integer to represent an event.
     * @param sender   Represents the username of the user that sends the game invitation
     * @param opponent Represents the username of the user that the game invitation was * sent to
     * @param status   Represents the status of a game
     * @param turn     The username of the player that made the last move
     * @param lastMove An integer storing the last move of the game
     */
    public Event(int eventID, String sender, String opponent, EventStatus status, String turn, int lastMove) {
        m_EventID = eventID;
        m_Sender = sender;
        m_Opponent = opponent;
        m_Status = status;
        m_Turn = turn;
        m_LastMove = lastMove;
    }

    /**
     * @return Returns value of m_LastMove
     */
    public int getLastMove() {
        return m_LastMove;
    }

    /**
     * @param lastMove Value to assign to m_LastMove
     */
    public void setLastMove(int lastMove) {
        m_LastMove = lastMove;
    }

    /**
     * @return Returns value of m_Turn
     */
    public String getTurn() {
        return m_Turn;
    }

    /**
     * @param turn Value to assign to m_Turn
     */
    public void setTurn(String turn) {
        m_Turn = turn;
    }

    /**
     * @return Returns value of m_Opponent
     */
    public String getOpponent() {
        return m_Opponent;
    }

    /**
     * @return Returns value of m_Sender
     */
    public String getSender() {
        return m_Sender;
    }

    /**
     * @return Returns value of m_EventID
     */
    public int getEventID() {
        return m_EventID;
    }

    /**
     * @return Returns value of m_Status
     */
    public EventStatus getStatus() {
        return m_Status;
    }

    /**
     * @param status Value to assign to m_Status
     */
    public void setStatus(EventStatus status) {
        m_Status = status;
    }

    /**
     * @param otherEvent Event to compare with
     * @return boolean value representing whether two events are equal
     */
    public boolean equals(Event otherEvent) {
        return m_EventID == otherEvent.m_EventID;
    }

    /**
     * Represents the 6 possible states for an Event
     */
    public enum EventStatus {
        PENDING,
        DECLINED,
        ACCEPTED,
        PLAYING,
        COMPLETED,
        ABORTED
    }

}