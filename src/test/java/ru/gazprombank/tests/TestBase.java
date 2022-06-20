package ru.gazprombank.tests;

import ru.gazprombank.config.Project;
import ru.gazprombank.helpers.AllureAttachments;
import ru.gazprombank.helpers.DriverSettings;
import ru.gazprombank.helpers.DriverUtils;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;


@ExtendWith({AllureJunit5.class})
public class TestBase {
    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        DriverSettings.configure();
    }

    @AfterEach
    public void addAttachments() {
        if (hasWebDriverStarted()) {

            String sessionId = DriverUtils.getSessionId();
            AllureAttachments.addScreenshotAs("Last screenshot");
            AllureAttachments.addPageSource();
            AllureAttachments.addBrowserConsoleLogs();
            Selenide.closeWebDriver();

            if (Project.isVideoOn()) {
                AllureAttachments.addVideo(sessionId);
            }
        }
    }
}
