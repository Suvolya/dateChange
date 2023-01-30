package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.getFormatDate;

public class DateChangeTest {

    @BeforeEach
    void setupTest() {

        open("http://localhost:9999");
    }

    @Test
    void shouldTestFormChangeDate() {

        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        LocalDate initDate = DataGenerator.generateDate();
        String date = getFormatDate(initDate);
        String date2 = getFormatDate(initDate.plusDays(3));
        $("[data-test-id=date] [value]").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(date);
        $("[name='name']").setValue(DataGenerator.generateName());
        $("[name='phone']").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $$("span.button__text").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на " + date), Duration.ofSeconds(15)).shouldBe(Condition.visible);

        $("[data-test-id=date] [value]").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] [value]").setValue(date2);
        $$("span.button__text").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15)).shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] button").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + date2), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}