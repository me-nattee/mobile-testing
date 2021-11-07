import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.either
import org.junit.Assert
import org.junit.Test

class MainClassTest : MainClass() {

    @Test
    fun testGetLocalNumber() {
        val expected = 14
        val actual = getLocalNumber()


        Assert.assertTrue("The number isn't 14", actual == expected)
    }

    @Test
    fun testGetClassNumber() {
        val actual = getClassNumber()

        Assert.assertTrue("The number is less than 45", actual > 45)
    }

    @Test
    fun testGetClassString() {
        val actual = getClassString()

        Assert.assertThat(actual, either(containsString("hello")).or(containsString("Hello")))
        //doesn't need a message
    }
}