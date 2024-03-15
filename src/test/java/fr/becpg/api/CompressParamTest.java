package fr.becpg.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.becpg.api.helper.CompressParamHelper;

/**
 * <p>CompressParamTest class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 * @since 3.2.0
 */
class CompressParamTest {

	Logger logger = LoggerFactory.getLogger(ChannelApiTest.class);

	private final static String PARAM1 = "bcpg:code,bcpg:erpCode,cm:name,cm:title,bcpg:eanCode,bcpg:legalName,cm:description,bcpg:entityTplRef"
			+ ",bcpg:productHierarchy1,bcpg:productHierarchy2,bcpg:bestBeforeDate,bcpg:certificateDate,bcpg:contractDate"
			+ ",bcpg:foodContact,bcpg:netWeightUnit,bcpg:numberOfServings,bcpg:packagingDescription,bcpg:periodAfterOpening"
			+ ",bcpg:precautionOfUseRef,bcpg:preparationTips,bcpg:priceCurrency,bcpg:productComments,bcpg:productDensity"
			+ ",bcpg:productMicrobioCriteriaRef,bcpg:productQty,bcpg:productSpecifications,bcpg:productState,bcpg:productUnit"
			+ ",bcpg:projectedQty,bcpg:servingSize,bcpg:startEffectivity,bcpg:storageConditionsRef"
			+ ",bcpg:supplierPlants,bcpg:suppliers,bcpg:suppliers|bcpg:code,bcpg:suppliers|bcpg:erpCode"
			+ ",bcpg:clients,bcpg:clients|bcpg:code,bcpg:clients|bcpg:erpCode,bcpg:unitPrice,bcpg:useByDate,gs1:sortingBonusCriteria";
	private final static String PARAM2 = "(@cm\\:created:[%s TO MAX] OR @cm\\:modified:[%s TO MAX]) AND ( TYPE:\"bcpg:product\" OR TYPE:\"bcpg:client\" OR TYPE:\"bcpg:supplier\" ) AND =bcpg\\:code:\"PF1000015754\"";

	/**
	 * <p>test.</p>
	 */
	@Test
	void test() {

		String encodedParam1 = CompressParamHelper.encodeParam(PARAM1);
		String encodedParam2 = CompressParamHelper.encodeParam(PARAM2);

		logger.info("encodedParam1 : " + encodedParam1 + "\n new length " + encodedParam1.length() + " old length " + PARAM1.length());
		logger.info("encodedParam2 : " + encodedParam2 + "\n new length " + encodedParam2.length() + " old length " + PARAM2.length());

		Assertions.assertTrue(encodedParam1.length() <= PARAM1.length());
		Assertions.assertTrue(encodedParam2.length() <= PARAM2.length());

		Assertions.assertEquals(PARAM1, CompressParamHelper.decodeParam(encodedParam1));
		Assertions.assertEquals(PARAM2, CompressParamHelper.decodeParam(encodedParam2));

	}

}
