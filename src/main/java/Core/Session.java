package Core;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import javafx.stage.Stage;
import module.App.App;

import java.util.ArrayList;

public class Session {

    private User user;
    private App app;
    private ArrayList<Calendar> calendars;
    private ArrayList<Entry<?>> events;

    public ArrayList<Entry<?>> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Entry<?>> events) {
        this.events = events;
    }

    public ArrayList<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(ArrayList<Calendar> calendars) {
        this.calendars = calendars;
    }
    private Session(){
    }
    public void initializaSession(Stage stage){
        app = new App(stage);
    }
    private static Session sharedInstance = new Session(); //singleton para manejar una sola session
    public static Session getInstance(){
        return sharedInstance;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public App getApp() {
        return app;
    }


    public void setApp(App app) {
        this.app = app;
    }
}
