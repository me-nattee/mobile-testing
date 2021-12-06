import lib.CoreTestCase
import lib.ui.*
import org.junit.Assert
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.WebElement

open class FirstTest : CoreTestCase() {

    override fun setUp() {
        super.setUp()
        this.driver
    }

    @Test
    fun testSearch() {
        val search = SearchPageObject(driver)

        search.initSearchInput()
        search.typeSearchLine("Java")
        search.waitForSearchResult("Object-oriented programming language")
    }

    @Test
    fun testCancelSearch() {
        val action = MainPageObject(driver)
        val search = SearchPageObject(driver)

        search.initSearchInput()
        search.waitForCancelButtonToAppear()
        search.clickCancelButton()
        search.waitForCancelButtonToDisappear()
    }

    @Test
    fun testCompareTitle() {
        val action = MainPageObject(driver)
        val search = SearchPageObject(driver)
        val article = ArticlePageObject(driver)

        search.initSearchInput()
        search.typeSearchLine("Java")
        search.clickByArticleWithSubstring("Object-oriented programming language")
        val articleTitle = article.getArticleTitle()

        action.hasTitleOfResult(By.id("view_page_title_text"), articleTitle, "No expected title")
    }

    @Test
    fun testSwipe() {
        val search = SearchPageObject(driver)
        val article = ArticlePageObject(driver)

        search.initSearchInput()
        search.typeSearchLine("Appium")
        search.clickByArticleWithSubstring("Appium")
        article.waitForTitleOfElement()
        article.swipeToFooter()
    }

    @Test
    fun testSearchHasPlaceholder() {
        val action = MainPageObject(driver)
        action.hasText(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@text='Search Wikipedia']"),
                "Search Wikipedia", "No expected placeholder")
    }

    @Test
    fun testSearchAndClearResults() {
        val action = MainPageObject(driver)
        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        action.hasResults(By.id("page_list_item_container"), "Can't find any results of search")
        action.clear(By.id("search_src_text"), "Can't find element for clearing", 5)
        action.hasNotElement(By.id("page_list_item_container"), "There are results", 5)
    }

    @Test
    fun testSearchIsRelevant() {
        val action = MainPageObject(driver)
        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), "Java", "Can't find search input", 5)
        action.resultsHasTitle(By.id("org.wikipedia:id/page_list_item_title"), "Java")

    }

    @Test
    fun testSaveArticleTiMyList() {
        val search = SearchPageObject(driver)

        search.initSearchInput()
        search.typeSearchLine("Java")
        search.clickByArticleWithSubstring("Object-oriented programming language")
        val article = ArticlePageObject(driver)
        article.waitForTitleOfElement()
        val nameOfFolder = "Learning programming"
        article.addArticleToMyList(nameOfFolder)
        article.closeArticle()
        val navigation = NavigationUI(driver)
        navigation.clickMyLists()
        val myList = MyListsPageObject(driver)
        myList.openFolderByName(nameOfFolder)
        val articleTitle = article.getArticleTitle()
        myList.swipeByArticleToDelete(articleTitle)
    }

    @Test
    fun testAmountOfNotEmptySearch() {
        val action = MainPageObject(driver)
        val request = "Linkin Park discography"
        val locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"


        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 5)
        action.waitElement(By.xpath(locator), "Cannot find request $request", 15)
        val amountOfElements = action.getAmountOfElements(By.xpath(locator))

        Assert.assertTrue("There're results", amountOfElements > 0)
    }

    @Test
    fun testAmountOfEmptySearch() {
        val action = MainPageObject(driver)
        val request = "fejerjhererw"
        val locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']"
        val emptyResultsLabel = "//*[@text='No results found']"

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can find search input by $request", 5)
        action.getNotElement(By.xpath(emptyResultsLabel), "Found results by request $request")
    }

    @Test
    fun testChangeScreenOrientationOnSearchResults() {
        val action = MainPageObject(driver)
        val request = "Java"

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 5)
        action.click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language'", 15)

        val titleBeforeRotation = action.getAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text", "Cannot find title of article", 15)
        driver!!.rotate(ScreenOrientation.LANDSCAPE)

        val titleAfterRotation = action.getAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text", "Cannot find title of article", 15)

        Assert.assertEquals("Article title have been change", titleBeforeRotation, titleAfterRotation)

        driver!!.rotate(ScreenOrientation.PORTRAIT)

        val titleAfterSecondRotation = action.getAttribute(By.id("org.wikipedia:id/view_page_title_text"),
                "text", "Cannot find title of article", 15)

        Assert.assertEquals("Article title have been change", titleBeforeRotation, titleAfterSecondRotation)

    }

    @Test
    fun testCheckSearchArticleInBackgraund() {
        val action = MainPageObject(driver)
        val request = "Java"
        val article = "Object-oriented programming language"

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 5)
        action.waitElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='$article']"),
                "Can't find the element", 5)
        driver!!.runAppInBackground(2)
        action.waitElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='$article']"),
                "Can't find article after return from background", 5)
    }

    @Test
    fun testSaveTwoArticleTiMyList() {
        val action = MainPageObject(driver)
        val request = "Java"
        val nameOfList = "Java (programming language)"

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 10)
        action.resultsHasTitle(By.id("org.wikipedia:id/page_list_item_title"), request)
        action.click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find the element", 5)
        action.click(By.xpath("//android.widget.ImageView[@content-desc='More options']"), "Cannot find button to open More Options", 5)
        action.waitElement(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)
        action.click(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)
        action.click(By.id("org.wikipedia:id/onboarding_button"), "Cannot find 'Got it'", 5)
        action.clear(By.id("org.wikipedia:id/text_input"), "Cannot clear a field", 5)
        action.sendKeys(By.id("org.wikipedia:id/text_input"), nameOfList, "Cannot put text", 5)
        action.click(By.xpath("//*[@text='OK']"), "Cannot find OK button", 5)

        action.click(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"), "Cannot find cross button", 5) //закрываем сохраненную статью

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 5)
        action.resultsHasTitle(By.id("org.wikipedia:id/page_list_item_title"), request)
        action.click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
                "Can't find the element", 5)
        action.click(By.xpath("//android.widget.ImageView[@content-desc='More options']"), "Cannot find button to open More Options", 5)
        action.waitElement(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)
        action.click(By.xpath("//*[@text='Add to reading list']"), "Cannot find option in More Options", 5)

        action.click(By.xpath("//*[@resource-id='org.wikipedia:id/item_title']//*[@text='$nameOfList']"),
                "Can't find list $nameOfList", 5) //клик на существующий лист

        action.click(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"), "Cannot find cross button", 5) //закрываем сохраненную статью


        action.click(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"), "Cannot find button My lists", 5)
        action.click(By.xpath("//*[@text='$nameOfList']"), "Cannot find created list", 5)
        action.swipeLeft(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"), "Cannot swipe")
        action.waitElement(By.xpath("//*[@text='Object-oriented programming language']"), "Element doesnt exist", 5)
        action.click(By.xpath("//*[@text='Object-oriented programming language']"), "Cannot action.click on element", 5)
        action.hasTitleOfResult(By.id("view_page_title_text"), "Java (programming language)", "No expected title")
    }

    @Test
    fun testAssertElementPresent() {
        val action = MainPageObject(driver)
        val request = "Java"

        action.click(By.xpath("//*[contains(@text, 'Search Wikipedia')]"), "Can't find Search Wikipedia input", 5)
        action.sendKeys(By.id("search_src_text"), request, "Can't find search input", 10)
        action.click(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find the element", 5)
        val element: WebElement = driver!!.findElement(By.xpath("org.wikipedia:id/view_page_title_text"))
        Assert.assertTrue("There's no attribute", element.isDisplayed)
    }
}