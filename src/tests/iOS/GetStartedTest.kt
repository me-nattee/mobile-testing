package tests.iOS

import lib.IOSTestCase
import lib.ui.WelcomePageObject
import org.junit.Test

open class GetStartedTest : IOSTestCase() {

    @Test
    open fun testWelcome(){
        val page = WelcomePageObject(driver)

        page.waitForLearnMore()
        page.clickNextButton()

        page.waitForNewWayToExplore()
        page.clickNextButton()

        page.waitForAddOrEdit()
        page.clickNextButton()

        page.waitForLearnMoreAboutDataCollected()
        page.clickGetStartedButton()
    }
}