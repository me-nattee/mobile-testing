package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

open class ArticlePageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver) {

    private val TITLE = "view_page_title_text"
    private val FOOTER = "//*[@text='View page in browser']"
    private val OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']"
    private val OPTIONS_ADD_TO_MY_LIST = "//*[@text='Add to reading list']"
    private val OVERLAY = "org.wikipedia:id/onboarding_button"
    private val MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input"
    private val MY_LIST_OK_BUTTON = "//*[@text='OK']"
    private val CLOSE_ARTICLE = "//android.widget.ImageButton[@content-desc='Navigate up']"

    open fun waitForTitleOfElement(): WebElement {
        return this.waitElement(By.id(TITLE), "No expected title", 15)
    }

    open fun getArticleTitle(): String {
        val title: WebElement = waitForTitleOfElement()
        return title.getAttribute("text")
    }

    open fun swipeToFooter() {
        this.swipeToElement(By.xpath(FOOTER), "Cannot find the end of the article", 20)
    }

    open fun addArticleToMyList(nameOfFolder: String) {
        this.click(By.xpath(OPTIONS_BUTTON), "Cannot find button to open More Options", 5)
        this.waitElement(By.xpath(OPTIONS_ADD_TO_MY_LIST), "Cannot find option in More Options", 5)
        this.click(By.xpath(OPTIONS_ADD_TO_MY_LIST), "Cannot find option in More Options", 5)
        this.click(By.id(OVERLAY), "Cannot find 'Got it'", 5)
        this.clear(By.id(MY_LIST_NAME_INPUT), "Cannot clear a field", 5)
        this.sendKeys(By.id(MY_LIST_NAME_INPUT), nameOfFolder, "Cannot put text", 5)
        this.click(By.xpath(MY_LIST_OK_BUTTON), "Cannot find OK button", 5)
    }

    open fun closeArticle() {
        this.click(By.xpath(CLOSE_ARTICLE), "Cannot find cross button", 5)
    }

}