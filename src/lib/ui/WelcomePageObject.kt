package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By

class WelcomePageObject (driver: AppiumDriver<MobileElement>?) : MainPageObject(driver) {

    private val STEP_MORE_LEARN_LINK = "Learn more about Wikipedia"
    private val NEXT = "Next"
    private val GET_STARTED = "Get started"
    private val ADD_LANGUAGES = "Add or edit preferred languages"
    private val NEW_WAYS_TO_EXPLORE = "New ways to explore"
    private val DATA_COLLECTED = "Learn more about data collected"

    open fun waitForLearnMore() {
        this.waitElement(By.id(STEP_MORE_LEARN_LINK), "Cannot find Learn more link", 10)
    }

    open fun clickNextButton() {
        this.click(By.id(NEXT), "Cannot find next link", 10)
    }

    open fun waitForNewWayToExplore() {
        this.waitElement(By.id(NEW_WAYS_TO_EXPLORE), "Cannot find New ways to explore", 10)
    }

    open fun waitForAddOrEdit() {
        this.waitElement(By.id(ADD_LANGUAGES), "Cannot find Add or edit preferred languages", 10)
    }

    open fun waitForLearnMoreAboutDataCollected() {
        this.waitElement(By.id(DATA_COLLECTED), "Cannot find Collected", 10)
    }

    open fun clickGetStartedButton() {
        this.click(By.id(GET_STARTED), "Cannot find Get started", 10)
    }
}