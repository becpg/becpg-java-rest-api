package fr.becpg.api.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * <p>DateExtractorHelper class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class DateExtractorHelper {

	private static final ThreadLocal<Map<String, TimeZone>> timezones = new ThreadLocal<>();
	
	private static Pattern ISO_DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$");


	private DateExtractorHelper() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	protected void finalize() {
		timezones.remove();
	}

	/**
	 * <p>parseWithSpace.</p>
	 *
	 * @param isoDate a {@link java.lang.String} object
	 * @return a {@link java.util.Date} object
	 * @throws java.text.ParseException if any.
	 */
	public static Date parseWithSpace(String isoDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("us"));
		return sdf.parse(isoDate);
	}

	/**
	 * <p>format.</p>
	 *
	 * @param isoDate a {@link java.lang.String} object
	 * @param dateFormat a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String format(String isoDate, String dateFormat) {
		Date date = parse(isoDate);

		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		return formater.format(date);
	}

	/**
	 * Parse date from ISO formatted string
	 *
	 * @param isoDate
	 *            ISO string to parse
	 * @return the date
	 */
	public static Date parse(String isoDate) {

		if (isoDate == null) {
			return null;
		}

		// parse toString date
		if (isoDate.contains(" ")) {
			try {
				return parseWithSpace(isoDate);
			} catch (ParseException e) {
				return new Date();
			}
		}

		Date parsed;

		int offset = 0;

		// extract year
		int year = Integer.parseInt(isoDate.substring(offset, offset += 4));
		if (isoDate.charAt(offset) != '-') {
			throw new IndexOutOfBoundsException("Expected - character but found " + isoDate.charAt(offset));
		}

		// extract month
		int month = Integer.parseInt(isoDate.substring(offset += 1, offset += 2));
		if (isoDate.charAt(offset) != '-') {
			throw new IndexOutOfBoundsException("Expected - character but found " + isoDate.charAt(offset));
		}

		// extract day
		int day = Integer.parseInt(isoDate.substring(offset += 1, offset += 2));
		if (isoDate.charAt(offset) != 'T') {
			throw new IndexOutOfBoundsException("Expected T character but found " + isoDate.charAt(offset));
		}

		// extract hours, minutes, seconds and milliseconds
		int hour = Integer.parseInt(isoDate.substring(offset += 1, offset += 2));
		if (isoDate.charAt(offset) != ':') {
			throw new IndexOutOfBoundsException("Expected : character but found " + isoDate.charAt(offset));
		}
		int minutes = Integer.parseInt(isoDate.substring(offset += 1, offset += 2));
		if (isoDate.charAt(offset) != ':') {
			throw new IndexOutOfBoundsException("Expected : character but found " + isoDate.charAt(offset));
		}
		int seconds = Integer.parseInt(isoDate.substring(offset += 1, offset += 2));
		if (isoDate.charAt(offset) != '.') {
			throw new IndexOutOfBoundsException("Expected . character but found " + isoDate.charAt(offset));
		}
		int milliseconds = Integer.parseInt(isoDate.substring(offset += 1, offset += 3));

		// extract timezone
		String timezoneId;
		char timezoneIndicator = isoDate.charAt(offset);
		if ((timezoneIndicator == '+') || (timezoneIndicator == '-')) {
			timezoneId = "GMT" + isoDate.substring(offset);
		} else if (timezoneIndicator == 'Z') {
			timezoneId = "GMT";
		} else {
			throw new IndexOutOfBoundsException("Invalid time zone indicator " + timezoneIndicator);
		}

		// Get the timezone
		Map<String, TimeZone> timezoneMap = timezones.get();
		if (timezoneMap == null) {
			timezoneMap = new HashMap<>(4);
			timezones.set(timezoneMap);
		}
		TimeZone timezone = timezoneMap.get(timezoneId);
		if (timezone == null) {
			timezone = TimeZone.getTimeZone(timezoneId);
			timezoneMap.put(timezoneId, timezone);
		}
		if (!timezone.getID().equals(timezoneId)) {
			throw new IndexOutOfBoundsException();
		}

		// initialize Calendar object#
		// Note: always de-serialise from Gregorian Calendar
		Calendar calendar = new GregorianCalendar(timezone);
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		calendar.set(Calendar.MILLISECOND, milliseconds);

		// extract the date
		parsed = calendar.getTime();

		return parsed;
	}

	/**
	 * <p>formatISODate.</p>
	 *
	 * @param date a {@link java.util.Date} object
	 * @return a {@link java.lang.String} object
	 */
	public static String formatISODate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		StringBuilder formatted = new StringBuilder(28);
		padInt(formatted, calendar.get(Calendar.YEAR), 4);
		formatted.append('-');
		padInt(formatted, calendar.get(Calendar.MONTH) + 1, 2);
		formatted.append('-');
		padInt(formatted, calendar.get(Calendar.DAY_OF_MONTH), 2);
		formatted.append('T');
		padInt(formatted, calendar.get(Calendar.HOUR_OF_DAY), 2);
		formatted.append(':');
		padInt(formatted, calendar.get(Calendar.MINUTE), 2);
		formatted.append(':');
		padInt(formatted, calendar.get(Calendar.SECOND), 2);
		formatted.append('.');
		padInt(formatted, calendar.get(Calendar.MILLISECOND), 3);

		TimeZone tz = calendar.getTimeZone();
		int offset = tz.getOffset(calendar.getTimeInMillis());
		if (offset != 0) {
			int hours = Math.abs((offset / (60 * 1000)) / 60);
			int minutes = Math.abs((offset / (60 * 1000)) % 60);
			formatted.append(offset < 0 ? '-' : '+');
			padInt(formatted, hours, 2);
			formatted.append(':');
			padInt(formatted, minutes, 2);
		} else {
			formatted.append('Z');
		}

		return formatted.toString();
	}

	private static void padInt(StringBuilder buffer, int value, int length) {
		String strValue = Integer.toString(value);
		for (int i = length - strValue.length(); i > 0; i--) {
			buffer.append('0');
		}
		buffer.append(strValue);
	}

	public static boolean isDate(String isoDate) {
		return ISO_DATE_PATTERN.matcher(isoDate).matches();
	}

}
