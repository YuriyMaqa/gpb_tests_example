package ru.gazprombank.tests;

import ru.gazprombank.helpers.DriverUtils;
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


@Tag("gpb")
@Owner("YuriyMqa")
public class GbpTest extends TestBase {

    @Test
    @Description("Homework test")
    @DisplayName("Проверка скачивания файла pdf 'Требования к обеспечению на первичном рынке' ")
    void generatedTest() {
        step("open /documents_and_tariffs/?fs=722#119", () -> {
            open("https://www.gazprombank.ru/documents_and_tariffs/?fs=722#119");
        });

        step("Проверка содержимого файла pdf 'Требования к обеспечению на первичном рынке' ", () -> {
            File pdfDownload = $("#bx_3218110189_5066157")
                    .shouldBe(Condition.visible, Duration.ofSeconds(10)).download();
            PDF pdf = new PDF(pdfDownload);
            assertThat(pdf.text).contains("Перечень требований к объекту недвижимости на первичном рынке");
        });

    }

    @Test
    @Description("Homework test")
    @DisplayName("Проверка открытия страницы по тексту в заголовке")
    void titleTest() {
        step("Open url 'https://www.gazprombank.ru/'", () ->
                open("https://www.gazprombank.ru/"));

        step("Page title should have text 'Газпромбанк — «Газпромбанк» (Акционерное общество)'", () -> {
            String expectedTitle = "Газпромбанк — «Газпромбанк» (Акционерное общество)";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @Description("Homework test")
    @DisplayName("Проверка на ошибки в консоль логе страницы")
    void consoleShouldNotHaveErrorsTest() {
        step("Open url 'https://www.gazprombank.ru/'", () ->
                open("https://www.gazprombank.ru/"));

        step("Console logs should not contain text 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @Test
    @Description("Homework test")
    @DisplayName("Проверка на работу метода \"Получение максимальной ставки\" на странице вклада")
    void generatedTest1() {

        String depositAmount = "50000",
                depositAmountDefault = "1 500 000 ₽";

        step("Открыть страницу \"Вклад 'Управлять'\"", () -> {
            open("https://www.gazprombank.ru/personal/increase/deposits/detail/1929/");
        });

        step("Изменяем сумму вклада на 50 000", () -> {
            $("[name='deposit_amount']").sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            $("[name='deposit_amount']").setValue(depositAmount);
        });

        step("Проверяем, что появился пункт 'Получить максимальную ставку'", () -> {
            $(".nr-deposit-calc-rate__max-rate-button").shouldHave(text("Получить максимальную ставку"));
        });

        step("Кликаем на пункт 'Получить максимальную ставку'", () -> {
            $(".nr-deposit-calc-rate__max-rate-button").click();
        });

        step("Проверяем, что значение в поле \"сумма вклада\" изменилось на дефолтное", () -> {
            $("[name='deposit_amount']")
                    .shouldHave(attribute("value", depositAmountDefault));
        });

        step("Проверяем, что появилась надпись  \"Вы можете увеличить сумму вклада, ставка не изменится\"",
                () -> {
                    $(".nr-deposit-calc-rate__text")
                            .shouldHave(text("Вы можете увеличить сумму вклада, ставка не изменится"));
                });

    }

    @Test
    @Description("Homework test")
    @DisplayName("Проверка наличия фильтра подтверждения по смс в форме обратной связи")
    void smsFilterTest() {
        step("Открыть страницу \"Вклад 'Управлять'\"", () -> {
            open("https://www.gazprombank.ru/personal/increase/deposits/detail/1929/");
        });

        step("Заполняем форму обратной связи валидными значениями", () -> {
            $(".nr-step-form-fields").$("[name=fio]").setValue("Иванов Пётр Васильевич");
            $(".nr-formik-phone__input").setValue("9521365491");
            $(".nr-step-form-fields").$("[name=email]").setValue("dsfsdg@gamil.com");
        });

        step("Даём согласие на обработку данных и кликаем \"далее\"", () -> {
            $("#i_agree").click();
            $(".nr-step-form-sms [type=button]").click();
        });

        step("Проверяем, что появилось поле ввода смс с кодом", () -> {
            $(".nr-formik-text__label").shouldBe(Condition.visible, Duration.ofSeconds(10));
            $(".nr-formik-text__label").shouldHave(text("Код из СМС"));
        });
    }

    @Test
    void tenzortest() {
        open("");

    }
}
