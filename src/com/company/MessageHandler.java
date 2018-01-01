package com.company;

import java.util.ArrayList;

public class MessageHandler {
    private ArrayList<Schedule> list;
    private Schedule schedule;

    MessageHandler(ArrayList<Schedule> list, Schedule schedule) {
        this.list = list;
        this.schedule = schedule;
    }

    public void handle() {
        if (schedule.isDeleted()) {
            list.get(schedule.getId() - 1).setDeleted(true);
            System.out.println("Element with ID " + schedule.getId() + " was deleted");
        } else if (schedule.getId() <= list.size()) {
            list.set(schedule.getId() - 1, schedule);
            System.out.println("Element with ID " + schedule.getId() + " was edited");
        } else {
            list.add(schedule.getId() - 1, schedule);
            System.out.println("Element with ID " + schedule.getId() + " was added");
        }
    }
}
