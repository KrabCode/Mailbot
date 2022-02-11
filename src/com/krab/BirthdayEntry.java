package com.krab;

import java.time.LocalDateTime;

public class BirthdayEntry {
    private String name;
    private String birthday;
    private transient LocalDateTime nextBirthday;

    LocalDateTime getNextBirthday() {
        return nextBirthday;
    }

    void setNextBirthday(LocalDateTime nextBirthday) {
        this.nextBirthday = nextBirthday;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getBirthday() {
        return birthday;
    }

    void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String toString() {
        return "BirthdayEntry{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nextBirthday=" + nextBirthday +
                '}';
    }
}
