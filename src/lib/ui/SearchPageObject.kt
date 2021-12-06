package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By

open class SearchPageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver) {

    private var SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]"
    private var SEARCH_INPUT = "search_src_text"
    private var SEARCH_CANCEL_BUTTON = "search_close_btn"
    private var SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']"

    fun getResultSearchElement(subString: String): String {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", subString)
    }

    fun initSearchInput() {
        this.waitElement(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element", 5)
        this.click(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5)
    }

    fun typeSearchLine(searchLine: String) {
        this.sendKeys(By.id(SEARCH_INPUT), searchLine, "Cannot find and and type into search input", 5)
    }
    fun waitForSearchResult(subString: String) {
        val searchResultXpath: String = getResultSearchElement(subString)
        this.waitElement(By.xpath(searchResultXpath), "Cannot find search result $subString", 5)
    }

    fun clickByArticleWithSubstring(subString: String) {
        val searchResultXpath: String = getResultSearchElement(subString)
        this.click(By.xpath(searchResultXpath), "Cannot find and click search result $subString", 10)
    }

    fun waitForCancelButtonToAppear() {
        this.waitElement(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5)
    }

    fun waitForCancelButtonToDisappear() {
        this.hasNotElement(By.id(SEARCH_CANCEL_BUTTON), "Can find search cancel button", 5)
    }

    fun clickCancelButton() {
        this.click(By.id(SEARCH_CANCEL_BUTTON), "Can find and click on cancel button", 5)
    }
}