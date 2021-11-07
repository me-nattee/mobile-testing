import org.junit.Assert
import org.junit.Test

class MainClassTest: MainClass() {

    @Test
    fun testGetLocalNumber() {
        val expected = 14
        val actual = getLocalNumber()


        Assert.assertTrue("The number isn't 14", actual==expected)
    }
}