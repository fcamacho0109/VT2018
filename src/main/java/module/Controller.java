package module;

import Core.Database;
import Core.Session;
import Core.User;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Controller implements Initializable {

    ArrayList<String> modalsPresented = new ArrayList<String>();
    ArrayList<String> modalsClosed = new ArrayList<String>();

    public Set<Entry<?>> getAllEntries(CalendarView calendarView, Interval interval){
        ArrayList<Entry<?>> eventos = new ArrayList<Entry<?>>();
        for(int i=0;i<calendarView.getCalendarSources().size();i++){
            for(int j=0;j<calendarView.getCalendarSources().get(i).getCalendars().size();j++){
                Calendar cal = calendarView.getCalendarSources().get(i).getCalendars().get(j);

                eventos.addAll(calendarView.getCalendarSources().get(i).getCalendars().get(j).findEntries(""));

            }
        }
        Set<Entry<?>> events = new LinkedHashSet<>(eventos);
        return events;
    }


    @FXML
    TextField userLoginText;
    @FXML
    PasswordField userLoginPass;
    @FXML
    Button loginBtn;



    @FXML
    private void login_Btn(){

        String user = "";
        String pass = "";
        user = userLoginText.getText().toString();
        pass = userLoginPass.getText().toString();





        if (!(user.equals("") && pass.equals("")) ) {
            userLoginText.setText("");
            userLoginPass.setText("");
            //userLoginText.setPromptText("Minimo 8 caracteres");
            //userLoginPass.setPromptText("Minimo 8 caracteres");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Aun no se definen acciones para el boton!" +
                    "\nEditar metodo login_Btn() para agregar login \n" +
                    "con user role y password de la BD");


            alert.showAndWait();
        }
        else {
            Locale.setDefault(new Locale("es", "SP"));

            User user1 = new User(user,"LastName harcoded","33 33 33 33",User.Rol.Admin);
            Session.getInstance().setUser(user1);
            System.out.println("bienvenido "+Session.getInstance().getUser().rol);
            Stage myStage = (Stage)(loginBtn.getScene().getWindow() );
            //Parent root = FXMLLoader.load(getClass().getResource("/res_main/MainScreen.fxml"));

            myStage.setScene(Session.getInstance().getApp().getView());
            myStage.setTitle(Session.getInstance().getApp().getTitle());


            CalendarView calendarView = new CalendarView();

            //Calendar citas_doctor = new Calendar("Citas doctor");
            //Calendar citas_estética = new Calendar("Citas estética");



            /*Entry<?> consulta = new Entry<>();
            consulta.setId("1");
            consulta.setTitle("Consulta de doc");
            consulta.setFullDay(false);

            consulta.setInterval(start,end);
            consulta.setLocation("Detalleees");
            citas_doctor.addEntry(consulta);

            citas_doctor.setStyle(Calendar.Style.STYLE2);
            citas_estética.setStyle(Calendar.Style.STYLE1);
*/
            CalendarSource myCalendarSource = new CalendarSource("Croquetos");
            myCalendarSource.getCalendars().setAll(Session.getInstance().getCalendars());
            //calendarView.getCalendarSources().setAll(myCalendarSource);

            calendarView.setShowAddCalendarButton(false);
            calendarView.setRequestedTime(LocalTime.now());
            calendarView.getCalendarSources();
            calendarView.setShowDeveloperConsole(true);
            System.out.println(calendarView.toString());
            calendarView.getDayPage();
            calendarView.setShowPrintButton(false);

            myEntryFactory factory = new myEntryFactory();
            calendarView.setEntryFactory(lambda -> factory.newEntry());



            calendarView.getCalendarSources().setAll(myCalendarSource);

            calendarView.setRequestedTime(LocalTime.now());

            Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                    while (true) {
                        Platform.runLater(() -> {
                            calendarView.setToday(LocalDate.now());
                            calendarView.setTime(LocalTime.now());
                            Entry<?> intervalo = new Entry<>();
                            intervalo.changeStartDate(LocalDate.now());
                            intervalo.changeStartTime(LocalTime.now());
                            intervalo.changeEndDate(LocalDate.now());
                            intervalo.changeEndTime(LocalTime.now().plusMinutes(15));

                            Set<Entry<?>> list = getAllEntries(calendarView,intervalo.getInterval());
                            list.forEach( l -> {
                                if(l.intersects(intervalo)) {
                                    System.out.print(l.getStartDate() + " ");
                                    System.out.print(l.getStartTime() + " - ");
                                    System.out.print(l.getEndTime() + " \n");
                                    System.out.println(l.getProperties());

                                    //System.out.print(intervalo + "\n");

                                    if(!modalsPresented.contains(l.getId())&&!modalsClosed.contains(l.getId())){
                                        modalsPresented.add(l.getId());
                                        final Stage dialog = new Stage();
                                        dialog.initModality(Modality.APPLICATION_MODAL);
                                        dialog.initOwner(myStage);
                                        VBox dialogVbox = new VBox(20);
                                        dialogVbox.getChildren().add(new Text("Tienes una cita con id "+l.getId()));
                                        Scene dialogScene = new Scene(dialogVbox, 300, 200);

                                        dialog.setScene(dialogScene);
                                        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                            @Override
                                            public void handle(WindowEvent event) {
                                                System.out.println("Close");
                                                modalsPresented.remove(l.getId());
                                                modalsClosed.add(l.getId());
                                            }
                                        });
                                        dialog.show();
                                    }


                                    System.out.println("---------------------------");
                                }

                            });
                        });

                        try {
                            // update every 10 seconds
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
            };

            updateTimeThread.setPriority(Thread.MIN_PRIORITY);
            updateTimeThread.setDaemon(true);
            updateTimeThread.start();
            Session.getInstance().getApp().stage.centerOnScreen();
            Session.getInstance().getApp().setPane(calendarView);
            //Session.getInstance().getApp().getController().setPane();


        }



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database.getInstance();
        Database.getInstance().getCalendarios();
        Database.getInstance().getEventos();

        /*comboType.setItems(FXCollections.observableArrayList(
                User.Rol.Admin,
                User.Rol.Usuario,
                User.Rol.Master
        ));
        */
    }
    private void MyCustomEntryDialog(){

    }
}
//calendarView.setDefaultCalendarProvider(param -> citas_doctor);




            /*VCalendar vCalendar = new VCalendar();
            ICalendarAgenda agenda = new ICalendarAgenda(vCalendar);
            VEvent vEvent = new VEvent()
                                   .withDateTimeStart(LocalDateTime.now().minusMonths(1))
                                   .withDateTimeEnd(LocalDateTime.now().minusMonths(1).plusHours(1))
                                   .withSummary("Example Daily Event")
                                   .withRecurrenceRule("RRULE:FREQ=DAILY")
                                   .withUniqueIdentifier("exampleuid000jfxtras.org");
           vCalendar.addChild(vEvent);

            BorderPane root = new BorderPane();
            root.setCenter(agenda);
            /*

            Agenda agenda = new Agenda();

            // add an appointment
            agenda.appointments().addAll(
                    new Agenda.AppointmentImplLocal()
                            .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
                            .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
                            .withDescription("It's time")
                            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
            );
            */
//myStage.setScene(new Scene(calendarView));