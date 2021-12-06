package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By

open class MyListsPageObject(driver: AppiumDriver<MobileElement>?) : MainPageObject(driver) {

    private val FOLDER_BY_NAME_TEMPLATE = "//*[@text='{FOLDER_NAME}']"
    private val ARTICLE_BY_TITLE = "//*[@text='{TITLE}']"

    fun getFolderXPathByName(nameOfFolder: String): String {
        return  FOLDER_BY_NAME_TEMPLATE.replace("{FOLDER_NAME}", nameOfFolder)
    }

    fun getSavedArticleXPathByTitle(articleTitle: String): String {
        return  ARTICLE_BY_TITLE.replace("{TITLE}", articleTitle)
    }

    open fun openFolderByName(nameOfFolder: String) {
        val folderNameXPath = getFolderXPathByName(nameOfFolder)
        this.click(By.xpath(folderNameXPath), "Cannot find folder with name $nameOfFolder", 5)
    }

    open fun waitForArticleToAppear(articleTitle: String) {
        val articleXPath = getFolderXPathByName(articleTitle)
        this.waitElement(By.xpath(articleXPath), "Cannot find saved article", 15)
    }

    open fun swipeByArticleToDelete(articleTitle: String) {
        this.waitForArticleToAppear(articleTitle)
        val articleXPath = getFolderXPathByName(articleTitle)
        this.swipeLeft(By.xpath(articleXPath), "Cannot find saved article")
        this.waitForArticleToDisappear(articleTitle)
    }

    open fun waitForArticleToDisappear(articleTitle: String) {
        val articleXPath = getFolderXPathByName(articleTitle)
        this.hasNotElement(By.xpath(articleXPath), "Saved article still present", 15)
    }
}