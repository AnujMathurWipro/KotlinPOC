package com.anuj.pockotlin

import com.anuj.pockotlin.util.Utility

import org.junit.Assert
import org.junit.Test

class UtilityTest {

    private val validUrls = arrayOf("http://www.google.com", "http://www.google.com/abc/xyz", "http://www.google.com/abc/xyz/pqr.aaa", "http://www.google.com/asdasdsadsadasd", "http://www.google.com?a=a,b=b", "http://www.google.com/dsgafsh/sdgasjh?a=a")

    private val invalidUrls = arrayOf("www.google.com", null, "", "http://", "dsdkjh sh dhs akdh sa", "http://www.hdhasg dhgsah gsh gdjahs djh", "http://www.hdhasg.com/ghg!@#$%^&*())(*&^%$#@!")
    @Test
    fun testValidUrls() {
        for (validUrl in validUrls) Assert.assertTrue("URL = $validUrl", Utility.isValidURL(validUrl))
    }

    @Test
    fun testInvalidUrls() {
        for (invalidUrl in invalidUrls) Assert.assertFalse("URL = $invalidUrl", Utility.isValidURL(invalidUrl))
    }
}
