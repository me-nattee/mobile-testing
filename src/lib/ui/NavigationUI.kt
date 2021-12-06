package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By

open class NavigationUI(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver) {

    private val MY_LISTS_LINK = "//android.widget.FrameLayout[@content-desc='My lists']"

    open fun clickMyLists() {
        this.click(By.xpath(MY_LISTS_LINK), "Cannot find button My lists", 5)
    }



}