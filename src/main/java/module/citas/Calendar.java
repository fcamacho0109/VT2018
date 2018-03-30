package module.citas;


import Core.Session;
import jfxtras.scene.control.agenda.Agenda;

import java.time.LocalDate;

public class Calendar {
    public Calendar(){
        Agenda agenda = new Agenda();

        // add an appointment
        agenda.appointments().addAll(
                new Agenda.AppointmentImplLocal()
                        .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
                        .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
                        .withDescription("It's time")
                        .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
        );
        Session.getInstance().getApp().getController().setPane(agenda);

    }
}
