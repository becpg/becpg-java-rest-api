package fr.becpg.api.helper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CompressParamHelper class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class CompressParamHelper {

	private static Log logger = LogFactory.getLog(CompressParamHelper.class);
	
	private static final String BASE_64_PREFIX = "b64-";
	
	private static final Map<String,String> replacementMaps = new HashMap<>();
	
	static {
		replacementMaps.put("bcpg:", "§");
	}
	
	
	/**
	 * <p>encodeParam.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static  String encodeParam(String value) {
		if (value != null) {
			byte[] compressedData = new byte[4096];
			for(Entry<String,String> entry: replacementMaps.entrySet()) {
				value = value.replaceAll(entry.getKey(), entry.getValue());
			}
			byte[] stringAsBytes = value.getBytes();
			Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
			compresser.setInput(stringAsBytes);
			compresser.finish();
			int compressedDataLength = compresser.deflate(compressedData);
			compresser.end();
			return BASE_64_PREFIX+Base64.getEncoder().encodeToString(Arrays.copyOf(compressedData, compressedDataLength));
		}
		return value;
	}


	/**
	 * <p>decodeParam.</p>
	 *
	 * @param param a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static  String decodeParam(String param)   {
		if ((param != null) && param.startsWith(BASE_64_PREFIX) ) {
		     try {
				 Inflater decompresser = new Inflater();
				 byte[] compressedData = Base64.getDecoder().decode(param.replaceFirst(BASE_64_PREFIX, ""));
			     decompresser.setInput(compressedData, 0, compressedData.length);
			     byte[] output = new byte[100000];
			     
			     int decompressedDataLength = decompresser.inflate(output);
			     decompresser.end();
			     String ret = (new String(output, 0, decompressedDataLength, StandardCharsets.UTF_8));
			     for(Entry<String,String> entry: replacementMaps.entrySet()) {
			    	 ret = ret.replaceAll( entry.getValue(), entry.getKey());
					}
			     
		    	 return ret;
			} catch (DataFormatException e) {
				logger.error("Error decoding param: "+ param,e);
			}
		}
		return param;
	}
	
}
