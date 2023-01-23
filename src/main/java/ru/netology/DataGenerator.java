package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker getFaker() {
        return new Faker(new Locale("ru"));
    }

    public static LocalDate generateDate() {
        long daysToAddForFirstMeeting = LocalDate.now().plusDays(4).toEpochDay();
        long daysToAddForSecondMeeting = LocalDate.now().plusDays(7).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(daysToAddForFirstMeeting, daysToAddForSecondMeeting);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static String generateCity() {
        return DataGenerator.getFaker().address().city();
    }

    public static String generateName() {
        return DataGenerator.getFaker().name().fullName();
    }

    public static String generatePhone() {
        return DataGenerator.getFaker().phoneNumber().phoneNumber();
    }
}