package com.krab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sun.deploy.util.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {


    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("This automated mailing program is used with 2 arguments\n" +
                    "1) the location of the json file containing the birthday entries\n" +
                    "2) the e-mail address to notify");
        }
        JsonReader reader = new JsonReader(new FileReader(args[0]));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BirthdayEntry[] birthdays = gson.fromJson(reader, BirthdayEntry[].class);
        List<BirthdayEntry> birthdaysToNotify = new ArrayList<>();
        for (BirthdayEntry birthdayEntry : birthdays) {
            if(shouldNotifyInWeeklyMail(birthdayEntry)){
                birthdaysToNotify.add(birthdayEntry);
            }
        }
        if(birthdaysToNotify.size() == 0){
            System.out.println("No birthdays upcoming, exiting...");
        }else{
            System.out.println(birthdaysToNotify.size() + " birthdays upcoming, notifying");
            sendBirthdayNotificationMail(birthdaysToNotify, args[1]);
        }
    }

    private static boolean shouldNotifyInWeeklyMail(BirthdayEntry birthdayEntry) {
        LocalDateTime originalBirthday = LocalDateTime.parse(birthdayEntry.getBirthday());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextBirthday = originalBirthday.withYear(now.getYear());
        if(nextBirthday.isBefore(now)){
            nextBirthday = nextBirthday.plusYears(1);
        }
        birthdayEntry.setNextBirthday(nextBirthday);
//        System.out.println(birthdayEntry.getName() + "'s next birthday is on " + nextBirthday);
        return nextBirthday.minusDays(7).isBefore(now);
    }

    private static void sendBirthdayNotificationMail(List<BirthdayEntry> birthdayEntries, String emailAddress) {
        StringBuilder mailContent = new StringBuilder();
        for(BirthdayEntry entry : birthdayEntries){
            mailContent.append(entry.getName()).append("'s next birthday is coming up on ")
                    .append(entry.getNextBirthday().getDayOfWeek().toString().toLowerCase())
                    .append(" ")
                    .append(entry.getNextBirthday().format(DateTimeFormatter.ofPattern("dd.MM."))).append("\n");
        }
        System.out.println("mail content:\n " + mailContent);
        sendMail(mailContent, emailAddress);
    }

    private static void sendMail(StringBuilder mailContent, String emailAddress) {
        // TODO
    }
}
