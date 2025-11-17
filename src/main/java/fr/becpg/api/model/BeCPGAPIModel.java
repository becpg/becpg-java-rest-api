package fr.becpg.api.model; 

/**
 * Defines constants for commonly used Alfresco model QNames (Types, Aspects, Properties, Associations)
 * and paths within the beCPG context.
 * Prevents hardcoding string literals throughout the application.
 */
public final class BeCPGAPIModel { 

	private BeCPGAPIModel() {
		// Private constructor to prevent instantiation of this utility class
	}

	// --- General Properties ---
	public static final String PROP_CHARACT_NAME = "bcpg:charactName";
	public static final String PROP_PARENT_LEVEL = "bcpg:parentLevel";
	public static final String PROP_LK_VALUE = "bcpg:lkvValue";
	public static final String PROP_CODE = "bcpg:code";
	public static final String PROP_ERP_CODE = "bcpg:erpCode";
	public static final String PROP_LEGAL_NAME = "bcpg:legalName";
	public static final String PROP_PLURAL_LEGAL_NAME = "bcpg:pluralLegalName";
	public static final String ASSOC_CM_CONTAINS = "cm:contains";
	public static final String PROP_CM_NAME = "cm:name";
	public static final String PROP_BEST_BEFORE_DATE = "bcpg:bestBeforeDate";
	public static final String PROP_PRODUCT_USE_BY_DATE = "qa:productUseByDate";
	public static final String PROP_USE_BY_DATE = "bcpg:useByDate";
	public static final String PROP_PERIOD_AFTER_OPENING = "bcpg:periodAfterOpening";

	// --- Specific Properties ---
	public static final String PROP_CM_USER = "cm:person";
	public static final String PROP_CM_USERNAME = "cm:userName";

	// --- Product Types ---
	public static final String TYPE_FINISHED_PRODUCT = "bcpg:finishedProduct";
	public static final String TYPE_RAW_MATERIAL = "bcpg:rawMaterial";
	public static final String TYPE_PACKAGING_MATERIAL = "bcpg:packagingMaterial";
	public static final String TYPE_RESOURCE_PRODUCT = "bcpg:resourceProduct";
	public static final String TYPE_STEP = "mpm:processStep";
	public static final String TYPE_SEMI_FINISHED_PRODUCT = "bcpg:semiFinishedProduct";
	public static final String TYPE_LOCAL_SEMI_FINISHED_PRODUCT = "bcpg:localSemiFinishedProduct";
	public static final String TYPE_PACKAGING_KIT = "bcpg:packagingKit";
	public static final String TYPE_PRODUCT_SPECIFICATION = "bcpg:productSpecification";
	public static final String TYPE_LOGISTIC_UNIT = "bcpg:logisticUnit";
	public static final String TYPE_PRODUCT_COLLECTION = "bcpg:productCollection";
	public static final String TYPE_PROJECT = "pjt:project";
	public static final String TYPE_SUPPLIER = "bcpg:supplier";
	public static final String TYPE_CLIENT = "bcpg:client";
	public static final String TYPE_SUBSIDIARY = "bcpg:subsidiary";
	public static final String TYPE_PLANT = "bcpg:plant";
	public static final String TYPE_TRADEMARK = "bcpg:trademark";
	public static final String TYPE_CERTIFICATION = "bcpg:certification";
	public static final String TYPE_APPROVAL_NUMBER = "bcpg:approvalNumber";
	public static final String TYPE_LABORATORY = "bcpg:laboratory";

	// --- Batch ---
	public static final String PROP_BATCH_STATE = "qa:batchState";
	public static final String PROP_BATCH_QTY_UNIT = "qa:batchQtyUnit";
	public static final String PROP_BATCH_QTY = "qa:batchQty";
	public static final String PROP_BATCH_ID = "qa:batchId";

	// --- Ingredient Type ---
	public static final String TYPE_ING = "bcpg:ing";
	public static final String TYPE_ING_TYPE_ITEM = "bcpg:ingTypeItem";
	
	// --- Product Type ---
	public static final String TYPE_PRODUCT = "bcpg:product";
	public static final String TYPE_PRODUCTLIST_ITEM = "bcpg:productListItem";
	
	// --- Characteristic Types ---
	public static final String TYPE_ALLERGEN = "bcpg:allergen";
	public static final String TYPE_COST = "bcpg:cost";
	public static final String TYPE_LCA = "bcpg:lca";
	public static final String TYPE_MICROBIO = "bcpg:microbio";
	public static final String TYPE_GEO_ORIGIN = "bcpg:geoOrigin";
	public static final String TYPE_BIO_ORIGIN = "bcpg:bioOrigin";
	public static final String TYPE_NUT = "bcpg:nut";
	public static final String TYPE_ORGANO = "bcpg:organo";
	public static final String TYPE_PHYSICO_CHEM = "bcpg:physicoChem";
	public static final String TYPE_LABEL_CLAIM = "bcpg:labelClaim";
	public static final String TYPE_STORAGE_CONDITIONS = "bcpg:storageConditions";
	public static final String TYPE_PRECAUTION_OF_USE = "bcpg:precautionOfUse";
	public static final String TYPE_CUSTOMS_CODE = "bcpg:customsCode";
	public static final String TYPE_TOX = "bcpg:tox";
	public static final String TYPE_TOX_ING = "bcpg:toxIng";
	
	// --- Regulatory Types ---
	public static final String TYPE_REGULATORY_USAGE = "bcpg:regulatoryUsage";
	
	// --- DataList Types ---
	public static final String TYPE_ALLERGENLIST = "bcpg:allergenList";
	public static final String TYPE_PRODUCTLIST = "bcpg:productList";
	public static final String TYPE_COMPOLIST = "bcpg:compoList";
	public static final String TYPE_PACKAGINGLIST = "bcpg:packagingList";
	public static final String TYPE_COSTLIST = "bcpg:costList";
	public static final String TYPE_PRICELIST = "bcpg:priceList";
	public static final String TYPE_INGLIST = "bcpg:ingList";
	public static final String TYPE_NUTLIST = "bcpg:nutList";
	public static final String TYPE_ORGANOLIST = "bcpg:organoList";
	public static final String TYPE_INGLABELINGLIST = "bcpg:ingLabelingList";
	public static final String TYPE_LABELINGRULELIST = "bcpg:labelingRuleList";
	public static final String TYPE_MICROBIOLIST = "bcpg:microbioList";
	public static final String TYPE_PHYSICOCHEMLIST = "bcpg:physicoChemList";
	public static final String TYPE_FORBIDDENINGLIST = "bcpg:forbiddenIngList";
	public static final String TYPE_REGULATORYLIST = "bcpg:regulatoryList";
	public static final String TYPE_REQCTRLLIST = "bcpg:reqCtrlList";
	public static final String TYPE_SPEC_COMPATIBILITY_LIST = "bcpg:productSpecCompatibilityList";
	public static final String TYPE_DYNAMICCHARACTLIST = "bcpg:dynamicCharactList";
	public static final String TYPE_CONTACTLIST = "bcpg:contactList";
	public static final String TYPE_LABELCLAIMLIST = "bcpg:labelClaimList";
	public static final String TYPE_SVHCLIST = "bcpg:svhcList";
	public static final String TYPE_LCALIST = "bcpg:lcaList";
	public static final String TYPE_INGREGULATORYLIST = "bcpg:ingRegulatoryList";
	public static final String TYPE_TASKLIST = "pjt:taskList";
	public static final String TYPE_BUDGETLIST = "pjt:budgetList";
	public static final String TYPE_DELIVERABLELIST = "pjt:deliverableList";
	public static final String TYPE_INVOICELIST = "pjt:invoiceList";
	public static final String TYPE_LOGTIMELIST = "pjt:logTimeList";
	public static final String TYPE_SCORELIST = "pjt:scoreList";
	public static final String TYPE_PROCESSLIST = "mpm:processList";
	public static final String TYPE_STOCKLIST = "qa:stockList";

	public static final String TYPE_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteria";

	// --- Entity Template ---
	public static final String ASPECT_ENTITY_TPL = "bcpg:entityTplAspect";
	public static final String PROP_TPL_ENABLED = "bcpg:entityTplEnabled";
	public static final String PROP_TPL_IS_DEFAULT = "bcpg:entityTplIsDefault";
	public static final String ASSOC_ENTITY_TPL_REF = "bcpg:entityTplRef";

	public static final String ASSOC_DOCUMENT_TYPE_REF = "bcpg:documentTypeRef";

	public static final String PROP_EXTRA_PROMPT = "bcpg:aiExtraPrompt";
	public static final String PROP_SUGGESTED_FIELDS = "bcpg:aiSuggestedFields";
	public static final String PROP_VALIDATION_CRITERIA = "bcpg:aiValidationCriteria";
	public static final String PROP_VALIDATION_STATE = "bcpg:aiValidationState";
	public static final String PROP_VALIDATION_HINTS = "bcpg:aiValidationHints";
	public static final String PROP_VALIDATION_DATE = "bcpg:aiValidationDate";

	public static final String PROP_CM_MODIFIED = "cm:modified";

	// --- Composition DataList ---
	public static final String ASSOC_COMPOLIST_PRODUCT = "bcpg:compoListProduct"; 
	public static final String PROP_COMPOLIST_QTY_SUB_FORMULA = "bcpg:compoListQtySubFormula"; 
	public static final String PROP_COMPOLIST_UNIT = "bcpg:compoListUnit"; 
	public static final String PROP_COMPOLIST_YIELD_PERC = "bcpg:compoListYieldPerc";
	public static final String PROP_COMPOLIST_OVERRUN_PERC = "bcpg:compoListOverrunPerc";
	public static final String PROP_COMPOLIST_VOLUME = "bcpg:compoListVolume";
	public static final String PROP_COMPOLIST_LOSS_PERC = "bcpg:compoListLossPerc";

	// --- Packaging DataList ---
	public static final String ASSOC_PACKAGINGLIST_PRODUCT = "bcpg:packagingListProduct";
	public static final String PROP_PACKAGINGLIST_QTY = "bcpg:packagingListQty";
	public static final String PROP_PACKAGINGLIST_UNIT = "bcpg:packagingListUnit";
	public static final String PROP_PACKAGINGLIST_LOSS_PERC = "bcpg:packagingListLossPerc";
	public static final String PROP_PACKAGINGLIST_PKG_LEVEL = "bcpg:packagingListPkgLevel";

	// --- Process DataList ---
	public static final String PROP_PL_RESOURCE = "mpm:plResource";
	public static final String PROP_PL_QTY = "mpm:plQty";
	public static final String PROP_PL_UNIT = "mpm:plUnit";
	public static final String PROP_PL_STEP = "mpm:plStep";
	public static final String PROP_PL_QTY_RESOURCE = "mpm:plQtyResource";
	public static final String PROP_PL_RATE_RESOURCE = "mpm:plRateResource";
	public static final String PROP_PROCESS_STEP_DESCRIPTION = "bcpg:processStepDescription";
	public static final String PROP_PROCESS_STEP_ORDER = "bcpg:processStepOrder";

	// --- Project Task Datalist ---
	public static final String PROP_TASKLIST_RESOURCES = "pjt:tlResources";
	public static final String PROP_TASKLIST_STATE = "pjt:tlState";
	public static final String PROP_TASKLIST_TASK_NAME = "pjt:tlTaskName";
	public static final String PROP_TASKLIST_TASK_DESCRIPTION = "pjt:tlTaskDescription";
	public static final String PROP_TASKLIST_IS_MILESTONE = "pjt:tlIsMilestone";
	public static final String PROP_TASKLIST_DURATION = "pjt:tlDuration";
	public static final String PROP_TASKLIST_START = "pjt:tlStart";
	public static final String PROP_TASKLIST_END = "pjt:tlEnd";
	public static final String PROP_TASKLIST_PREV_TASKS = "pjt:tlPrevTasks";
	public static final String PROP_TASKLIST_REFUSED_TASK_REF = "pjt:tlRefusedTaskRef";

	// --- Project score Datalist ---
	public static final String ASSOC_SL_SCORE_CRITERION = "pjt:slScoreCriterion";
	public static final String PROP_SL_WEIGHT = "pjt:slWeight";
	public static final String PROP_SL_SCORE = "pjt:slScore";
	public static final String PROP_SL_SCREENING = "pjt:slScreening";
	public static final String PROP_SL_SCORE_RANGE = "pjt:slScoreRange";

	// --- Project logtime Datalist ---
	public static final String ASSOC_LTL_TASK = "pjt:ltlTask";
	public static final String PROP_LTL_DATE = "pjt:ltlDate";
	public static final String PROP_LTL_TYPE = "pjt:ltlType";
	public static final String PROP_LTL_COMMENT = "pjt:ltlComment";
	public static final String PROP_LTL_TIME = "pjt:ltlTime";

	// --- Project invoice Datalist ---
	public static final String ASSOC_IL_BUDGET_REF = "pjt:ilBudgetRef";
	public static final String ASSOC_IL_TASK_REF = "pjt:ilTaskRef";
	public static final String PROP_IL_INVOICE_NAME = "pjt:ilInvoiceName";
	public static final String PROP_IL_DATE = "pjt:ilDate";
	public static final String PROP_INVOICE = "pjt:invoice";

	// --- Project deliverables Datalist ---
	public static final String ASSOC_DL_CONTENT = "pjt:dlContent";
	public static final String ASSOC_DL_TASKS = "pjt:dlTask";
	public static final String PROP_DL_URL = "pjt:dlUrl";
	public static final String PROP_DL_SCRIPT_EXEC_ORDER = "pjt:dlScriptExecOrder";
	public static final String PROP_DL_STATE = "pjt:dlState";
	public static final String PROP_DL_DESCRIPTION = "pjt:dlDescription";

	// --- Project budgeting Datalist ---
	public static final String PROP_BUDGETLIST_BUDGETED_EXPENSE = "pjt:blBudgetedExpense";
	public static final String PROP_BUDGETLIST_BUDGETED_INVOICE = "pjt:blBudgetedInvoice";
	public static final String PROP_BUDGETLIST_ITEM = "pjt:blItem";
	public static final String PROP_BUDGETLIST_PROFIT = "pjt:blProfit";

	// --- Organoleptic DataList ---
	public static final String PROP_ORGANO_LIST_VALUE = "bcpg:organoListValue";
	public static final String ASSOC_ORGANO_LIST_ORGANO = "bcpg:organoListOrgano";

	// --- Nutrition DataList ---
	public static final String PROP_NUT_LIST_VALUE = "bcpg:nutListValue";
	public static final String ASSOC_NUT_LIST_NUT = "bcpg:nutListNut";

	public static final String PROP_MICROBIO_LIST_VALUE = "bcpg:mblValue";
	public static final String ASSOC_MICROBIO_LIST_MICROBIO = "bcpg:mblMicrobio";
	public static final String PROP_MICROBIO_UNIT = "bcpg:mblUnit";

	// --- Allergen DataList ---
	public static final String ASSOC_ALLERGEN = "bcpg:allergenListAllergen";
	public static final String PROP_ALLERGEN_LIST_VOLUNTARY = "bcpg:allergenListVoluntary";
	public static final String PROP_ALLERGEN_LIST_INVOLUNTARY = "bcpg:allergenListInVoluntary";
	public static final String PROP_ALLERGEN_LIST_ON_LINE = "bcpg:allergenListOnLine";
	public static final String PROP_ALLERGEN_LIST_ON_SITE = "bcpg:allergenListOnSite";
	public static final String PROP_ALLERGEN_LIST_IS_CLEANED = "bcpg:allergenListIsCleaned";

	public static final String PROP_LCL_CLAIM_VALUE = "bcpg:lclClaimValue";
	public static final String ASSOC_LCL_LABEL_CLAIM = "bcpg:lclLabelClaim";

	// --- Ingredient DataList (Detailed) ---
	public static final String ASSOC_ING_LIST_ING = "bcpg:ingListIng";
	public static final String PROP_ING_LIST_QTY_PERC = "bcpg:ingListQtyPerc";
	public static final String PROP_ING_TYPE_V2 = "bcpg:ingTypeV2";
	
	// --- Regulatory DataLists ---
	
	// --- Regulatory Properties ---
	public static final String PROP_REGULATORY_CODE = "bcpg:regulatoryCode";
	public static final String PROP_REGULATORY_MODE = "bcpg:regulatoryMode";
	public static final String PROP_REGULATORY_STATE = "bcpg:regulatoryState";
	public static final String PROP_REGULATORY_MODULE = "bcpg:regulatoryModule";
	public static final String PROP_REGULATORY_ID = "bcpg:regulatoryId";
	public static final String PROP_REGULATORY_USAGE_REF = "bcpg:regulatoryUsageRef";
	public static final String PROP_REGULATORY_COUNTRIES = "bcpg:regulatoryCountries";
	public static final String PROP_REGULATORY_RECIPE_ID = "bcpg:regulatoryRecipeId";
	public static final String PROP_REGULATORY_COMMENT = "bcpg:regulatoryComment";
	
	// --- Ingredient Properties ---
	public static final String PROP_CAS_NUMBER = "bcpg:casNumber";
	public static final String PROP_CE_NUMBER = "bcpg:ceNumber";
	public static final String PROP_GEO_ORIGIN_ISO_CODE = "bcpg:geoOriginISOCode";
	
	// --- Forbidden Ingredient List Properties ---
	public static final String PROP_FIL_QTY_PERC_MAXI = "bcpg:filQtyPercMaxi";
	public static final String PROP_FIL_QTY_PERC_MAXI_UNIT = "bcpg:filQtyPercMaxiUnit";
	public static final String PROP_FIL_INGS = "bcpg:filIngs";
	public static final String PROP_FIL_REQ_MESSAGE = "bcpg:filReqMessage";
	public static final String PROP_FIL_REQ_TYPE = "bcpg:filReqType";
	public static final String PROP_FIL_DATA_TYPE = "bcpg:filDataType";
	public static final String PROP_FIL_SOURCES_V2 = "bcpg:filSourcesV2";
	public static final String PROP_FIL_REGULATORY_CODE = "bcpg:filRegulatoryCode";
	
	// --- Ingredient Regulatory List Properties ---
	public static final String PROP_IRL_ING = "bcpg:irlIng";
	public static final String PROP_IRL_USAGES = "bcpg:irlUsages";
	public static final String PROP_IRL_RESULT_INDICATOR = "bcpg:irlResultIndicator";
	public static final String PROP_IRL_RESTRICTION_LEVELS = "bcpg:irlRestrictionLevels";
	public static final String PROP_IRL_CITATION = "bcpg:irlCitation";
	public static final String PROP_IRL_PRECAUTIONS = "bcpg:irlPrecautions";
	public static final String PROP_IRL_SOURCES = "bcpg:irlSources";
	
	// --- Regulatory Requirement Types ---
	public static final String REQ_TYPE_FORBIDDEN = "Forbidden";
	public static final String REQ_TYPE_TOLERATED = "Tolerated";

	// --- Physico-Chemical DataList ---
	public static final String PROP_PCL_VALUE = "bcpg:pclValue";
	public static final String ASSOC_PCL_PHYSICO_CHEM = "bcpg:pclPhysicoChem";

	// --- Cost List ---
	public static final String ASSOC_COST_LIST_COST = "bcpg:costListCost";
	public static final String PROP_COSTLIST_VALUE_PER_PRODUCT = "bcpg:costListValuePerProduct";
	public static final String PROP_COSTLIST_VALUE = "bcpg:costListValue";
	public static final String PROP_COSTLIST_UNIT = "bcpg:costListUnit";

	// --- Requirements List ---
	public static final String PROP_RCL_REQ_MESSAGE = "bcpg:rclReqMessage";
	public static final String PROP_RCL_REQ_TYPE = "bcpg:rclReqType";
	public static final String PROP_RCL_SOURCES_V2 = "bcpg:rclSourcesV2";
	public static final String ASSOC_RCL_CHARACT = "bcpg:rclCharact";
	public static final String PROP_RCL_REQ_DATA_TYPE = "bcpg:rclDataType";
	public static final String DATA_TYPE_SPECIFICATION = "Specification";

	// --- Additional Missing Properties from PLMModel ---
	
	// Product List
	public static final String ASSOC_PRODUCTLIST_PRODUCT = "bcpg:productListProduct";
	
	// Composition List Extended
	public static final String PROP_COMPOLIST_QTY = "bcpg:compoListQty";
	public static final String PROP_COMPOLIST_QTY_FOR_PRODUCT = "bcpg:compoListQtyForProduct";
	public static final String PROP_COMPOLIST_QTY_PERC_FOR_PRODUCT = "bcpg:compoListQtyPercForProduct";
	public static final String PROP_COMPOLIST_QTY_PERC_FOR_SF = "bcpg:compoListQtyPercForSF";
	public static final String PROP_COMPOLIST_QTY_AFTER_PROCESS = "bcpg:compoListQtyAfterProcess";
	public static final String PROP_COMPOLIST_DECL_TYPE = "bcpg:compoListDeclType";
	
	// Packaging List Extended
	public static final String PROP_PACKAGINGLIST_QTY_FOR_PRODUCT = "bcpg:packagingListQtyForProduct";
	public static final String PROP_PACKAGINGLIST_QTY_PERC_FOR_PRODUCT = "bcpg:packagingListQtyPercForProduct";
	public static final String PROP_PACKAGINGLIST_QTY_PERC_FOR_SF = "bcpg:packagingListQtyPercForSF";
	public static final String PROP_PACKAGINGLIST_ISMASTER = "bcpg:packagingListIsMaster";
	
	// Cost List Extended
	public static final String PROP_COSTLIST_MAXI = "bcpg:costListMaxi";
	
	// Price List
	public static final String ASSOC_PRICELIST_COST = "bcpg:priceListCost";
	public static final String PROP_PRICELIST_PREF_RANK = "bcpg:priceListPrefRank";
	public static final String PROP_PRICELIST_VALUE = "bcpg:priceListValue";
	public static final String PROP_PRICELIST_UNIT = "bcpg:priceListUnit";
	public static final String PROP_PRICELIST_PURCHASE_QTY = "bcpg:priceListPurchaseQty";
	public static final String PROP_PRICELIST_PURCHASE_UNIT = "bcpg:priceListPurchaseUnit";
	
	// Ingredient List Extended
	public static final String PROP_INGLIST_QTY_PERCWITHYIELD = "bcpg:ingListQtyPercWithYield";
	public static final String PROP_INGLIST_QTY_PERCWITHSECONDARYYIELD = "bcpg:ingListQtyPercWithSecondaryYield";
	public static final String PROP_INGLIST_IS_GMO = "bcpg:ingListIsGMO";
	public static final String PROP_INGLIST_IS_IONIZED = "bcpg:ingListIsIonized";
	public static final String PROP_INGLIST_IS_PROCESSING_AID = "bcpg:ingListIsProcessingAid";
	public static final String PROP_INGLIST_DECL_TYPE = "bcpg:ingListDeclType";
	public static final String ASSOC_INGLIST_GEO_ORIGIN = "bcpg:ingListGeoOrigin";
	public static final String ASSOC_INGLIST_BIO_ORIGIN = "bcpg:ingListBioOrigin";
	
	// Nutrition List Extended
	public static final String PROP_NUTLIST_VALUE_PER_SERVING = "bcpg:nutListValuePerServing";
	public static final String PROP_NUTLIST_MEASUREMENTPRECISION = "bcpg:nutListMeasurementPrecision";
	public static final String PROP_NUTLIST_FORMULATED_VALUE_PER_SERVING = "bcpg:nutListFormulatedValuePerServing";
	public static final String PROP_NUTLIST_FORMULATED_VALUE = "bcpg:nutListFormulatedValue";
	public static final String PROP_NUTLIST_UNIT_PREPARED = "bcpg:nutListUnitPrepared";
	public static final String PROP_NUTLIST_MINI = "bcpg:nutListMini";
	public static final String PROP_NUTLIST_VALUE_PREPARED = "bcpg:nutListValuePrepared";
	public static final String PROP_NUTLIST_FORMULATED_PREPARED = "bcpg:nutListFormulatedValuePrepared";
	public static final String PROP_NUTLIST_FORMULATED_MINI = "bcpg:nutListFormulatedMini";
	public static final String PROP_NUTLIST_FORMULATED_MAXI = "bcpg:nutListFormulatedMaxi";
	public static final String PROP_NUTLIST_MAXI = "bcpg:nutListMaxi";
	public static final String PROP_NUTLIST_GROUP = "bcpg:nutListGroup";
	public static final String PROP_NUTLIST_ROUNDED_VALUE = "bcpg:nutListRoundedValue";
	public static final String PROP_NUTLIST_PREPARED_ROUNDED_VALUE = "bcpg:nutListRoundedValuePrepared";
	public static final String PROP_NUTLIST_METHOD = "bcpg:nutListMethod";
	public static final String ASPECT_NUTLIST_PREPARED = "bcpg:nutListPreparedAspect";
	
	// Labeling Rule List
	public static final String PROP_LABELINGRULELIST_LABEL = "bcpg:lrLabel";
	public static final String PROP_LABELINGRULELIST_TYPE = "bcpg:lrType";
	public static final String PROP_LABELINGRULELIST_SYNC_STATE = "bcpg:lrSyncState";
	public static final String ASSOC_ILL_GRP = "bcpg:illGrp";
	public static final String PROP_ILL_VALUE = "bcpg:illValue";
	public static final String PROP_ILL_MANUAL_VALUE = "bcpg:illManualValue";
	public static final String PROP_ILL_LOG_VALUE = "bcpg:illLogValue";
	
	// Microbio List Extended
	public static final String PROP_MICROBIOLIST_TYPE = "bcpg:mblType";
	public static final String PROP_MICROBIOLIST_MAXI = "bcpg:mblMaxi";
	public static final String PROP_MICROBIOLIST_TEXT_CRITERIA = "bcpg:mblTextCriteria";
	public static final String PROP_MICROBIO_TYPE = "bcpg:microbioType";
	
	// Physico-Chemical Extended
	public static final String PROP_PHYSICOCHEMLIST_UNIT = "bcpg:pclUnit";
	public static final String PROP_PHYSICOCHEMLIST_TYPE = "bcpg:pclType";
	public static final String PROP_PHYSICOCHEMLIST_MINI = "bcpg:pclMini";
	public static final String PROP_PHYSICOCHEMLIST_MAXI = "bcpg:pclMaxi";
	public static final String PROP_PHYSICO_CHEM_UNIT = "bcpg:physicoChemUnit";
	public static final String PROP_PHYSICO_CHEM_TYPE = "bcpg:physicoChemType";
	public static final String PROP_PHYSICO_CHEM_FORMULATED = "bcpg:physicoChemFormulated";
	public static final String PROP_PHYSICO_CHEM_FORMULATED_FROM_VOL = "bcpg:physicoChemFormulatedFromVol";
	public static final String PROP_PHYSICO_CHEM_FORMULA = "bcpg:physicoChemFormula";
	public static final String PROP_PHYSICO_CHEM_CODE = "bcpg:physicoChemCode";
	
	// Forbidden Ingredient Extended
	public static final String PROP_FIL_IS_GMO = "bcpg:filIsGMO";
	public static final String PROP_FIL_IS_IONIZED = "bcpg:filIsIonized";
	public static final String ASSOC_FIL_GEO_ORIGINS = "bcpg:filGeoOrigins";
	public static final String ASSOC_FIL_BIO_ORIGINS = "bcpg:filBioOrigins";
	
	// Spec Compatibility
	public static final String PROP_SPEC_COMPATIBILITY_JOB_ON = "bcpg:specCompatibilityJobOn";
	public static final String PROP_SPEC_COMPATIBILITY_TEST_MODE = "bcpg:specCompatibilityTestMode";
	public static final String PROP_SPEC_COMPATIBILITY_LOG = "bcpg:specCompatibilityLog";
	public static final String ASSOC_PSCL_SOURCE_ITEM = "bcpg:psclSourceItem";
	
	// Dynamic Charact
	public static final String PROP_DYNAMICCHARACT_TITLE = "bcpg:dynamicCharactTitle";
	public static final String PROP_DYNAMICCHARACT_FORMULA = "bcpg:dynamicCharactFormula";
	public static final String PROP_DYNAMICCHARACT_VALUE = "bcpg:dynamicCharactValue";
	public static final String PROP_DYNAMICCHARACT_GROUP_COLOR = "bcpg:dynamicCharactGroupColor";
	public static final String PROP_DYNAMICCHARACT_COLUMN = "bcpg:dynamicCharactColumn";
	public static final String PROP_DYNAMICCHARACT_SYNCHRONIZABLE_STATE = "bcpg:dynamicCharactSynchronisableState";
	
	// Contact List
	public static final String PROP_CONTACT_LIST_FIRST_NAME = "bcpg:contactListFirstName";
	public static final String PROP_CONTACT_LIST_LAST_NAME = "bcpg:contactListLastName";
	public static final String PROP_CONTACT_LIST_EMAIL = "bcpg:contactListEmail";
	
	// Label Claim Extended
	public static final String PROP_LCL_TYPE = "bcpg:lclType";
	public static final String PROP_LCL_FORMULAERROR = "bcpg:lclFormulaErrorLog";
	public static final String ASSOC_LCL_MISSING_LABELCLAIMS = "bcpg:lclMissingLabelClaims";
	public static final String PROP_LABEL_CLAIM_CODE = "bcpg:labelClaimCode";
	public static final String PROP_LABEL_CLAIM_TYPE = "bcpg:labelClaimType";
	public static final String PROP_LABEL_CLAIM_FORMULA = "bcpg:labelClaimFormula";
	public static final String PROP_CLAIM_REGULATORY_THRESHOLD = "bcpg:labelClaimRegulatoryThreshold";
	
	// SVHC List
	public static final String PROP_SVHCLIST_QTY_PERC = "bcpg:svhcListQtyPerc";
	public static final String PROP_IS_SVHC = "bcpg:isSubstanceOfVeryHighConcern";
	public static final String ASSOC_SVHCLIST_ING = "bcpg:svhcListIng";
	public static final String PROP_SVHC_REASONS_FOR_INCLUSION = "bcpg:svhcReasonsForInclusion";
	
	// Allergen Extended
	public static final String PROP_ALLERGEN_CODE = "bcpg:allergenCode";
	public static final String PROP_ALLERGEN_TYPE = "bcpg:allergenType";
	public static final String PROP_ALLERGEN_REGULATORY_THRESHOLD = "bcpg:allergenRegulatoryThreshold";
	public static final String PROP_ALLERGEN_INVOL_REGULATORY_THRESHOLD = "bcpg:allergenInVoluntaryRegulatoryThreshold";
	public static final String ASSOC_ALLERGENLIST_VOLUNTARY_SOURCES = "bcpg:allergenListVolSources";
	public static final String ASSOC_ALLERGENLIST_INVOLUNTARY_SOURCES = "bcpg:allergenListInVolSources";
	public static final String PROP_ALLERGEN_DECISION_TREE = "bcpg:allergenListDecisionTree";
	public static final String ASSOC_ALLERGENSUBSETS = "bcpg:allergenSubset";
	public static final String ASPECT_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteriaAspect";
	public static final String ASSOC_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteriaRef";
	
	// Cost
	public static final String PROP_COST_FORMULA = "bcpg:costFormula";
	public static final String PROP_COSTCURRENCY = "bcpg:costCurrency";
	public static final String PROP_COSTFIXED = "bcpg:costFixed";
	public static final String PROP_COSTTYPE = "bcpg:costType";
	
	// LCA
	public static final String PROP_LCA_FORMULA = "bcpg:lcaFormula";
	public static final String PROP_LCAUNIT = "bcpg:lcaUnit";
	public static final String PROP_LCAFIXED = "bcpg:lcaFixed";
	public static final String PROP_LCATYPE = "bcpg:lcaType";
	public static final String PROP_LCA_NORMALIZATION = "bcpg:lcaNormalization";
	public static final String PROP_LCA_PONDERATION = "bcpg:lcaPonderation";
	public static final String PROP_LCA_CODE = "bcpg:lcaCode";
	public static final String ASSOC_LCALIST_LCA = "bcpg:lcaListLca";
	public static final String ASSOC_LCALIST_COMPONENT = "bcpg:lcaListComponent";
	public static final String PROP_LCALIST_UNIT = "bcpg:lcaListUnit";
	public static final String PROP_LCALIST_VALUE = "bcpg:lcaListValue";
	public static final String PROP_LCALIST_PREVIOUS_VALUE = "bcpg:lcaListPreviousValue";
	public static final String PROP_LCALIST_FUTURE_VALUE = "bcpg:lcaListFutureValue";

	// Ingredient Extended
	public static final String PROP_ING_CEECODE = "bcpg:ingCEECode";
	public static final String PROP_ING_CASCODE = "bcpg:ingCASCode";
	public static final String ASPECT_ING_TYPE = "bcpg:ingTypeAspect";
	public static final String PROP_ING_TYPE_DEC_THRESHOLD = "bcpg:ingTypeDecThreshold";
	
	// Nut
	public static final String PROP_NUTGROUP = "bcpg:nutGroup";
	public static final String PROP_NUTTYPE = "bcpg:nutType";
	public static final String PROP_NUTUNIT = "bcpg:nutUnit";
	public static final String PROP_NUTGDA = "bcpg:nutGDA";
	public static final String PROP_NUTUL = "bcpg:nutUL";
	public static final String PROP_NUT_FORMULA = "bcpg:nutFormula";
	
	// Supplier/Client Aspects
	public static final String ASPECT_SUPPLIERS = "bcpg:suppliersAspect";
	public static final String ASSOC_SUPPLIERS = "bcpg:suppliers";
	public static final String ASSOC_SUPPLIER_PLANTS = "bcpg:supplierPlants";
	public static final String PROP_SUPPLIER_STATE = "bcpg:supplierState";
	public static final String PROP_EXTERNAL_ACCESS_GROUP = "bcpg:externalAccessGroup";
	public static final String ASPECT_SUPPLIERS_ACCOUNTREF = "bcpg:supplierAccountRefAspect";
	public static final String ASSOC_SUPPLIER_ACCOUNTS = "bcpg:supplierAccountRef";
	public static final String ASPECT_CLIENTS = "bcpg:clientsAspect";
	public static final String ASSOC_CLIENTS = "bcpg:clients";
	public static final String PROP_CLIENT_STATE = "bcpg:clientState";
	
	// Product Aspect
	public static final String ASPECT_PRODUCT = "bcpg:productAspect";
	public static final String PROP_PRODUCT_HIERARCHY1 = "bcpg:productHierarchy1";
	public static final String PROP_PRODUCT_HIERARCHY2 = "bcpg:productHierarchy2";
	public static final String PROP_PRODUCT_STATE = "bcpg:productState";
	public static final String PROP_PRODUCT_UNIT = "bcpg:productUnit";
	public static final String PROP_PRODUCT_QTY = "bcpg:productQty";
	public static final String PROP_PRODUCT_DENSITY = "bcpg:productDensity";
	public static final String PROP_PRODUCT_COMMENTS = "bcpg:productComments";
	public static final String PROP_PRODUCT_SCORE = "bcpg:productScores";
	public static final String PROP_PRODUCT_DROP_PACKAGING_OF_COMPONENTS = "bcpg:dropPackagingOfComponents";
	public static final String PROP_PRODUCT_SERVING_SIZE = "bcpg:servingSize";
	public static final String PROP_PRODUCT_SERVING_SIZE_BY_COUNTRY = "bcpg:servingSizeByCountry";
	public static final String PROP_PRODUCT_SERVING_SIZE_UNIT = "bcpg:servingSizeUnit";
	public static final String PROP_NUTRIENT_PREPARED_UNIT = "bcpg:nutrientPreparedUnit";
	public static final String PROP_PRODUCT_NET_VOLUME = "bcpg:netVolume";
	public static final String PROP_PRODUCT_NET_WEIGHT = "bcpg:netWeight";
	public static final String PROP_PRODUCT_COMPO_QTY_USED = "bcpg:productCompoQtyUsed";
	public static final String PROP_PRODUCT_COMPO_VOLUME_USED = "bcpg:productCompoVolumeUsed";
	
	// Transformation Aspect
	public static final String ASPECT_TRANSFORMATION = "bcpg:transformationAspect";
	public static final String ASSOC_PRODUCT_SPECIFICATIONS = "bcpg:productSpecifications";
	
	// EAN Aspect
	public static final String ASPECT_EAN = "bcpg:eanAspect";
	public static final String PROP_EAN_CODE = "bcpg:eanCode";
	
	// Profitability Aspect
	public static final String ASPECT_PROFITABILITY = "bcpg:profitabilityAspect";
	public static final String PROP_UNIT_PRICE = "bcpg:unitPrice";
	public static final String PROP_BREAK_EVEN = "bcpg:breakEven";
	public static final String PROP_PROJECTED_QTY = "bcpg:projectedQty";
	public static final String PROP_PROFITABILITY = "bcpg:profitability";
	public static final String PROP_UNIT_TOTAL_COST = "bcpg:unitTotalCost";
	public static final String PROP_PRICE_CURRENCY = "bcpg:priceCurrency";
	
	// Manufacturing Aspect
	public static final String ASPECT_MANUFACTURING = "bcpg:manufacturingAspect";
	public static final String ASSOC_SUBSIDIARY = "bcpg:subsidiaryRef";
	public static final String ASSOC_PLANTS = "bcpg:plants";
	public static final String ASSOC_LABORATORIES = "bcpg:laboratories";
	public static final String ASSOC_TRADEMARK = "bcpg:trademarkRef";
	public static final String ASSOC_SUBSIDIARY_CERTIFICATIONS = "bcpg:subsidiaryCertifications";
	public static final String ASSOC_PLANT_CERTIFICATIONS = "bcpg:plantCertifications";
	public static final String PROP_TRADEMARK_TYPE = "bcpg:trademarkType";
	
	// Storage and Precaution
	public static final String ASSOC_STORAGE_CONDITIONS = "bcpg:storageConditionsRef";
	public static final String ASSOC_PRECAUTION_OF_USE = "bcpg:precautionOfUseRef";
	
	// Instruction Aspect
	public static final String ASPECT_INSTRUCTION = "bcpg:instructionAspect";
	public static final String PROP_INSTRUCTION = "bcpg:instruction";
	
	// Reconstitutable Aspect
	public static final String ASPECT_RECONSTITUTABLE = "bcpg:reconstitutableAspect";
	public static final String PROP_RECONSTITUTION_RATE = "bcpg:reconstitutionRate";
	public static final String PROP_RECONSTITUTION_PRIORITY = "bcpg:reconstitutionPriority";
	public static final String ASSOC_DILUENT_REF = "bcpg:diluentRef";
	public static final String ASSOC_TARGET_RECONSTITUTION_REF = "bcpg:targetReconstitutionRef";
	
	// Compare Aspect
	public static final String ASPECT_COMPARE_WITH_DYN_COLUMN = "bcpg:compareWithDynColumnAspect";
	public static final String PROP_COMPARE_WITH_DYN_COLUMN = "bcpg:compareWithDynColumn";
	
	// Nutrient Profiling Score Aspect
	public static final String ASPECT_NUTRIENT_PROFILING_SCORE = "bcpg:nutrientProfilingScoreAspect";
	public static final String PROP_NUTRIENT_PROFILING_SCORE = "bcpg:nutrientProfilingScore";
	public static final String PROP_NUTRIENT_PROFILING_CLASS = "bcpg:nutrientProfilingClass";
	public static final String PROP_NUTRIENT_PROFILE_SCORE_FORMULA = "bcpg:nutrientProfileScoreFormula";
	public static final String PROP_NUTRIENT_PROFILE_CLASS_FORMULA = "bcpg:nutrientProfileClassFormula";
	public static final String PROP_NUTRIENT_PROFILING_DETAILS = "bcpg:nutrientProfilingDetails";
	public static final String PROP_NUTRIENT_PROFILE_CATEGORY = "bcpg:nutrientProfileCategory";
	public static final String PROP_NUTRIENT_PROFILE_VERSION = "bcpg:nutrientProfileVersion";
	
	// Eco Score Aspect
	public static final String ASPECT_ECO_SCORE = "bcpg:ecoScoreAspect";
	public static final String PROP_ECO_SCORE = "bcpg:ecoScore";
	public static final String PROP_ECO_SCORE_CLASS = "bcpg:ecoScoreClass";
	public static final String PROP_ECO_SCORE_CATEGORY = "bcpg:ecoScoreCategory";
	public static final String PROP_ECO_SCORE_DETAILS = "bcpg:ecoScoreDetails";
	
	// Customs Code Aspect
	public static final String PROP_CUSTOMSCODE_CODE = "bcpg:cstsCode";
	public static final String ASPECT_CUSTOMSCODE = "bcpg:customsCodeAspect";
	public static final String ASSOC_CUSTOMSCODE = "bcpg:customsCodeRef";
	
	// Regulatory Code Aspect
	public static final String ASPECT_REGULATORY_CODE = "bcpg:regulatoryCodeAspect";
	public static final String ASPECT_REGULATORY = "bcpg:regulatoryAspect";
	public static final String PROP_REGULATORY_RESULT = "bcpg:regulatoryResult";
	
	// Tox Properties
	public static final String PROP_TOX_TYPES = "bcpg:toxTypes";
	public static final String PROP_TOX_VALUE = "bcpg:toxValue";
	public static final String PROP_TOX_ING_ING = "bcpg:toxIngIng";
	public static final String PROP_TOX_ING_TOX = "bcpg:toxIngTox";
	public static final String PROP_TOX_ING_MAX_VALUE = "bcpg:toxIngMaxValue";
	public static final String PROP_TOX_ING_SYSTEMIC_VALUE = "bcpg:toxIngSystemicValue";
	public static final String PROP_TOX_CALCULATE_SYSTEMIC = "bcpg:toxCalculateSystemic";
	public static final String PROP_TOX_CALCULATE_MAX = "bcpg:toxCalculateMax";
	public static final String PROP_ING_TOX_DATA = "bcpg:ingToxData";
	public static final String PROP_ING_TOX_SYSTEMIC_VALUES = "bcpg:ingToxSystemicValues";
	public static final String PROP_ING_TOX_MAX_VALUES = "bcpg:ingToxMaxValues";
	public static final String PROP_ING_TOX_POD_SYSTEMIC = "bcpg:ingToxPodSystemic";
	public static final String PROP_ING_TOX_DERMAL_ABSORPTION = "bcpg:ingToxDermalAbsorption";
	public static final String PROP_ING_TOX_ORAL_ABSORPTION = "bcpg:ingToxOralAbsorption";
	public static final String PROP_TOX_ABSORPTION_TYPE = "bcpg:toxAbsorptionType";
	public static final String PROP_ING_TOX_MOS_MOE = "bcpg:ingToxMosMoe";
	public static final String PROP_ING_TOX_MAX_SKIN_IRRITATION_RINSE_OFF = "bcpg:ingToxMaxSkinIrritationRinseOff";
	public static final String PROP_ING_TOX_MAX_SKIN_IRRITATION_LEAVE_ON = "bcpg:ingToxMaxSkinIrritationLeaveOn";
	public static final String PROP_ING_TOX_MAX_SENSITIZATION = "bcpg:ingToxMaxSensitization";
	public static final String PROP_ING_TOX_MAX_OCULAR_IRRITATION = "bcpg:ingToxMaxOcularIrritation";
	public static final String PROP_ING_TOX_MAX_PHOTOTOXIC = "bcpg:ingToxMaxPhototoxic";
	
	// Chemical Codes
	public static final String PROP_EC_NUMBER = "bcpg:ecNumber";
	public static final String PROP_FDA_NUMBER = "bcpg:fdaNumber";
	public static final String PROP_FEMA_NUMBER = "bcpg:femaNumber";
	public static final String PROP_FL_NUMBER = "bcpg:flNumber";
	
	// Water and Evaporable Aspects
	public static final String ASPECT_WATER = "bcpg:waterAspect";
	public static final String ASPECT_EVAPORABLE = "bcpg:evaporableAspect";
	public static final String PROP_EVAPORATED_RATE = "bcpg:evaporatedRate";
	
	// Labeling Rule Aspect
	public static final String LABELING_RULE_ASPECT = "bcpg:labelingRuleAspect";
	public static final String PROP_MODIFIED_CATALOG1 = "bcpg:modifiedCatalog1";
	public static final String PROP_MODIFIED_CATALOG2 = "bcpg:modifiedCatalog2";
	public static final String PROP_MODIFIED_CATALOG3 = "bcpg:modifiedCatalog3";
	
	// GLOP Properties
	public static final String PROP_GLOP_TARGET = "bcpg:glopTarget";
	public static final String PROP_GLOP_VALUE = "bcpg:glopValue";
	public static final String ASPECT_GLOP_PRODUCT = "bcpg:glopProductAspect";
	
	// Propagate Up Aspect
	public static final String PROP_IS_CHARACT_PROPAGATE_UP = "bcpg:isCharactPropagateUp";
	public static final String ASPECT_PROPAGATE_UP = "bcpg:propagateUpAspect";
	public static final String ASSOC_PROPAGATED_CHARACTS = "bcpg:propagatedCharacts";
	
	// Bio Origin
	public static final String PROP_BIO_ORIGIN_TYPE = "bcpg:bioOriginType";

	// --- Common Paths ---
	public static final String PATH_DOCUMENTS = "Documents";
	public static final String PATH_SUPPLIER_DOCUMENTS = "SupplierDocuments"; 
	public static final String PATH_IMAGES = "Images";
	public static final String PATH_INGS = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:Ings";
	public static final String PATH_REGULATORY_USAGES = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:RegulatoryUsages";
	public static final String PATH_GEO_ORIGINS = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:GeoOrigins";
}
