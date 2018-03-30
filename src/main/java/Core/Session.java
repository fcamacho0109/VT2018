package Core;

import javafx.stage.Stage;
import module.App.App;

public class Session {

    private User user;
    private App app;
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
