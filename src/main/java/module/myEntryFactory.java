package module;

import Core.Session;
import com.calendarfx.model.Entry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class myEntryFactory {
    public myEntryFactory(){

    }
    public Entry<?> newEntry() {
        Entry<?> entry = new Entry<>();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(Session.getInstance().getApp().stage);
        try {
            VBox dialogVbox = FXMLLoader.load(getClass().getResource("/popUpEntry/popUpEntry.fxml"));


            Scene dialogScene = new Scene(dialogVbox);
            dialog.setTitle("Nueva Cita");
            dialog.setScene(dialogScene);

            dialog.show();

            return entry;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
