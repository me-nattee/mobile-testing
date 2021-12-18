package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import junit.framework.TestCase
import org.openqa.selenium.ScreenOrientation

abstract class CoreTestCase : TestCase() {
    abstract var driver: AppiumDriver<MobileElement>?
    var url = "http://127.0.0.1:4723/wd/hub"
    abstract var platform: Platform

    override fun setUp() {
        super.setUp()
        this.platform = Platform();
        driver = this.platform.getDriver()
        this.driver

        driver!!.rotate(ScreenOrientation.PORTRAIT)
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()

        driver?.quit() ?: throw Exception("Driver instance was unable to quit.")
    }
}