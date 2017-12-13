package com.company;

import java.util.ArrayList;

public class Main {
    private static ArrayList<Schedule> list = new ArrayList<>();

    static {
        list.add(new Schedule(1, "8:00", "Subject1"));
        list.add(new Schedule(2, "9:00", "Subject2"));
        list.add(new Schedule(3, "10:00", "Subject3"));
        list.add(new Schedule(4, "11:00", "Subject4"));
        list.add(new Schedule(5, "12:00", "Subject5"));
    }

    public static void main(String[] args) {
        new TCPServer(list).startServer();
    }
}
