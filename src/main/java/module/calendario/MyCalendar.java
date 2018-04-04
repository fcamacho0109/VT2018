package module.calendario;

import Core.Session;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import module.myEntryFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class MyCalendar  {

    CalendarView calendarView;
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




    public MyCalendar() {
        if(Session.getInstance().getMyCalendar() == null){
            calendarView = new CalendarView();
            Session.getInstance().setMyCalendar(this);
            System.out.println("new");
        }else{
            calendarView = Session.getInstance().getMyCalendar().calendarView;
            System.out.println("from singleton");
        }
        Locale.setDefault(new Locale("es", "SP"));


        System.out.println("bienvenido "+Session.getInstance().getUser().rol);
        Stage myStage = Session.getInstance().getApp().stage;
        myStage.setScene(Session.getInstance().getApp().getView());
        myStage.setTitle(Session.getInstance().getApp().getTitle());



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
    }
    public CalendarView getCalendar(){
        return calendarView;
    }
}
