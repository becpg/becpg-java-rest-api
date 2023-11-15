package fr.becpg.api;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.becpg.api.helper.DateExtractorHelper;

 class DateExtractorHelperTest {
    private final static String TEST_DATE=  "2022-06-08T22:00:00.000Z";
	
	@Test
	void testExtractor() {
		
		Assert.assertTrue("Test isDate", DateExtractorHelper.isDate(TEST_DATE));
		Date date = DateExtractorHelper.parse(TEST_DATE);
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		Assert.assertEquals(TEST_DATE, DateExtractorHelper.formatISODate(date));
	}
	
}
