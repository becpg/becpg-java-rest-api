package fr.becpg.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fr.becpg.api.helper.DateExtractorHelper;

 class DateExtractorHelperTest {
    private final static String TEST_DATE=  "2022-06-08T22:00:00.000Z";

	/** A d:date as published by a repository older than 26.1: the instant it is stored at. */
	private final static String LEGACY_DAY = "2027-06-30T23:00:00.000Z";

	/** The same d:date as published by a 26.1 repository: a calendar day. */
	private final static String TEST_DAY = "2027-07-01";

	private final static String[] TIME_ZONES = { "UTC", "Europe/London", "Europe/Paris", "America/Chicago", "Australia/Sydney" };

	@Test
	void testExtractor() {

		Assert.assertTrue("Test isDate", DateExtractorHelper.isDate(TEST_DATE));
		Date date = DateExtractorHelper.parse(TEST_DATE);

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		Assert.assertEquals(TEST_DATE, DateExtractorHelper.formatISODate(date));
	}

	/**
	 * A d:date is published as a calendar day since 26.1. It must be read as a date, otherwise it
	 * travels down the channels as plain text and skips the date format configured on them.
	 */
	@Test
	void testDayIsADate() {
		Assert.assertTrue("A calendar day is a date", DateExtractorHelper.isDate(TEST_DAY));
		Assert.assertFalse("Plain text is not a date", DateExtractorHelper.isDate("Panda Cloudoos"));
		Assert.assertFalse("A null value is not a date", DateExtractorHelper.isDate(null));
	}

	/**
	 * The day a channel writes out is the day beCPG published, whatever zone the connector runs in.
	 */
	@Test
	void testDayKeepsItsDayInEveryTimeZone() {
		TimeZone initial = TimeZone.getDefault();
		try {
			for (String zone : TIME_ZONES) {
				TimeZone.setDefault(TimeZone.getTimeZone(zone));

				Date parsed = DateExtractorHelper.parse(TEST_DAY);

				Assert.assertEquals("Day preserved in " + zone, TEST_DAY, new SimpleDateFormat("yyyy-MM-dd").format(parsed));
			}
		} finally {
			TimeZone.setDefault(initial);
		}
	}

	/**
	 * One connector build serves the whole estate, from 4.2 to 26.1, so the instant form a
	 * repository older than 26.1 publishes a d:date in must keep being read exactly as before.
	 */
	@Test
	void testLegacyInstantStillReadsAsBefore() {
		TimeZone initial = TimeZone.getDefault();
		try {
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

			Assert.assertTrue("A legacy instant is a date", DateExtractorHelper.isDate(LEGACY_DAY));
			Assert.assertEquals("Instant unchanged", LEGACY_DAY, DateExtractorHelper.formatISODate(DateExtractorHelper.parse(LEGACY_DAY)));
		} finally {
			TimeZone.setDefault(initial);
		}
	}

}
