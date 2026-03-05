package fr.becpg.api.model; 

/**
 * Defines constants for commonly used Alfresco model QNames (Types, Aspects, Properties, Associations)
 * and paths within the beCPG context.
 * Prevents hardcoding string literals throughout the application.
 *
 * @author matthieu
 */
public final class BeCPGAPIModel { 

	private BeCPGAPIModel() {
		// Private constructor to prevent instantiation of this utility class
	}

	// --- General Properties ---
	/** Constant <code>PROP_CHARACT_NAME="bcpg:charactName"</code> */
	public static final String PROP_CHARACT_NAME = "bcpg:charactName";
	/** Constant <code>PROP_PARENT_LEVEL="bcpg:parentLevel"</code> */
	public static final String PROP_PARENT_LEVEL = "bcpg:parentLevel";
	/** Constant <code>PROP_LK_VALUE="bcpg:lkvValue"</code> */
	public static final String PROP_LK_VALUE = "bcpg:lkvValue";
	/** Constant <code>PROP_LV_VALUE="bcpg:lvValue"</code> */
	public static final String PROP_LV_VALUE = "bcpg:lvValue";
	/** Constant <code>PROP_CODE="bcpg:code"</code> */
	public static final String PROP_CODE = "bcpg:code";
	/** Constant <code>PROP_ERP_CODE="bcpg:erpCode"</code> */
	public static final String PROP_ERP_CODE = "bcpg:erpCode";
	/** Constant <code>PROP_LEGAL_NAME="bcpg:legalName"</code> */
	public static final String PROP_LEGAL_NAME = "bcpg:legalName";
	/** Constant <code>PROP_PLURAL_LEGAL_NAME="bcpg:pluralLegalName"</code> */
	public static final String PROP_PLURAL_LEGAL_NAME = "bcpg:pluralLegalName";
	/** Constant <code>ASSOC_CM_CONTAINS="cm:contains"</code> */
	public static final String ASSOC_CM_CONTAINS = "cm:contains";
	/** Constant <code>PROP_CM_NAME="cm:name"</code> */
	public static final String PROP_CM_NAME = "cm:name";
	/** Constant <code>PROP_BEST_BEFORE_DATE="bcpg:bestBeforeDate"</code> */
	public static final String PROP_BEST_BEFORE_DATE = "bcpg:bestBeforeDate";
	/** Constant <code>PROP_PRODUCT_USE_BY_DATE="qa:productUseByDate"</code> */
	public static final String PROP_PRODUCT_USE_BY_DATE = "qa:productUseByDate";
	/** Constant <code>PROP_USE_BY_DATE="bcpg:useByDate"</code> */
	public static final String PROP_USE_BY_DATE = "bcpg:useByDate";
	/** Constant <code>PROP_PERIOD_AFTER_OPENING="bcpg:periodAfterOpening"</code> */
	public static final String PROP_PERIOD_AFTER_OPENING = "bcpg:periodAfterOpening";

	// --- Specific Properties ---
	/** Constant <code>PROP_CM_USER="cm:person"</code> */
	public static final String PROP_CM_USER = "cm:person";
	/** Constant <code>PROP_CM_USERNAME="cm:userName"</code> */
	public static final String PROP_CM_USERNAME = "cm:userName";
	
	/** Constant <code>TYPE_ENTITY_V2="bcpg:entityV2"</code> */
	public static final String TYPE_ENTITY_V2 = "bcpg:entityV2";
	/** Constant <code>TYPE_FOLDER="cm:folder"</code> */
	public static final String TYPE_FOLDER = "cm:folder";
	/** Constant <code>TYPE_CONTENT="cm:content"</code> */
	public static final String TYPE_CONTENT = "cm:content";


	// --- Product Types ---
	/** Constant <code>TYPE_FINISHED_PRODUCT="bcpg:finishedProduct"</code> */
	public static final String TYPE_FINISHED_PRODUCT = "bcpg:finishedProduct";
	/** Constant <code>TYPE_RAW_MATERIAL="bcpg:rawMaterial"</code> */
	public static final String TYPE_RAW_MATERIAL = "bcpg:rawMaterial";
	/** Constant <code>TYPE_PACKAGING_MATERIAL="bcpg:packagingMaterial"</code> */
	public static final String TYPE_PACKAGING_MATERIAL = "bcpg:packagingMaterial";
	/** Constant <code>TYPE_RESOURCE_PRODUCT="bcpg:resourceProduct"</code> */
	public static final String TYPE_RESOURCE_PRODUCT = "bcpg:resourceProduct";
	/** Constant <code>TYPE_STEP="mpm:processStep"</code> */
	public static final String TYPE_STEP = "mpm:processStep";
	/** Constant <code>TYPE_SEMI_FINISHED_PRODUCT="bcpg:semiFinishedProduct"</code> */
	public static final String TYPE_SEMI_FINISHED_PRODUCT = "bcpg:semiFinishedProduct";
	/** Constant <code>TYPE_LOCAL_SEMI_FINISHED_PRODUCT="bcpg:localSemiFinishedProduct"</code> */
	public static final String TYPE_LOCAL_SEMI_FINISHED_PRODUCT = "bcpg:localSemiFinishedProduct";
	/** Constant <code>TYPE_PACKAGING_KIT="bcpg:packagingKit"</code> */
	public static final String TYPE_PACKAGING_KIT = "bcpg:packagingKit";
	/** Constant <code>TYPE_PRODUCT_SPECIFICATION="bcpg:productSpecification"</code> */
	public static final String TYPE_PRODUCT_SPECIFICATION = "bcpg:productSpecification";
	/** Constant <code>TYPE_LOGISTIC_UNIT="bcpg:logisticUnit"</code> */
	public static final String TYPE_LOGISTIC_UNIT = "bcpg:logisticUnit";
	/** Constant <code>TYPE_PRODUCT_COLLECTION="bcpg:productCollection"</code> */
	public static final String TYPE_PRODUCT_COLLECTION = "bcpg:productCollection";
	/** Constant <code>TYPE_PROJECT="pjt:project"</code> */
	public static final String TYPE_PROJECT = "pjt:project";
	/** Constant <code>TYPE_SUPPLIER="bcpg:supplier"</code> */
	public static final String TYPE_SUPPLIER = "bcpg:supplier";
	/** Constant <code>TYPE_CLIENT="bcpg:client"</code> */
	public static final String TYPE_CLIENT = "bcpg:client";
	/** Constant <code>TYPE_SUBSIDIARY="bcpg:subsidiary"</code> */
	public static final String TYPE_SUBSIDIARY = "bcpg:subsidiary";
	/** Constant <code>TYPE_PLANT="bcpg:plant"</code> */
	public static final String TYPE_PLANT = "bcpg:plant";
	/** Constant <code>TYPE_TRADEMARK="bcpg:trademark"</code> */
	public static final String TYPE_TRADEMARK = "bcpg:trademark";
	/** Constant <code>TYPE_CERTIFICATION="bcpg:certification"</code> */
	public static final String TYPE_CERTIFICATION = "bcpg:certification";
	/** Constant <code>TYPE_APPROVAL_NUMBER="bcpg:approvalNumber"</code> */
	public static final String TYPE_APPROVAL_NUMBER = "bcpg:approvalNumber";
	/** Constant <code>TYPE_LABORATORY="bcpg:laboratory"</code> */
	public static final String TYPE_LABORATORY = "bcpg:laboratory";

	// --- Batch ---
	/** Constant <code>PROP_BATCH_STATE="qa:batchState"</code> */
	public static final String PROP_BATCH_STATE = "qa:batchState";
	/** Constant <code>PROP_BATCH_QTY_UNIT="qa:batchQtyUnit"</code> */
	public static final String PROP_BATCH_QTY_UNIT = "qa:batchQtyUnit";
	/** Constant <code>PROP_BATCH_QTY="qa:batchQty"</code> */
	public static final String PROP_BATCH_QTY = "qa:batchQty";
	/** Constant <code>PROP_BATCH_ID="qa:batchId"</code> */
	public static final String PROP_BATCH_ID = "qa:batchId";

	// --- Ingredient Type ---
	/** Constant <code>TYPE_ING="bcpg:ing"</code> */
	public static final String TYPE_ING = "bcpg:ing";
	/** Constant <code>TYPE_ING_TYPE_ITEM="bcpg:ingTypeItem"</code> */
	public static final String TYPE_ING_TYPE_ITEM = "bcpg:ingTypeItem";
	
	// --- Product Type ---
	/** Constant <code>TYPE_PRODUCT="bcpg:product"</code> */
	public static final String TYPE_PRODUCT = "bcpg:product";
	/** Constant <code>TYPE_PRODUCTLIST_ITEM="bcpg:productListItem"</code> */
	public static final String TYPE_PRODUCTLIST_ITEM = "bcpg:productListItem";
	
	// --- Characteristic Types ---
	/** Constant <code>TYPE_ALLERGEN="bcpg:allergen"</code> */
	public static final String TYPE_ALLERGEN = "bcpg:allergen";
	/** Constant <code>TYPE_COST="bcpg:cost"</code> */
	public static final String TYPE_COST = "bcpg:cost";
	/** Constant <code>TYPE_LCA="bcpg:lca"</code> */
	public static final String TYPE_LCA = "bcpg:lca";
	/** Constant <code>TYPE_MICROBIO="bcpg:microbio"</code> */
	public static final String TYPE_MICROBIO = "bcpg:microbio";
	/** Constant <code>TYPE_GEO_ORIGIN="bcpg:geoOrigin"</code> */
	public static final String TYPE_GEO_ORIGIN = "bcpg:geoOrigin";
	/** Constant <code>TYPE_BIO_ORIGIN="bcpg:bioOrigin"</code> */
	public static final String TYPE_BIO_ORIGIN = "bcpg:bioOrigin";
	/** Constant <code>TYPE_NUT="bcpg:nut"</code> */
	public static final String TYPE_NUT = "bcpg:nut";
	/** Constant <code>TYPE_ORGANO="bcpg:organo"</code> */
	public static final String TYPE_ORGANO = "bcpg:organo";
	/** Constant <code>TYPE_PHYSICO_CHEM="bcpg:physicoChem"</code> */
	public static final String TYPE_PHYSICO_CHEM = "bcpg:physicoChem";
	/** Constant <code>TYPE_LABEL_CLAIM="bcpg:labelClaim"</code> */
	public static final String TYPE_LABEL_CLAIM = "bcpg:labelClaim";
	/** Constant <code>TYPE_STORAGE_CONDITIONS="bcpg:storageConditions"</code> */
	public static final String TYPE_STORAGE_CONDITIONS = "bcpg:storageConditions";
	/** Constant <code>TYPE_PRECAUTION_OF_USE="bcpg:precautionOfUse"</code> */
	public static final String TYPE_PRECAUTION_OF_USE = "bcpg:precautionOfUse";
	/** Constant <code>TYPE_CUSTOMS_CODE="bcpg:customsCode"</code> */
	public static final String TYPE_CUSTOMS_CODE = "bcpg:customsCode";
	/** Constant <code>TYPE_TOX="bcpg:tox"</code> */
	public static final String TYPE_TOX = "bcpg:tox";
	/** Constant <code>TYPE_TOX_ING="bcpg:toxIng"</code> */
	public static final String TYPE_TOX_ING = "bcpg:toxIng";
	
	// --- Regulatory Types ---
	/** Constant <code>TYPE_REGULATORY_USAGE="bcpg:regulatoryUsage"</code> */
	public static final String TYPE_REGULATORY_USAGE = "bcpg:regulatoryUsage";
	
	// --- DataList Types ---
	/** Constant <code>TYPE_ALLERGENLIST="bcpg:allergenList"</code> */
	public static final String TYPE_ALLERGENLIST = "bcpg:allergenList";
	/** Constant <code>TYPE_PRODUCTLIST="bcpg:productList"</code> */
	public static final String TYPE_PRODUCTLIST = "bcpg:productList";
	/** Constant <code>TYPE_COMPOLIST="bcpg:compoList"</code> */
	public static final String TYPE_COMPOLIST = "bcpg:compoList";
	/** Constant <code>TYPE_PACKAGINGLIST="bcpg:packagingList"</code> */
	public static final String TYPE_PACKAGINGLIST = "bcpg:packagingList";
	/** Constant <code>TYPE_COSTLIST="bcpg:costList"</code> */
	public static final String TYPE_COSTLIST = "bcpg:costList";
	/** Constant <code>TYPE_PRICELIST="bcpg:priceList"</code> */
	public static final String TYPE_PRICELIST = "bcpg:priceList";
	/** Constant <code>TYPE_INGLIST="bcpg:ingList"</code> */
	public static final String TYPE_INGLIST = "bcpg:ingList";
	/** Constant <code>TYPE_NUTLIST="bcpg:nutList"</code> */
	public static final String TYPE_NUTLIST = "bcpg:nutList";
	/** Constant <code>TYPE_ORGANOLIST="bcpg:organoList"</code> */
	public static final String TYPE_ORGANOLIST = "bcpg:organoList";
	/** Constant <code>TYPE_INGLABELINGLIST="bcpg:ingLabelingList"</code> */
	public static final String TYPE_INGLABELINGLIST = "bcpg:ingLabelingList";
	/** Constant <code>TYPE_LABELINGRULELIST="bcpg:labelingRuleList"</code> */
	public static final String TYPE_LABELINGRULELIST = "bcpg:labelingRuleList";
	/** Constant <code>TYPE_MICROBIOLIST="bcpg:microbioList"</code> */
	public static final String TYPE_MICROBIOLIST = "bcpg:microbioList";
	/** Constant <code>TYPE_PHYSICOCHEMLIST="bcpg:physicoChemList"</code> */
	public static final String TYPE_PHYSICOCHEMLIST = "bcpg:physicoChemList";
	/** Constant <code>TYPE_FORBIDDENINGLIST="bcpg:forbiddenIngList"</code> */
	public static final String TYPE_FORBIDDENINGLIST = "bcpg:forbiddenIngList";
	/** Constant <code>TYPE_REGULATORYLIST="bcpg:regulatoryList"</code> */
	public static final String TYPE_REGULATORYLIST = "bcpg:regulatoryList";
	/** Constant <code>TYPE_REQCTRLLIST="bcpg:reqCtrlList"</code> */
	public static final String TYPE_REQCTRLLIST = "bcpg:reqCtrlList";
	/** Constant <code>TYPE_SPEC_COMPATIBILITY_LIST="bcpg:productSpecCompatibilityList"</code> */
	public static final String TYPE_SPEC_COMPATIBILITY_LIST = "bcpg:productSpecCompatibilityList";
	/** Constant <code>TYPE_DYNAMICCHARACTLIST="bcpg:dynamicCharactList"</code> */
	public static final String TYPE_DYNAMICCHARACTLIST = "bcpg:dynamicCharactList";
	/** Constant <code>TYPE_CONTACTLIST="bcpg:contactList"</code> */
	public static final String TYPE_CONTACTLIST = "bcpg:contactList";
	/** Constant <code>TYPE_LABELCLAIMLIST="bcpg:labelClaimList"</code> */
	public static final String TYPE_LABELCLAIMLIST = "bcpg:labelClaimList";
	/** Constant <code>TYPE_SVHCLIST="bcpg:svhcList"</code> */
	public static final String TYPE_SVHCLIST = "bcpg:svhcList";
	/** Constant <code>TYPE_LCALIST="bcpg:lcaList"</code> */
	public static final String TYPE_LCALIST = "bcpg:lcaList";
	/** Constant <code>TYPE_INGREGULATORYLIST="bcpg:ingRegulatoryList"</code> */
	public static final String TYPE_INGREGULATORYLIST = "bcpg:ingRegulatoryList";
	/** Constant <code>TYPE_TASKLIST="pjt:taskList"</code> */
	public static final String TYPE_TASKLIST = "pjt:taskList";
	/** Constant <code>TYPE_BUDGETLIST="pjt:budgetList"</code> */
	public static final String TYPE_BUDGETLIST = "pjt:budgetList";
	/** Constant <code>TYPE_DELIVERABLELIST="pjt:deliverableList"</code> */
	public static final String TYPE_DELIVERABLELIST = "pjt:deliverableList";
	/** Constant <code>TYPE_INVOICELIST="pjt:invoiceList"</code> */
	public static final String TYPE_INVOICELIST = "pjt:invoiceList";
	/** Constant <code>TYPE_LOGTIMELIST="pjt:logTimeList"</code> */
	public static final String TYPE_LOGTIMELIST = "pjt:logTimeList";
	/** Constant <code>TYPE_SCORELIST="pjt:scoreList"</code> */
	public static final String TYPE_SCORELIST = "pjt:scoreList";
	/** Constant <code>TYPE_PROCESSLIST="mpm:processList"</code> */
	public static final String TYPE_PROCESSLIST = "mpm:processList";
	/** Constant <code>TYPE_STOCKLIST="qa:stockList"</code> */
	public static final String TYPE_STOCKLIST = "qa:stockList";

	/** Constant <code>TYPE_PRODUCT_MICROBIO_CRITERIA="bcpg:productMicrobioCriteria"</code> */
	public static final String TYPE_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteria";

	// --- Entity Template ---
	/** Constant <code>ASPECT_ENTITY_TPL="bcpg:entityTplAspect"</code> */
	public static final String ASPECT_ENTITY_TPL = "bcpg:entityTplAspect";
	/** Constant <code>PROP_TPL_ENABLED="bcpg:entityTplEnabled"</code> */
	public static final String PROP_TPL_ENABLED = "bcpg:entityTplEnabled";
	/** Constant <code>PROP_TPL_IS_DEFAULT="bcpg:entityTplIsDefault"</code> */
	public static final String PROP_TPL_IS_DEFAULT = "bcpg:entityTplIsDefault";
	/** Constant <code>ASSOC_ENTITY_TPL_REF="bcpg:entityTplRef"</code> */
	public static final String ASSOC_ENTITY_TPL_REF = "bcpg:entityTplRef";

	/** Constant <code>ASSOC_DOCUMENT_TYPE_REF="bcpg:documentTypeRef"</code> */
	public static final String ASSOC_DOCUMENT_TYPE_REF = "bcpg:documentTypeRef";

	/** Constant <code>PROP_EXTRA_PROMPT="bcpg:aiExtraPrompt"</code> */
	public static final String PROP_EXTRA_PROMPT = "bcpg:aiExtraPrompt";
	/** Constant <code>PROP_SUGGESTED_FIELDS="bcpg:aiSuggestedFields"</code> */
	public static final String PROP_SUGGESTED_FIELDS = "bcpg:aiSuggestedFields";
	/** Constant <code>PROP_VALIDATION_CRITERIA="bcpg:aiValidationCriteria"</code> */
	public static final String PROP_VALIDATION_CRITERIA = "bcpg:aiValidationCriteria";
	/** Constant <code>PROP_VALIDATION_STATE="bcpg:aiValidationState"</code> */
	public static final String PROP_VALIDATION_STATE = "bcpg:aiValidationState";
	/** Constant <code>PROP_VALIDATION_HINTS="bcpg:aiValidationHints"</code> */
	public static final String PROP_VALIDATION_HINTS = "bcpg:aiValidationHints";
	/** Constant <code>PROP_VALIDATION_DATE="bcpg:aiValidationDate"</code> */
	public static final String PROP_VALIDATION_DATE = "bcpg:aiValidationDate";

	/** Constant <code>PROP_CM_MODIFIED="cm:modified"</code> */
	public static final String PROP_CM_MODIFIED = "cm:modified";

	// --- Composition DataList ---
	/** Constant <code>ASSOC_COMPOLIST_PRODUCT="bcpg:compoListProduct"</code> */
	public static final String ASSOC_COMPOLIST_PRODUCT = "bcpg:compoListProduct"; 
	/** Constant <code>PROP_COMPOLIST_QTY_SUB_FORMULA="bcpg:compoListQtySubFormula"</code> */
	public static final String PROP_COMPOLIST_QTY_SUB_FORMULA = "bcpg:compoListQtySubFormula"; 
	/** Constant <code>PROP_COMPOLIST_UNIT="bcpg:compoListUnit"</code> */
	public static final String PROP_COMPOLIST_UNIT = "bcpg:compoListUnit"; 
	/** Constant <code>PROP_COMPOLIST_YIELD_PERC="bcpg:compoListYieldPerc"</code> */
	public static final String PROP_COMPOLIST_YIELD_PERC = "bcpg:compoListYieldPerc";
	/** Constant <code>PROP_COMPOLIST_OVERRUN_PERC="bcpg:compoListOverrunPerc"</code> */
	public static final String PROP_COMPOLIST_OVERRUN_PERC = "bcpg:compoListOverrunPerc";
	/** Constant <code>PROP_COMPOLIST_VOLUME="bcpg:compoListVolume"</code> */
	public static final String PROP_COMPOLIST_VOLUME = "bcpg:compoListVolume";
	/** Constant <code>PROP_COMPOLIST_LOSS_PERC="bcpg:compoListLossPerc"</code> */
	public static final String PROP_COMPOLIST_LOSS_PERC = "bcpg:compoListLossPerc";

	// --- Packaging DataList ---
	/** Constant <code>ASSOC_PACKAGINGLIST_PRODUCT="bcpg:packagingListProduct"</code> */
	public static final String ASSOC_PACKAGINGLIST_PRODUCT = "bcpg:packagingListProduct";
	/** Constant <code>PROP_PACKAGINGLIST_QTY="bcpg:packagingListQty"</code> */
	public static final String PROP_PACKAGINGLIST_QTY = "bcpg:packagingListQty";
	/** Constant <code>PROP_PACKAGINGLIST_UNIT="bcpg:packagingListUnit"</code> */
	public static final String PROP_PACKAGINGLIST_UNIT = "bcpg:packagingListUnit";
	/** Constant <code>PROP_PACKAGINGLIST_LOSS_PERC="bcpg:packagingListLossPerc"</code> */
	public static final String PROP_PACKAGINGLIST_LOSS_PERC = "bcpg:packagingListLossPerc";
	/** Constant <code>PROP_PACKAGINGLIST_PKG_LEVEL="bcpg:packagingListPkgLevel"</code> */
	public static final String PROP_PACKAGINGLIST_PKG_LEVEL = "bcpg:packagingListPkgLevel";

	// --- Process DataList ---
	/** Constant <code>PROP_PL_RESOURCE="mpm:plResource"</code> */
	public static final String PROP_PL_RESOURCE = "mpm:plResource";
	/** Constant <code>PROP_PL_QTY="mpm:plQty"</code> */
	public static final String PROP_PL_QTY = "mpm:plQty";
	/** Constant <code>PROP_PL_UNIT="mpm:plUnit"</code> */
	public static final String PROP_PL_UNIT = "mpm:plUnit";
	/** Constant <code>PROP_PL_STEP="mpm:plStep"</code> */
	public static final String PROP_PL_STEP = "mpm:plStep";
	/** Constant <code>PROP_PL_QTY_RESOURCE="mpm:plQtyResource"</code> */
	public static final String PROP_PL_QTY_RESOURCE = "mpm:plQtyResource";
	/** Constant <code>PROP_PL_RATE_RESOURCE="mpm:plRateResource"</code> */
	public static final String PROP_PL_RATE_RESOURCE = "mpm:plRateResource";
	/** Constant <code>PROP_PROCESS_STEP_DESCRIPTION="bcpg:processStepDescription"</code> */
	public static final String PROP_PROCESS_STEP_DESCRIPTION = "bcpg:processStepDescription";
	/** Constant <code>PROP_PROCESS_STEP_ORDER="bcpg:processStepOrder"</code> */
	public static final String PROP_PROCESS_STEP_ORDER = "bcpg:processStepOrder";

	// --- Project Task Datalist ---
	/** Constant <code>PROP_TASKLIST_RESOURCES="pjt:tlResources"</code> */
	public static final String PROP_TASKLIST_RESOURCES = "pjt:tlResources";
	/** Constant <code>PROP_TASKLIST_STATE="pjt:tlState"</code> */
	public static final String PROP_TASKLIST_STATE = "pjt:tlState";
	/** Constant <code>PROP_TASKLIST_TASK_NAME="pjt:tlTaskName"</code> */
	public static final String PROP_TASKLIST_TASK_NAME = "pjt:tlTaskName";
	/** Constant <code>PROP_TASKLIST_TASK_DESCRIPTION="pjt:tlTaskDescription"</code> */
	public static final String PROP_TASKLIST_TASK_DESCRIPTION = "pjt:tlTaskDescription";
	/** Constant <code>PROP_TASKLIST_IS_MILESTONE="pjt:tlIsMilestone"</code> */
	public static final String PROP_TASKLIST_IS_MILESTONE = "pjt:tlIsMilestone";
	/** Constant <code>PROP_TASKLIST_DURATION="pjt:tlDuration"</code> */
	public static final String PROP_TASKLIST_DURATION = "pjt:tlDuration";
	/** Constant <code>PROP_TASKLIST_START="pjt:tlStart"</code> */
	public static final String PROP_TASKLIST_START = "pjt:tlStart";
	/** Constant <code>PROP_TASKLIST_END="pjt:tlEnd"</code> */
	public static final String PROP_TASKLIST_END = "pjt:tlEnd";
	/** Constant <code>PROP_TASKLIST_PREV_TASKS="pjt:tlPrevTasks"</code> */
	public static final String PROP_TASKLIST_PREV_TASKS = "pjt:tlPrevTasks";
	/** Constant <code>PROP_TASKLIST_REFUSED_TASK_REF="pjt:tlRefusedTaskRef"</code> */
	public static final String PROP_TASKLIST_REFUSED_TASK_REF = "pjt:tlRefusedTaskRef";

	// --- Project score Datalist ---
	/** Constant <code>ASSOC_SL_SCORE_CRITERION="pjt:slScoreCriterion"</code> */
	public static final String ASSOC_SL_SCORE_CRITERION = "pjt:slScoreCriterion";
	/** Constant <code>PROP_SL_WEIGHT="pjt:slWeight"</code> */
	public static final String PROP_SL_WEIGHT = "pjt:slWeight";
	/** Constant <code>PROP_SL_SCORE="pjt:slScore"</code> */
	public static final String PROP_SL_SCORE = "pjt:slScore";
	/** Constant <code>PROP_SL_SCREENING="pjt:slScreening"</code> */
	public static final String PROP_SL_SCREENING = "pjt:slScreening";
	/** Constant <code>PROP_SL_SCORE_RANGE="pjt:slScoreRange"</code> */
	public static final String PROP_SL_SCORE_RANGE = "pjt:slScoreRange";

	// --- Project logtime Datalist ---
	/** Constant <code>ASSOC_LTL_TASK="pjt:ltlTask"</code> */
	public static final String ASSOC_LTL_TASK = "pjt:ltlTask";
	/** Constant <code>PROP_LTL_DATE="pjt:ltlDate"</code> */
	public static final String PROP_LTL_DATE = "pjt:ltlDate";
	/** Constant <code>PROP_LTL_TYPE="pjt:ltlType"</code> */
	public static final String PROP_LTL_TYPE = "pjt:ltlType";
	/** Constant <code>PROP_LTL_COMMENT="pjt:ltlComment"</code> */
	public static final String PROP_LTL_COMMENT = "pjt:ltlComment";
	/** Constant <code>PROP_LTL_TIME="pjt:ltlTime"</code> */
	public static final String PROP_LTL_TIME = "pjt:ltlTime";

	// --- Project invoice Datalist ---
	/** Constant <code>ASSOC_IL_BUDGET_REF="pjt:ilBudgetRef"</code> */
	public static final String ASSOC_IL_BUDGET_REF = "pjt:ilBudgetRef";
	/** Constant <code>ASSOC_IL_TASK_REF="pjt:ilTaskRef"</code> */
	public static final String ASSOC_IL_TASK_REF = "pjt:ilTaskRef";
	/** Constant <code>PROP_IL_INVOICE_NAME="pjt:ilInvoiceName"</code> */
	public static final String PROP_IL_INVOICE_NAME = "pjt:ilInvoiceName";
	/** Constant <code>PROP_IL_DATE="pjt:ilDate"</code> */
	public static final String PROP_IL_DATE = "pjt:ilDate";
	/** Constant <code>PROP_INVOICE="pjt:invoice"</code> */
	public static final String PROP_INVOICE = "pjt:invoice";

	// --- Project deliverables Datalist ---
	/** Constant <code>ASSOC_DL_CONTENT="pjt:dlContent"</code> */
	public static final String ASSOC_DL_CONTENT = "pjt:dlContent";
	/** Constant <code>ASSOC_DL_TASKS="pjt:dlTask"</code> */
	public static final String ASSOC_DL_TASKS = "pjt:dlTask";
	/** Constant <code>PROP_DL_URL="pjt:dlUrl"</code> */
	public static final String PROP_DL_URL = "pjt:dlUrl";
	/** Constant <code>PROP_DL_SCRIPT_EXEC_ORDER="pjt:dlScriptExecOrder"</code> */
	public static final String PROP_DL_SCRIPT_EXEC_ORDER = "pjt:dlScriptExecOrder";
	/** Constant <code>PROP_DL_STATE="pjt:dlState"</code> */
	public static final String PROP_DL_STATE = "pjt:dlState";
	/** Constant <code>PROP_DL_DESCRIPTION="pjt:dlDescription"</code> */
	public static final String PROP_DL_DESCRIPTION = "pjt:dlDescription";

	// --- Project budgeting Datalist ---
	/** Constant <code>PROP_BUDGETLIST_BUDGETED_EXPENSE="pjt:blBudgetedExpense"</code> */
	public static final String PROP_BUDGETLIST_BUDGETED_EXPENSE = "pjt:blBudgetedExpense";
	/** Constant <code>PROP_BUDGETLIST_BUDGETED_INVOICE="pjt:blBudgetedInvoice"</code> */
	public static final String PROP_BUDGETLIST_BUDGETED_INVOICE = "pjt:blBudgetedInvoice";
	/** Constant <code>PROP_BUDGETLIST_ITEM="pjt:blItem"</code> */
	public static final String PROP_BUDGETLIST_ITEM = "pjt:blItem";
	/** Constant <code>PROP_BUDGETLIST_PROFIT="pjt:blProfit"</code> */
	public static final String PROP_BUDGETLIST_PROFIT = "pjt:blProfit";

	// --- Organoleptic DataList ---
	/** Constant <code>PROP_ORGANO_LIST_VALUE="bcpg:organoListValue"</code> */
	public static final String PROP_ORGANO_LIST_VALUE = "bcpg:organoListValue";
	/** Constant <code>ASSOC_ORGANO_LIST_ORGANO="bcpg:organoListOrgano"</code> */
	public static final String ASSOC_ORGANO_LIST_ORGANO = "bcpg:organoListOrgano";

	// --- Nutrition DataList ---
	/** Constant <code>PROP_NUT_LIST_VALUE="bcpg:nutListValue"</code> */
	public static final String PROP_NUT_LIST_VALUE = "bcpg:nutListValue";
	/** Constant <code>ASSOC_NUT_LIST_NUT="bcpg:nutListNut"</code> */
	public static final String ASSOC_NUT_LIST_NUT = "bcpg:nutListNut";

	/** Constant <code>PROP_MICROBIO_LIST_VALUE="bcpg:mblValue"</code> */
	public static final String PROP_MICROBIO_LIST_VALUE = "bcpg:mblValue";
	/** Constant <code>ASSOC_MICROBIO_LIST_MICROBIO="bcpg:mblMicrobio"</code> */
	public static final String ASSOC_MICROBIO_LIST_MICROBIO = "bcpg:mblMicrobio";
	/** Constant <code>PROP_MICROBIO_LIST_UNIT="bcpg:mblUnit"</code> */
	public static final String PROP_MICROBIO_LIST_UNIT = "bcpg:mblUnit";
	/** Constant <code>PROP_MICROBIO_LIST_MAXI="bcpg:mblMaxi"</code> */
	public static final String PROP_MICROBIO_LIST_MAXI = "bcpg:mblMaxi";

	// --- Allergen DataList ---
	/** Constant <code>ASSOC_ALLERGEN_LIST_ALLERGEN="bcpg:allergenListAllergen"</code> */
	public static final String ASSOC_ALLERGEN_LIST_ALLERGEN = "bcpg:allergenListAllergen";
	/** Constant <code>PROP_ALLERGEN_LIST_VOLUNTARY="bcpg:allergenListVoluntary"</code> */
	public static final String PROP_ALLERGEN_LIST_VOLUNTARY = "bcpg:allergenListVoluntary";
	/** Constant <code>PROP_ALLERGEN_LIST_INVOLUNTARY="bcpg:allergenListInVoluntary"</code> */
	public static final String PROP_ALLERGEN_LIST_INVOLUNTARY = "bcpg:allergenListInVoluntary";
	/** Constant <code>PROP_ALLERGEN_LIST_ON_LINE="bcpg:allergenListOnLine"</code> */
	public static final String PROP_ALLERGEN_LIST_ON_LINE = "bcpg:allergenListOnLine";
	/** Constant <code>PROP_ALLERGEN_LIST_ON_SITE="bcpg:allergenListOnSite"</code> */
	public static final String PROP_ALLERGEN_LIST_ON_SITE = "bcpg:allergenListOnSite";
	/** Constant <code>PROP_ALLERGEN_LIST_IS_CLEANED="bcpg:allergenListIsCleaned"</code> */
	public static final String PROP_ALLERGEN_LIST_IS_CLEANED = "bcpg:allergenListIsCleaned";

	/** Constant <code>PROP_LCL_CLAIM_VALUE="bcpg:lclClaimValue"</code> */
	public static final String PROP_LCL_CLAIM_VALUE = "bcpg:lclClaimValue";
	/** Constant <code>ASSOC_LCL_LABEL_CLAIM="bcpg:lclLabelClaim"</code> */
	public static final String ASSOC_LCL_LABEL_CLAIM = "bcpg:lclLabelClaim";

	// --- Ingredient DataList (Detailed) ---
	/** Constant <code>ASSOC_ING_LIST_ING="bcpg:ingListIng"</code> */
	public static final String ASSOC_ING_LIST_ING = "bcpg:ingListIng";
	/** Constant <code>PROP_ING_LIST_QTY_PERC="bcpg:ingListQtyPerc"</code> */
	public static final String PROP_ING_LIST_QTY_PERC = "bcpg:ingListQtyPerc";
	/** Constant <code>PROP_ING_TYPE_V2="bcpg:ingTypeV2"</code> */
	public static final String PROP_ING_TYPE_V2 = "bcpg:ingTypeV2";
	
	// --- Regulatory DataLists ---
	
	// --- Regulatory Properties ---
	/** Constant <code>PROP_REGULATORY_CODE="bcpg:regulatoryCode"</code> */
	public static final String PROP_REGULATORY_CODE = "bcpg:regulatoryCode";
	/** Constant <code>PROP_REGULATORY_MODE="bcpg:regulatoryMode"</code> */
	public static final String PROP_REGULATORY_MODE = "bcpg:regulatoryMode";
	/** Constant <code>PROP_REGULATORY_STATE="bcpg:regulatoryState"</code> */
	public static final String PROP_REGULATORY_STATE = "bcpg:regulatoryState";
	/** Constant <code>PROP_REGULATORY_MODULE="bcpg:regulatoryModule"</code> */
	public static final String PROP_REGULATORY_MODULE = "bcpg:regulatoryModule";
	/** Constant <code>PROP_REGULATORY_ID="bcpg:regulatoryId"</code> */
	public static final String PROP_REGULATORY_ID = "bcpg:regulatoryId";
	/** Constant <code>PROP_REGULATORY_USAGE_REF="bcpg:regulatoryUsageRef"</code> */
	public static final String PROP_REGULATORY_USAGE_REF = "bcpg:regulatoryUsageRef";
	/** Constant <code>PROP_REGULATORY_COUNTRIES="bcpg:regulatoryCountries"</code> */
	public static final String PROP_REGULATORY_COUNTRIES = "bcpg:regulatoryCountries";
	/** Constant <code>PROP_REGULATORY_RECIPE_ID="bcpg:regulatoryRecipeId"</code> */
	public static final String PROP_REGULATORY_RECIPE_ID = "bcpg:regulatoryRecipeId";
	/** Constant <code>PROP_REGULATORY_COMMENT="bcpg:regulatoryComment"</code> */
	public static final String PROP_REGULATORY_COMMENT = "bcpg:regulatoryComment";
	
	// --- Ingredient Properties ---
	/** Constant <code>PROP_CAS_NUMBER="bcpg:casNumber"</code> */
	public static final String PROP_CAS_NUMBER = "bcpg:casNumber";
	/** Constant <code>PROP_CE_NUMBER="bcpg:ceNumber"</code> */
	public static final String PROP_CE_NUMBER = "bcpg:ceNumber";
	/** Constant <code>PROP_GEO_ORIGIN_ISO_CODE="bcpg:geoOriginISOCode"</code> */
	public static final String PROP_GEO_ORIGIN_ISO_CODE = "bcpg:geoOriginISOCode";
	
	// --- Forbidden Ingredient List Properties ---
	/** Constant <code>PROP_FIL_QTY_PERC_MAXI="bcpg:filQtyPercMaxi"</code> */
	public static final String PROP_FIL_QTY_PERC_MAXI = "bcpg:filQtyPercMaxi";
	/** Constant <code>PROP_FIL_QTY_PERC_MAXI_UNIT="bcpg:filQtyPercMaxiUnit"</code> */
	public static final String PROP_FIL_QTY_PERC_MAXI_UNIT = "bcpg:filQtyPercMaxiUnit";
	/** Constant <code>PROP_FIL_INGS="bcpg:filIngs"</code> */
	public static final String PROP_FIL_INGS = "bcpg:filIngs";
	/** Constant <code>PROP_FIL_REQ_MESSAGE="bcpg:filReqMessage"</code> */
	public static final String PROP_FIL_REQ_MESSAGE = "bcpg:filReqMessage";
	/** Constant <code>PROP_FIL_REQ_TYPE="bcpg:filReqType"</code> */
	public static final String PROP_FIL_REQ_TYPE = "bcpg:filReqType";
	/** Constant <code>PROP_FIL_DATA_TYPE="bcpg:filDataType"</code> */
	public static final String PROP_FIL_DATA_TYPE = "bcpg:filDataType";
	/** Constant <code>PROP_FIL_SOURCES_V2="bcpg:filSourcesV2"</code> */
	public static final String PROP_FIL_SOURCES_V2 = "bcpg:filSourcesV2";
	/** Constant <code>PROP_FIL_REGULATORY_CODE="bcpg:filRegulatoryCode"</code> */
	public static final String PROP_FIL_REGULATORY_CODE = "bcpg:filRegulatoryCode";
	
	// --- Ingredient Regulatory List Properties ---
	/** Constant <code>PROP_IRL_ING="bcpg:irlIng"</code> */
	public static final String PROP_IRL_ING = "bcpg:irlIng";
	/** Constant <code>PROP_IRL_USAGES="bcpg:irlUsages"</code> */
	public static final String PROP_IRL_USAGES = "bcpg:irlUsages";
	/** Constant <code>PROP_IRL_RESULT_INDICATOR="bcpg:irlResultIndicator"</code> */
	public static final String PROP_IRL_RESULT_INDICATOR = "bcpg:irlResultIndicator";
	/** Constant <code>PROP_IRL_RESTRICTION_LEVELS="bcpg:irlRestrictionLevels"</code> */
	public static final String PROP_IRL_RESTRICTION_LEVELS = "bcpg:irlRestrictionLevels";
	/** Constant <code>PROP_IRL_CITATION="bcpg:irlCitation"</code> */
	public static final String PROP_IRL_CITATION = "bcpg:irlCitation";
	/** Constant <code>PROP_IRL_PRECAUTIONS="bcpg:irlPrecautions"</code> */
	public static final String PROP_IRL_PRECAUTIONS = "bcpg:irlPrecautions";
	/** Constant <code>PROP_IRL_SOURCES="bcpg:irlSources"</code> */
	public static final String PROP_IRL_SOURCES = "bcpg:irlSources";
	
	// --- Regulatory Requirement Types ---
	/** Constant <code>REQ_TYPE_FORBIDDEN="Forbidden"</code> */
	public static final String REQ_TYPE_FORBIDDEN = "Forbidden";
	/** Constant <code>REQ_TYPE_TOLERATED="Tolerated"</code> */
	public static final String REQ_TYPE_TOLERATED = "Tolerated";

	// --- Physico-Chemical DataList ---
	/** Constant <code>PROP_PCL_VALUE="bcpg:pclValue"</code> */
	public static final String PROP_PCL_VALUE = "bcpg:pclValue";
	/** Constant <code>PROP_PCL_UNIT="bcpg:pclUnit"</code> */
	public static final String PROP_PCL_UNIT= "bcpg:pclUnit";
	/** Constant <code>ASSOC_PCL_PHYSICO_CHEM="bcpg:pclPhysicoChem"</code> */
	public static final String ASSOC_PCL_PHYSICO_CHEM = "bcpg:pclPhysicoChem";
	
	/** Constant <code>ASSOC_DOCUMENT_ENTITY_REF="bcpg:documentEntityRef"</code> */
	public static final String ASSOC_DOCUMENT_ENTITY_REF = "bcpg:documentEntityRef";

	// --- Cost List ---
	/** Constant <code>ASSOC_COST_LIST_COST="bcpg:costListCost"</code> */
	public static final String ASSOC_COST_LIST_COST = "bcpg:costListCost";
	/** Constant <code>PROP_COSTLIST_VALUE_PER_PRODUCT="bcpg:costListValuePerProduct"</code> */
	public static final String PROP_COSTLIST_VALUE_PER_PRODUCT = "bcpg:costListValuePerProduct";
	/** Constant <code>PROP_COSTLIST_VALUE="bcpg:costListValue"</code> */
	public static final String PROP_COSTLIST_VALUE = "bcpg:costListValue";
	/** Constant <code>PROP_COSTLIST_UNIT="bcpg:costListUnit"</code> */
	public static final String PROP_COSTLIST_UNIT = "bcpg:costListUnit";

	// --- Requirements List ---
	/** Constant <code>PROP_RCL_REQ_MESSAGE="bcpg:rclReqMessage"</code> */
	public static final String PROP_RCL_REQ_MESSAGE = "bcpg:rclReqMessage";
	/** Constant <code>PROP_RCL_REQ_TYPE="bcpg:rclReqType"</code> */
	public static final String PROP_RCL_REQ_TYPE = "bcpg:rclReqType";
	/** Constant <code>PROP_RCL_SOURCES_V2="bcpg:rclSourcesV2"</code> */
	public static final String PROP_RCL_SOURCES_V2 = "bcpg:rclSourcesV2";
	/** Constant <code>ASSOC_RCL_CHARACT="bcpg:rclCharact"</code> */
	public static final String ASSOC_RCL_CHARACT = "bcpg:rclCharact";
	/** Constant <code>PROP_RCL_REQ_DATA_TYPE="bcpg:rclDataType"</code> */
	public static final String PROP_RCL_REQ_DATA_TYPE = "bcpg:rclDataType";
	/** Constant <code>DATA_TYPE_SPECIFICATION="Specification"</code> */
	public static final String DATA_TYPE_SPECIFICATION = "Specification";

	// --- Additional Missing Properties from PLMModel ---
	
	// Product List
	/** Constant <code>ASSOC_PRODUCTLIST_PRODUCT="bcpg:productListProduct"</code> */
	public static final String ASSOC_PRODUCTLIST_PRODUCT = "bcpg:productListProduct";
	
	// Composition List Extended
	/** Constant <code>PROP_COMPOLIST_QTY="bcpg:compoListQty"</code> */
	public static final String PROP_COMPOLIST_QTY = "bcpg:compoListQty";
	/** Constant <code>PROP_COMPOLIST_QTY_FOR_PRODUCT="bcpg:compoListQtyForProduct"</code> */
	public static final String PROP_COMPOLIST_QTY_FOR_PRODUCT = "bcpg:compoListQtyForProduct";
	/** Constant <code>PROP_COMPOLIST_QTY_PERC_FOR_PRODUCT="bcpg:compoListQtyPercForProduct"</code> */
	public static final String PROP_COMPOLIST_QTY_PERC_FOR_PRODUCT = "bcpg:compoListQtyPercForProduct";
	/** Constant <code>PROP_COMPOLIST_QTY_PERC_FOR_SF="bcpg:compoListQtyPercForSF"</code> */
	public static final String PROP_COMPOLIST_QTY_PERC_FOR_SF = "bcpg:compoListQtyPercForSF";
	/** Constant <code>PROP_COMPOLIST_QTY_AFTER_PROCESS="bcpg:compoListQtyAfterProcess"</code> */
	public static final String PROP_COMPOLIST_QTY_AFTER_PROCESS = "bcpg:compoListQtyAfterProcess";
	/** Constant <code>PROP_COMPOLIST_DECL_TYPE="bcpg:compoListDeclType"</code> */
	public static final String PROP_COMPOLIST_DECL_TYPE = "bcpg:compoListDeclType";
	
	// Packaging List Extended
	/** Constant <code>PROP_PACKAGINGLIST_QTY_FOR_PRODUCT="bcpg:packagingListQtyForProduct"</code> */
	public static final String PROP_PACKAGINGLIST_QTY_FOR_PRODUCT = "bcpg:packagingListQtyForProduct";
	/** Constant <code>PROP_PACKAGINGLIST_QTY_PERC_FOR_PRODUCT="bcpg:packagingListQtyPercForProduct"</code> */
	public static final String PROP_PACKAGINGLIST_QTY_PERC_FOR_PRODUCT = "bcpg:packagingListQtyPercForProduct";
	/** Constant <code>PROP_PACKAGINGLIST_QTY_PERC_FOR_SF="bcpg:packagingListQtyPercForSF"</code> */
	public static final String PROP_PACKAGINGLIST_QTY_PERC_FOR_SF = "bcpg:packagingListQtyPercForSF";
	/** Constant <code>PROP_PACKAGINGLIST_ISMASTER="bcpg:packagingListIsMaster"</code> */
	public static final String PROP_PACKAGINGLIST_ISMASTER = "bcpg:packagingListIsMaster";
	
	// Cost List Extended
	/** Constant <code>PROP_COSTLIST_MAXI="bcpg:costListMaxi"</code> */
	public static final String PROP_COSTLIST_MAXI = "bcpg:costListMaxi";
	
	// Price List
	/** Constant <code>ASSOC_PRICELIST_COST="bcpg:priceListCost"</code> */
	public static final String ASSOC_PRICELIST_COST = "bcpg:priceListCost";
	/** Constant <code>PROP_PRICELIST_PREF_RANK="bcpg:priceListPrefRank"</code> */
	public static final String PROP_PRICELIST_PREF_RANK = "bcpg:priceListPrefRank";
	/** Constant <code>PROP_PRICELIST_VALUE="bcpg:priceListValue"</code> */
	public static final String PROP_PRICELIST_VALUE = "bcpg:priceListValue";
	/** Constant <code>PROP_PRICELIST_UNIT="bcpg:priceListUnit"</code> */
	public static final String PROP_PRICELIST_UNIT = "bcpg:priceListUnit";
	/** Constant <code>PROP_PRICELIST_PURCHASE_QTY="bcpg:priceListPurchaseQty"</code> */
	public static final String PROP_PRICELIST_PURCHASE_QTY = "bcpg:priceListPurchaseQty";
	/** Constant <code>PROP_PRICELIST_PURCHASE_UNIT="bcpg:priceListPurchaseUnit"</code> */
	public static final String PROP_PRICELIST_PURCHASE_UNIT = "bcpg:priceListPurchaseUnit";
	
	// Ingredient List Extended
	/** Constant <code>PROP_INGLIST_QTY_PERCWITHYIELD="bcpg:ingListQtyPercWithYield"</code> */
	public static final String PROP_INGLIST_QTY_PERCWITHYIELD = "bcpg:ingListQtyPercWithYield";
	/** Constant <code>PROP_INGLIST_QTY_PERCWITHSECONDARYYIELD="bcpg:ingListQtyPercWithSecondaryYield"</code> */
	public static final String PROP_INGLIST_QTY_PERCWITHSECONDARYYIELD = "bcpg:ingListQtyPercWithSecondaryYield";
	/** Constant <code>PROP_INGLIST_IS_GMO="bcpg:ingListIsGMO"</code> */
	public static final String PROP_INGLIST_IS_GMO = "bcpg:ingListIsGMO";
	/** Constant <code>PROP_INGLIST_IS_IONIZED="bcpg:ingListIsIonized"</code> */
	public static final String PROP_INGLIST_IS_IONIZED = "bcpg:ingListIsIonized";
	/** Constant <code>PROP_INGLIST_IS_PROCESSING_AID="bcpg:ingListIsProcessingAid"</code> */
	public static final String PROP_INGLIST_IS_PROCESSING_AID = "bcpg:ingListIsProcessingAid";
	/** Constant <code>PROP_INGLIST_DECL_TYPE="bcpg:ingListDeclType"</code> */
	public static final String PROP_INGLIST_DECL_TYPE = "bcpg:ingListDeclType";
	/** Constant <code>ASSOC_INGLIST_GEO_ORIGIN="bcpg:ingListGeoOrigin"</code> */
	public static final String ASSOC_INGLIST_GEO_ORIGIN = "bcpg:ingListGeoOrigin";
	/** Constant <code>ASSOC_INGLIST_BIO_ORIGIN="bcpg:ingListBioOrigin"</code> */
	public static final String ASSOC_INGLIST_BIO_ORIGIN = "bcpg:ingListBioOrigin";
	
	// Nutrition List Extended
	/** Constant <code>PROP_NUTLIST_VALUE_PER_SERVING="bcpg:nutListValuePerServing"</code> */
	public static final String PROP_NUTLIST_VALUE_PER_SERVING = "bcpg:nutListValuePerServing";
	/** Constant <code>PROP_NUTLIST_MEASUREMENTPRECISION="bcpg:nutListMeasurementPrecision"</code> */
	public static final String PROP_NUTLIST_MEASUREMENTPRECISION = "bcpg:nutListMeasurementPrecision";
	/** Constant <code>PROP_NUTLIST_FORMULATED_VALUE_PER_SERVING="bcpg:nutListFormulatedValuePerServing"</code> */
	public static final String PROP_NUTLIST_FORMULATED_VALUE_PER_SERVING = "bcpg:nutListFormulatedValuePerServing";
	/** Constant <code>PROP_NUTLIST_FORMULATED_VALUE="bcpg:nutListFormulatedValue"</code> */
	public static final String PROP_NUTLIST_FORMULATED_VALUE = "bcpg:nutListFormulatedValue";
	/** Constant <code>PROP_NUTLIST_UNIT_PREPARED="bcpg:nutListUnitPrepared"</code> */
	public static final String PROP_NUTLIST_UNIT_PREPARED = "bcpg:nutListUnitPrepared";
	/** Constant <code>PROP_NUTLIST_MINI="bcpg:nutListMini"</code> */
	public static final String PROP_NUTLIST_MINI = "bcpg:nutListMini";
	/** Constant <code>PROP_NUTLIST_VALUE_PREPARED="bcpg:nutListValuePrepared"</code> */
	public static final String PROP_NUTLIST_VALUE_PREPARED = "bcpg:nutListValuePrepared";
	/** Constant <code>PROP_NUTLIST_FORMULATED_PREPARED="bcpg:nutListFormulatedValuePrepared"</code> */
	public static final String PROP_NUTLIST_FORMULATED_PREPARED = "bcpg:nutListFormulatedValuePrepared";
	/** Constant <code>PROP_NUTLIST_FORMULATED_MINI="bcpg:nutListFormulatedMini"</code> */
	public static final String PROP_NUTLIST_FORMULATED_MINI = "bcpg:nutListFormulatedMini";
	/** Constant <code>PROP_NUTLIST_FORMULATED_MAXI="bcpg:nutListFormulatedMaxi"</code> */
	public static final String PROP_NUTLIST_FORMULATED_MAXI = "bcpg:nutListFormulatedMaxi";
	/** Constant <code>PROP_NUTLIST_MAXI="bcpg:nutListMaxi"</code> */
	public static final String PROP_NUTLIST_MAXI = "bcpg:nutListMaxi";
	/** Constant <code>PROP_NUTLIST_GROUP="bcpg:nutListGroup"</code> */
	public static final String PROP_NUTLIST_GROUP = "bcpg:nutListGroup";
	/** Constant <code>PROP_NUTLIST_ROUNDED_VALUE="bcpg:nutListRoundedValue"</code> */
	public static final String PROP_NUTLIST_ROUNDED_VALUE = "bcpg:nutListRoundedValue";
	/** Constant <code>PROP_NUTLIST_PREPARED_ROUNDED_VALUE="bcpg:nutListRoundedValuePrepared"</code> */
	public static final String PROP_NUTLIST_PREPARED_ROUNDED_VALUE = "bcpg:nutListRoundedValuePrepared";
	/** Constant <code>PROP_NUTLIST_METHOD="bcpg:nutListMethod"</code> */
	public static final String PROP_NUTLIST_METHOD = "bcpg:nutListMethod";
	/** Constant <code>ASPECT_NUTLIST_PREPARED="bcpg:nutListPreparedAspect"</code> */
	public static final String ASPECT_NUTLIST_PREPARED = "bcpg:nutListPreparedAspect";
	
	// Labeling Rule List
	/** Constant <code>PROP_LABELINGRULELIST_LABEL="bcpg:lrLabel"</code> */
	public static final String PROP_LABELINGRULELIST_LABEL = "bcpg:lrLabel";
	/** Constant <code>PROP_LABELINGRULELIST_TYPE="bcpg:lrType"</code> */
	public static final String PROP_LABELINGRULELIST_TYPE = "bcpg:lrType";
	/** Constant <code>PROP_LABELINGRULELIST_SYNC_STATE="bcpg:lrSyncState"</code> */
	public static final String PROP_LABELINGRULELIST_SYNC_STATE = "bcpg:lrSyncState";
	/** Constant <code>ASSOC_ILL_GRP="bcpg:illGrp"</code> */
	public static final String ASSOC_ILL_GRP = "bcpg:illGrp";
	/** Constant <code>PROP_ILL_VALUE="bcpg:illValue"</code> */
	public static final String PROP_ILL_VALUE = "bcpg:illValue";
	/** Constant <code>PROP_ILL_MANUAL_VALUE="bcpg:illManualValue"</code> */
	public static final String PROP_ILL_MANUAL_VALUE = "bcpg:illManualValue";
	/** Constant <code>PROP_ILL_LOG_VALUE="bcpg:illLogValue"</code> */
	public static final String PROP_ILL_LOG_VALUE = "bcpg:illLogValue";
	
	// Microbio List Extended
	/** Constant <code>PROP_MICROBIOLIST_TYPE="bcpg:mblType"</code> */
	public static final String PROP_MICROBIOLIST_TYPE = "bcpg:mblType";
	/** Constant <code>PROP_MICROBIOLIST_MAXI="bcpg:mblMaxi"</code> */
	public static final String PROP_MICROBIOLIST_MAXI = "bcpg:mblMaxi";
	/** Constant <code>PROP_MICROBIOLIST_TEXT_CRITERIA="bcpg:mblTextCriteria"</code> */
	public static final String PROP_MICROBIOLIST_TEXT_CRITERIA = "bcpg:mblTextCriteria";
	/** Constant <code>PROP_MICROBIO_TYPE="bcpg:microbioType"</code> */
	public static final String PROP_MICROBIO_TYPE = "bcpg:microbioType";
	
	// Physico-Chemical Extended
	/** Constant <code>PROP_PHYSICOCHEMLIST_UNIT="bcpg:pclUnit"</code> */
	public static final String PROP_PHYSICOCHEMLIST_UNIT = "bcpg:pclUnit";
	/** Constant <code>PROP_PHYSICOCHEMLIST_TYPE="bcpg:pclType"</code> */
	public static final String PROP_PHYSICOCHEMLIST_TYPE = "bcpg:pclType";
	/** Constant <code>PROP_PHYSICOCHEMLIST_MINI="bcpg:pclMini"</code> */
	public static final String PROP_PHYSICOCHEMLIST_MINI = "bcpg:pclMini";
	/** Constant <code>PROP_PHYSICOCHEMLIST_MAXI="bcpg:pclMaxi"</code> */
	public static final String PROP_PHYSICOCHEMLIST_MAXI = "bcpg:pclMaxi";
	/** Constant <code>PROP_PHYSICO_CHEM_UNIT="bcpg:physicoChemUnit"</code> */
	public static final String PROP_PHYSICO_CHEM_UNIT = "bcpg:physicoChemUnit";
	/** Constant <code>PROP_PHYSICO_CHEM_TYPE="bcpg:physicoChemType"</code> */
	public static final String PROP_PHYSICO_CHEM_TYPE = "bcpg:physicoChemType";
	/** Constant <code>PROP_PHYSICO_CHEM_FORMULATED="bcpg:physicoChemFormulated"</code> */
	public static final String PROP_PHYSICO_CHEM_FORMULATED = "bcpg:physicoChemFormulated";
	/** Constant <code>PROP_PHYSICO_CHEM_FORMULATED_FROM_VOL="bcpg:physicoChemFormulatedFromVol"</code> */
	public static final String PROP_PHYSICO_CHEM_FORMULATED_FROM_VOL = "bcpg:physicoChemFormulatedFromVol";
	/** Constant <code>PROP_PHYSICO_CHEM_FORMULA="bcpg:physicoChemFormula"</code> */
	public static final String PROP_PHYSICO_CHEM_FORMULA = "bcpg:physicoChemFormula";
	/** Constant <code>PROP_PHYSICO_CHEM_CODE="bcpg:physicoChemCode"</code> */
	public static final String PROP_PHYSICO_CHEM_CODE = "bcpg:physicoChemCode";
	
	// Forbidden Ingredient Extended
	/** Constant <code>PROP_FIL_IS_GMO="bcpg:filIsGMO"</code> */
	public static final String PROP_FIL_IS_GMO = "bcpg:filIsGMO";
	/** Constant <code>PROP_FIL_IS_IONIZED="bcpg:filIsIonized"</code> */
	public static final String PROP_FIL_IS_IONIZED = "bcpg:filIsIonized";
	/** Constant <code>ASSOC_FIL_GEO_ORIGINS="bcpg:filGeoOrigins"</code> */
	public static final String ASSOC_FIL_GEO_ORIGINS = "bcpg:filGeoOrigins";
	/** Constant <code>ASSOC_FIL_BIO_ORIGINS="bcpg:filBioOrigins"</code> */
	public static final String ASSOC_FIL_BIO_ORIGINS = "bcpg:filBioOrigins";
	
	// Spec Compatibility
	/** Constant <code>PROP_SPEC_COMPATIBILITY_JOB_ON="bcpg:specCompatibilityJobOn"</code> */
	public static final String PROP_SPEC_COMPATIBILITY_JOB_ON = "bcpg:specCompatibilityJobOn";
	/** Constant <code>PROP_SPEC_COMPATIBILITY_TEST_MODE="bcpg:specCompatibilityTestMode"</code> */
	public static final String PROP_SPEC_COMPATIBILITY_TEST_MODE = "bcpg:specCompatibilityTestMode";
	/** Constant <code>PROP_SPEC_COMPATIBILITY_LOG="bcpg:specCompatibilityLog"</code> */
	public static final String PROP_SPEC_COMPATIBILITY_LOG = "bcpg:specCompatibilityLog";
	/** Constant <code>ASSOC_PSCL_SOURCE_ITEM="bcpg:psclSourceItem"</code> */
	public static final String ASSOC_PSCL_SOURCE_ITEM = "bcpg:psclSourceItem";
	
	// Dynamic Charact
	/** Constant <code>PROP_DYNAMICCHARACT_TITLE="bcpg:dynamicCharactTitle"</code> */
	public static final String PROP_DYNAMICCHARACT_TITLE = "bcpg:dynamicCharactTitle";
	/** Constant <code>PROP_DYNAMICCHARACT_FORMULA="bcpg:dynamicCharactFormula"</code> */
	public static final String PROP_DYNAMICCHARACT_FORMULA = "bcpg:dynamicCharactFormula";
	/** Constant <code>PROP_DYNAMICCHARACT_VALUE="bcpg:dynamicCharactValue"</code> */
	public static final String PROP_DYNAMICCHARACT_VALUE = "bcpg:dynamicCharactValue";
	/** Constant <code>PROP_DYNAMICCHARACT_GROUP_COLOR="bcpg:dynamicCharactGroupColor"</code> */
	public static final String PROP_DYNAMICCHARACT_GROUP_COLOR = "bcpg:dynamicCharactGroupColor";
	/** Constant <code>PROP_DYNAMICCHARACT_COLUMN="bcpg:dynamicCharactColumn"</code> */
	public static final String PROP_DYNAMICCHARACT_COLUMN = "bcpg:dynamicCharactColumn";
	/** Constant <code>PROP_DYNAMICCHARACT_SYNCHRONIZABLE_STATE="bcpg:dynamicCharactSynchronisableState"</code> */
	public static final String PROP_DYNAMICCHARACT_SYNCHRONIZABLE_STATE = "bcpg:dynamicCharactSynchronisableState";
	
	// Contact List
	/** Constant <code>PROP_CONTACT_LIST_FIRST_NAME="bcpg:contactListFirstName"</code> */
	public static final String PROP_CONTACT_LIST_FIRST_NAME = "bcpg:contactListFirstName";
	/** Constant <code>PROP_CONTACT_LIST_LAST_NAME="bcpg:contactListLastName"</code> */
	public static final String PROP_CONTACT_LIST_LAST_NAME = "bcpg:contactListLastName";
	/** Constant <code>PROP_CONTACT_LIST_EMAIL="bcpg:contactListEmail"</code> */
	public static final String PROP_CONTACT_LIST_EMAIL = "bcpg:contactListEmail";
	
	// Label Claim Extended
	/** Constant <code>PROP_LCL_TYPE="bcpg:lclType"</code> */
	public static final String PROP_LCL_TYPE = "bcpg:lclType";
	/** Constant <code>PROP_LCL_FORMULAERROR="bcpg:lclFormulaErrorLog"</code> */
	public static final String PROP_LCL_FORMULAERROR = "bcpg:lclFormulaErrorLog";
	/** Constant <code>ASSOC_LCL_MISSING_LABELCLAIMS="bcpg:lclMissingLabelClaims"</code> */
	public static final String ASSOC_LCL_MISSING_LABELCLAIMS = "bcpg:lclMissingLabelClaims";
	/** Constant <code>PROP_LABEL_CLAIM_CODE="bcpg:labelClaimCode"</code> */
	public static final String PROP_LABEL_CLAIM_CODE = "bcpg:labelClaimCode";
	/** Constant <code>PROP_LABEL_CLAIM_TYPE="bcpg:labelClaimType"</code> */
	public static final String PROP_LABEL_CLAIM_TYPE = "bcpg:labelClaimType";
	/** Constant <code>PROP_LABEL_CLAIM_FORMULA="bcpg:labelClaimFormula"</code> */
	public static final String PROP_LABEL_CLAIM_FORMULA = "bcpg:labelClaimFormula";
	/** Constant <code>PROP_CLAIM_REGULATORY_THRESHOLD="bcpg:labelClaimRegulatoryThreshold"</code> */
	public static final String PROP_CLAIM_REGULATORY_THRESHOLD = "bcpg:labelClaimRegulatoryThreshold";
	
	// SVHC List
	/** Constant <code>PROP_SVHCLIST_QTY_PERC="bcpg:svhcListQtyPerc"</code> */
	public static final String PROP_SVHCLIST_QTY_PERC = "bcpg:svhcListQtyPerc";
	/** Constant <code>PROP_IS_SVHC="bcpg:isSubstanceOfVeryHighConcern"</code> */
	public static final String PROP_IS_SVHC = "bcpg:isSubstanceOfVeryHighConcern";
	/** Constant <code>ASSOC_SVHCLIST_ING="bcpg:svhcListIng"</code> */
	public static final String ASSOC_SVHCLIST_ING = "bcpg:svhcListIng";
	/** Constant <code>PROP_SVHC_REASONS_FOR_INCLUSION="bcpg:svhcReasonsForInclusion"</code> */
	public static final String PROP_SVHC_REASONS_FOR_INCLUSION = "bcpg:svhcReasonsForInclusion";
	
	// Allergen Extended
	/** Constant <code>PROP_ALLERGEN_CODE="bcpg:allergenCode"</code> */
	public static final String PROP_ALLERGEN_CODE = "bcpg:allergenCode";
	/** Constant <code>PROP_ALLERGEN_TYPE="bcpg:allergenType"</code> */
	public static final String PROP_ALLERGEN_TYPE = "bcpg:allergenType";
	/** Constant <code>PROP_ALLERGEN_REGULATORY_THRESHOLD="bcpg:allergenRegulatoryThreshold"</code> */
	public static final String PROP_ALLERGEN_REGULATORY_THRESHOLD = "bcpg:allergenRegulatoryThreshold";
	/** Constant <code>PROP_ALLERGEN_INVOL_REGULATORY_THRESHOLD="bcpg:allergenInVoluntaryRegulatoryThres"{trunked}</code> */
	public static final String PROP_ALLERGEN_INVOL_REGULATORY_THRESHOLD = "bcpg:allergenInVoluntaryRegulatoryThreshold";
	/** Constant <code>ASSOC_ALLERGENLIST_VOLUNTARY_SOURCES="bcpg:allergenListVolSources"</code> */
	public static final String ASSOC_ALLERGENLIST_VOLUNTARY_SOURCES = "bcpg:allergenListVolSources";
	/** Constant <code>ASSOC_ALLERGENLIST_INVOLUNTARY_SOURCES="bcpg:allergenListInVolSources"</code> */
	public static final String ASSOC_ALLERGENLIST_INVOLUNTARY_SOURCES = "bcpg:allergenListInVolSources";
	/** Constant <code>PROP_ALLERGEN_DECISION_TREE="bcpg:allergenListDecisionTree"</code> */
	public static final String PROP_ALLERGEN_DECISION_TREE = "bcpg:allergenListDecisionTree";
	/** Constant <code>ASSOC_ALLERGENSUBSETS="bcpg:allergenSubset"</code> */
	public static final String ASSOC_ALLERGENSUBSETS = "bcpg:allergenSubset";
	/** Constant <code>ASPECT_PRODUCT_MICROBIO_CRITERIA="bcpg:productMicrobioCriteriaAspect"</code> */
	public static final String ASPECT_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteriaAspect";
	/** Constant <code>ASSOC_PRODUCT_MICROBIO_CRITERIA="bcpg:productMicrobioCriteriaRef"</code> */
	public static final String ASSOC_PRODUCT_MICROBIO_CRITERIA = "bcpg:productMicrobioCriteriaRef";
	
	// Cost
	/** Constant <code>PROP_COST_FORMULA="bcpg:costFormula"</code> */
	public static final String PROP_COST_FORMULA = "bcpg:costFormula";
	/** Constant <code>PROP_COSTCURRENCY="bcpg:costCurrency"</code> */
	public static final String PROP_COSTCURRENCY = "bcpg:costCurrency";
	/** Constant <code>PROP_COSTFIXED="bcpg:costFixed"</code> */
	public static final String PROP_COSTFIXED = "bcpg:costFixed";
	/** Constant <code>PROP_COSTTYPE="bcpg:costType"</code> */
	public static final String PROP_COSTTYPE = "bcpg:costType";
	
	// LCA
	/** Constant <code>PROP_LCA_FORMULA="bcpg:lcaFormula"</code> */
	public static final String PROP_LCA_FORMULA = "bcpg:lcaFormula";
	/** Constant <code>PROP_LCAUNIT="bcpg:lcaUnit"</code> */
	public static final String PROP_LCAUNIT = "bcpg:lcaUnit";
	/** Constant <code>PROP_LCAFIXED="bcpg:lcaFixed"</code> */
	public static final String PROP_LCAFIXED = "bcpg:lcaFixed";
	/** Constant <code>PROP_LCATYPE="bcpg:lcaType"</code> */
	public static final String PROP_LCATYPE = "bcpg:lcaType";
	/** Constant <code>PROP_LCA_NORMALIZATION="bcpg:lcaNormalization"</code> */
	public static final String PROP_LCA_NORMALIZATION = "bcpg:lcaNormalization";
	/** Constant <code>PROP_LCA_PONDERATION="bcpg:lcaPonderation"</code> */
	public static final String PROP_LCA_PONDERATION = "bcpg:lcaPonderation";
	/** Constant <code>PROP_LCA_CODE="bcpg:lcaCode"</code> */
	public static final String PROP_LCA_CODE = "bcpg:lcaCode";
	/** Constant <code>ASSOC_LCALIST_LCA="bcpg:lcaListLca"</code> */
	public static final String ASSOC_LCALIST_LCA = "bcpg:lcaListLca";
	/** Constant <code>ASSOC_LCALIST_COMPONENT="bcpg:lcaListComponent"</code> */
	public static final String ASSOC_LCALIST_COMPONENT = "bcpg:lcaListComponent";
	/** Constant <code>PROP_LCALIST_UNIT="bcpg:lcaListUnit"</code> */
	public static final String PROP_LCALIST_UNIT = "bcpg:lcaListUnit";
	/** Constant <code>PROP_LCALIST_VALUE="bcpg:lcaListValue"</code> */
	public static final String PROP_LCALIST_VALUE = "bcpg:lcaListValue";
	/** Constant <code>PROP_LCALIST_PREVIOUS_VALUE="bcpg:lcaListPreviousValue"</code> */
	public static final String PROP_LCALIST_PREVIOUS_VALUE = "bcpg:lcaListPreviousValue";
	/** Constant <code>PROP_LCALIST_FUTURE_VALUE="bcpg:lcaListFutureValue"</code> */
	public static final String PROP_LCALIST_FUTURE_VALUE = "bcpg:lcaListFutureValue";

	// Ingredient Extended
	/** Constant <code>PROP_ING_CEECODE="bcpg:ingCEECode"</code> */
	public static final String PROP_ING_CEECODE = "bcpg:ingCEECode";
	/** Constant <code>PROP_ING_CASCODE="bcpg:ingCASCode"</code> */
	public static final String PROP_ING_CASCODE = "bcpg:ingCASCode";
	/** Constant <code>ASPECT_ING_TYPE="bcpg:ingTypeAspect"</code> */
	public static final String ASPECT_ING_TYPE = "bcpg:ingTypeAspect";
	/** Constant <code>PROP_ING_TYPE_DEC_THRESHOLD="bcpg:ingTypeDecThreshold"</code> */
	public static final String PROP_ING_TYPE_DEC_THRESHOLD = "bcpg:ingTypeDecThreshold";
	
	// Nut
	/** Constant <code>PROP_NUTGROUP="bcpg:nutGroup"</code> */
	public static final String PROP_NUTGROUP = "bcpg:nutGroup";
	/** Constant <code>PROP_NUTTYPE="bcpg:nutType"</code> */
	public static final String PROP_NUTTYPE = "bcpg:nutType";
	/** Constant <code>PROP_NUTUNIT="bcpg:nutUnit"</code> */
	public static final String PROP_NUTUNIT = "bcpg:nutUnit";
	/** Constant <code>PROP_NUTGDA="bcpg:nutGDA"</code> */
	public static final String PROP_NUTGDA = "bcpg:nutGDA";
	/** Constant <code>PROP_NUTUL="bcpg:nutUL"</code> */
	public static final String PROP_NUTUL = "bcpg:nutUL";
	/** Constant <code>PROP_NUT_FORMULA="bcpg:nutFormula"</code> */
	public static final String PROP_NUT_FORMULA = "bcpg:nutFormula";
	
	// Supplier/Client Aspects
	/** Constant <code>ASPECT_SUPPLIERS="bcpg:suppliersAspect"</code> */
	public static final String ASPECT_SUPPLIERS = "bcpg:suppliersAspect";
	/** Constant <code>ASSOC_SUPPLIERS="bcpg:suppliers"</code> */
	public static final String ASSOC_SUPPLIERS = "bcpg:suppliers";
	/** Constant <code>ASSOC_SUPPLIER_PLANTS="bcpg:supplierPlants"</code> */
	public static final String ASSOC_SUPPLIER_PLANTS = "bcpg:supplierPlants";
	/** Constant <code>PROP_SUPPLIER_STATE="bcpg:supplierState"</code> */
	public static final String PROP_SUPPLIER_STATE = "bcpg:supplierState";
	/** Constant <code>PROP_EXTERNAL_ACCESS_GROUP="bcpg:externalAccessGroup"</code> */
	public static final String PROP_EXTERNAL_ACCESS_GROUP = "bcpg:externalAccessGroup";
	/** Constant <code>ASPECT_SUPPLIERS_ACCOUNTREF="bcpg:supplierAccountRefAspect"</code> */
	public static final String ASPECT_SUPPLIERS_ACCOUNTREF = "bcpg:supplierAccountRefAspect";
	/** Constant <code>ASSOC_SUPPLIER_ACCOUNTS="bcpg:supplierAccountRef"</code> */
	public static final String ASSOC_SUPPLIER_ACCOUNTS = "bcpg:supplierAccountRef";
	/** Constant <code>ASPECT_CLIENTS="bcpg:clientsAspect"</code> */
	public static final String ASPECT_CLIENTS = "bcpg:clientsAspect";
	/** Constant <code>ASSOC_CLIENTS="bcpg:clients"</code> */
	public static final String ASSOC_CLIENTS = "bcpg:clients";
	/** Constant <code>PROP_CLIENT_STATE="bcpg:clientState"</code> */
	public static final String PROP_CLIENT_STATE = "bcpg:clientState";
	
	// Product Aspect
	/** Constant <code>ASPECT_PRODUCT="bcpg:productAspect"</code> */
	public static final String ASPECT_PRODUCT = "bcpg:productAspect";
	/** Constant <code>PROP_PRODUCT_HIERARCHY1="bcpg:productHierarchy1"</code> */
	public static final String PROP_PRODUCT_HIERARCHY1 = "bcpg:productHierarchy1";
	/** Constant <code>PROP_PRODUCT_HIERARCHY2="bcpg:productHierarchy2"</code> */
	public static final String PROP_PRODUCT_HIERARCHY2 = "bcpg:productHierarchy2";
	/** Constant <code>PROP_PRODUCT_STATE="bcpg:productState"</code> */
	public static final String PROP_PRODUCT_STATE = "bcpg:productState";
	/** Constant <code>PROP_PRODUCT_UNIT="bcpg:productUnit"</code> */
	public static final String PROP_PRODUCT_UNIT = "bcpg:productUnit";
	/** Constant <code>PROP_PRODUCT_QTY="bcpg:productQty"</code> */
	public static final String PROP_PRODUCT_QTY = "bcpg:productQty";
	/** Constant <code>PROP_PRODUCT_DENSITY="bcpg:productDensity"</code> */
	public static final String PROP_PRODUCT_DENSITY = "bcpg:productDensity";
	/** Constant <code>PROP_PRODUCT_COMMENTS="bcpg:productComments"</code> */
	public static final String PROP_PRODUCT_COMMENTS = "bcpg:productComments";
	/** Constant <code>PROP_PRODUCT_SCORE="bcpg:productScores"</code> */
	public static final String PROP_PRODUCT_SCORE = "bcpg:productScores";
	/** Constant <code>PROP_PRODUCT_DROP_PACKAGING_OF_COMPONENTS="bcpg:dropPackagingOfComponents"</code> */
	public static final String PROP_PRODUCT_DROP_PACKAGING_OF_COMPONENTS = "bcpg:dropPackagingOfComponents";
	/** Constant <code>PROP_PRODUCT_SERVING_SIZE="bcpg:servingSize"</code> */
	public static final String PROP_PRODUCT_SERVING_SIZE = "bcpg:servingSize";
	/** Constant <code>PROP_PRODUCT_SERVING_SIZE_BY_COUNTRY="bcpg:servingSizeByCountry"</code> */
	public static final String PROP_PRODUCT_SERVING_SIZE_BY_COUNTRY = "bcpg:servingSizeByCountry";
	/** Constant <code>PROP_PRODUCT_SERVING_SIZE_UNIT="bcpg:servingSizeUnit"</code> */
	public static final String PROP_PRODUCT_SERVING_SIZE_UNIT = "bcpg:servingSizeUnit";
	/** Constant <code>PROP_NUTRIENT_PREPARED_UNIT="bcpg:nutrientPreparedUnit"</code> */
	public static final String PROP_NUTRIENT_PREPARED_UNIT = "bcpg:nutrientPreparedUnit";
	/** Constant <code>PROP_PRODUCT_NET_VOLUME="bcpg:netVolume"</code> */
	public static final String PROP_PRODUCT_NET_VOLUME = "bcpg:netVolume";
	/** Constant <code>PROP_PRODUCT_NET_WEIGHT="bcpg:netWeight"</code> */
	public static final String PROP_PRODUCT_NET_WEIGHT = "bcpg:netWeight";
	/** Constant <code>PROP_PRODUCT_COMPO_QTY_USED="bcpg:productCompoQtyUsed"</code> */
	public static final String PROP_PRODUCT_COMPO_QTY_USED = "bcpg:productCompoQtyUsed";
	/** Constant <code>PROP_PRODUCT_COMPO_VOLUME_USED="bcpg:productCompoVolumeUsed"</code> */
	public static final String PROP_PRODUCT_COMPO_VOLUME_USED = "bcpg:productCompoVolumeUsed";
	
	// Transformation Aspect
	/** Constant <code>ASPECT_TRANSFORMATION="bcpg:transformationAspect"</code> */
	public static final String ASPECT_TRANSFORMATION = "bcpg:transformationAspect";
	/** Constant <code>ASSOC_PRODUCT_SPECIFICATIONS="bcpg:productSpecifications"</code> */
	public static final String ASSOC_PRODUCT_SPECIFICATIONS = "bcpg:productSpecifications";
	
	// EAN Aspect
	/** Constant <code>ASPECT_EAN="bcpg:eanAspect"</code> */
	public static final String ASPECT_EAN = "bcpg:eanAspect";
	/** Constant <code>PROP_EAN_CODE="bcpg:eanCode"</code> */
	public static final String PROP_EAN_CODE = "bcpg:eanCode";
	
	// Profitability Aspect
	/** Constant <code>ASPECT_PROFITABILITY="bcpg:profitabilityAspect"</code> */
	public static final String ASPECT_PROFITABILITY = "bcpg:profitabilityAspect";
	/** Constant <code>PROP_UNIT_PRICE="bcpg:unitPrice"</code> */
	public static final String PROP_UNIT_PRICE = "bcpg:unitPrice";
	/** Constant <code>PROP_BREAK_EVEN="bcpg:breakEven"</code> */
	public static final String PROP_BREAK_EVEN = "bcpg:breakEven";
	/** Constant <code>PROP_PROJECTED_QTY="bcpg:projectedQty"</code> */
	public static final String PROP_PROJECTED_QTY = "bcpg:projectedQty";
	/** Constant <code>PROP_PROFITABILITY="bcpg:profitability"</code> */
	public static final String PROP_PROFITABILITY = "bcpg:profitability";
	/** Constant <code>PROP_UNIT_TOTAL_COST="bcpg:unitTotalCost"</code> */
	public static final String PROP_UNIT_TOTAL_COST = "bcpg:unitTotalCost";
	/** Constant <code>PROP_PRICE_CURRENCY="bcpg:priceCurrency"</code> */
	public static final String PROP_PRICE_CURRENCY = "bcpg:priceCurrency";
	
	// Manufacturing Aspect
	/** Constant <code>ASPECT_MANUFACTURING="bcpg:manufacturingAspect"</code> */
	public static final String ASPECT_MANUFACTURING = "bcpg:manufacturingAspect";
	/** Constant <code>ASSOC_SUBSIDIARY="bcpg:subsidiaryRef"</code> */
	public static final String ASSOC_SUBSIDIARY = "bcpg:subsidiaryRef";
	/** Constant <code>ASSOC_PLANTS="bcpg:plants"</code> */
	public static final String ASSOC_PLANTS = "bcpg:plants";
	/** Constant <code>ASSOC_LABORATORIES="bcpg:laboratories"</code> */
	public static final String ASSOC_LABORATORIES = "bcpg:laboratories";
	/** Constant <code>ASSOC_TRADEMARK="bcpg:trademarkRef"</code> */
	public static final String ASSOC_TRADEMARK = "bcpg:trademarkRef";
	/** Constant <code>ASSOC_SUBSIDIARY_CERTIFICATIONS="bcpg:subsidiaryCertifications"</code> */
	public static final String ASSOC_SUBSIDIARY_CERTIFICATIONS = "bcpg:subsidiaryCertifications";
	/** Constant <code>ASSOC_PLANT_CERTIFICATIONS="bcpg:plantCertifications"</code> */
	public static final String ASSOC_PLANT_CERTIFICATIONS = "bcpg:plantCertifications";
	/** Constant <code>PROP_TRADEMARK_TYPE="bcpg:trademarkType"</code> */
	public static final String PROP_TRADEMARK_TYPE = "bcpg:trademarkType";
	
	// Storage and Precaution
	/** Constant <code>ASSOC_STORAGE_CONDITIONS="bcpg:storageConditionsRef"</code> */
	public static final String ASSOC_STORAGE_CONDITIONS = "bcpg:storageConditionsRef";
	/** Constant <code>ASSOC_PRECAUTION_OF_USE="bcpg:precautionOfUseRef"</code> */
	public static final String ASSOC_PRECAUTION_OF_USE = "bcpg:precautionOfUseRef";
	
	// Instruction Aspect
	/** Constant <code>ASPECT_INSTRUCTION="bcpg:instructionAspect"</code> */
	public static final String ASPECT_INSTRUCTION = "bcpg:instructionAspect";
	/** Constant <code>PROP_INSTRUCTION="bcpg:instruction"</code> */
	public static final String PROP_INSTRUCTION = "bcpg:instruction";
	
	// Reconstitutable Aspect
	/** Constant <code>ASPECT_RECONSTITUTABLE="bcpg:reconstitutableAspect"</code> */
	public static final String ASPECT_RECONSTITUTABLE = "bcpg:reconstitutableAspect";
	/** Constant <code>PROP_RECONSTITUTION_RATE="bcpg:reconstitutionRate"</code> */
	public static final String PROP_RECONSTITUTION_RATE = "bcpg:reconstitutionRate";
	/** Constant <code>PROP_RECONSTITUTION_PRIORITY="bcpg:reconstitutionPriority"</code> */
	public static final String PROP_RECONSTITUTION_PRIORITY = "bcpg:reconstitutionPriority";
	/** Constant <code>ASSOC_DILUENT_REF="bcpg:diluentRef"</code> */
	public static final String ASSOC_DILUENT_REF = "bcpg:diluentRef";
	/** Constant <code>ASSOC_TARGET_RECONSTITUTION_REF="bcpg:targetReconstitutionRef"</code> */
	public static final String ASSOC_TARGET_RECONSTITUTION_REF = "bcpg:targetReconstitutionRef";
	
	// Compare Aspect
	/** Constant <code>ASPECT_COMPARE_WITH_DYN_COLUMN="bcpg:compareWithDynColumnAspect"</code> */
	public static final String ASPECT_COMPARE_WITH_DYN_COLUMN = "bcpg:compareWithDynColumnAspect";
	/** Constant <code>PROP_COMPARE_WITH_DYN_COLUMN="bcpg:compareWithDynColumn"</code> */
	public static final String PROP_COMPARE_WITH_DYN_COLUMN = "bcpg:compareWithDynColumn";
	
	// Nutrient Profiling Score Aspect
	/** Constant <code>ASPECT_NUTRIENT_PROFILING_SCORE="bcpg:nutrientProfilingScoreAspect"</code> */
	public static final String ASPECT_NUTRIENT_PROFILING_SCORE = "bcpg:nutrientProfilingScoreAspect";
	/** Constant <code>PROP_NUTRIENT_PROFILING_SCORE="bcpg:nutrientProfilingScore"</code> */
	public static final String PROP_NUTRIENT_PROFILING_SCORE = "bcpg:nutrientProfilingScore";
	/** Constant <code>PROP_NUTRIENT_PROFILING_CLASS="bcpg:nutrientProfilingClass"</code> */
	public static final String PROP_NUTRIENT_PROFILING_CLASS = "bcpg:nutrientProfilingClass";
	/** Constant <code>PROP_NUTRIENT_PROFILE_SCORE_FORMULA="bcpg:nutrientProfileScoreFormula"</code> */
	public static final String PROP_NUTRIENT_PROFILE_SCORE_FORMULA = "bcpg:nutrientProfileScoreFormula";
	/** Constant <code>PROP_NUTRIENT_PROFILE_CLASS_FORMULA="bcpg:nutrientProfileClassFormula"</code> */
	public static final String PROP_NUTRIENT_PROFILE_CLASS_FORMULA = "bcpg:nutrientProfileClassFormula";
	/** Constant <code>PROP_NUTRIENT_PROFILING_DETAILS="bcpg:nutrientProfilingDetails"</code> */
	public static final String PROP_NUTRIENT_PROFILING_DETAILS = "bcpg:nutrientProfilingDetails";
	/** Constant <code>PROP_NUTRIENT_PROFILE_CATEGORY="bcpg:nutrientProfileCategory"</code> */
	public static final String PROP_NUTRIENT_PROFILE_CATEGORY = "bcpg:nutrientProfileCategory";
	/** Constant <code>PROP_NUTRIENT_PROFILE_VERSION="bcpg:nutrientProfileVersion"</code> */
	public static final String PROP_NUTRIENT_PROFILE_VERSION = "bcpg:nutrientProfileVersion";
	
	// Eco Score Aspect
	/** Constant <code>ASPECT_ECO_SCORE="bcpg:ecoScoreAspect"</code> */
	public static final String ASPECT_ECO_SCORE = "bcpg:ecoScoreAspect";
	/** Constant <code>PROP_ECO_SCORE="bcpg:ecoScore"</code> */
	public static final String PROP_ECO_SCORE = "bcpg:ecoScore";
	/** Constant <code>PROP_ECO_SCORE_CLASS="bcpg:ecoScoreClass"</code> */
	public static final String PROP_ECO_SCORE_CLASS = "bcpg:ecoScoreClass";
	/** Constant <code>PROP_ECO_SCORE_CATEGORY="bcpg:ecoScoreCategory"</code> */
	public static final String PROP_ECO_SCORE_CATEGORY = "bcpg:ecoScoreCategory";
	/** Constant <code>PROP_ECO_SCORE_DETAILS="bcpg:ecoScoreDetails"</code> */
	public static final String PROP_ECO_SCORE_DETAILS = "bcpg:ecoScoreDetails";
	
	// Customs Code Aspect
	/** Constant <code>PROP_CUSTOMSCODE_CODE="bcpg:cstsCode"</code> */
	public static final String PROP_CUSTOMSCODE_CODE = "bcpg:cstsCode";
	/** Constant <code>ASPECT_CUSTOMSCODE="bcpg:customsCodeAspect"</code> */
	public static final String ASPECT_CUSTOMSCODE = "bcpg:customsCodeAspect";
	/** Constant <code>ASSOC_CUSTOMSCODE="bcpg:customsCodeRef"</code> */
	public static final String ASSOC_CUSTOMSCODE = "bcpg:customsCodeRef";
	
	// Regulatory Code Aspect
	/** Constant <code>ASPECT_REGULATORY_CODE="bcpg:regulatoryCodeAspect"</code> */
	public static final String ASPECT_REGULATORY_CODE = "bcpg:regulatoryCodeAspect";
	/** Constant <code>ASPECT_REGULATORY="bcpg:regulatoryAspect"</code> */
	public static final String ASPECT_REGULATORY = "bcpg:regulatoryAspect";
	/** Constant <code>PROP_REGULATORY_RESULT="bcpg:regulatoryResult"</code> */
	public static final String PROP_REGULATORY_RESULT = "bcpg:regulatoryResult";
	
	// Tox Properties
	/** Constant <code>PROP_TOX_TYPES="bcpg:toxTypes"</code> */
	public static final String PROP_TOX_TYPES = "bcpg:toxTypes";
	/** Constant <code>PROP_TOX_VALUE="bcpg:toxValue"</code> */
	public static final String PROP_TOX_VALUE = "bcpg:toxValue";
	/** Constant <code>PROP_TOX_ING_ING="bcpg:toxIngIng"</code> */
	public static final String PROP_TOX_ING_ING = "bcpg:toxIngIng";
	/** Constant <code>PROP_TOX_ING_TOX="bcpg:toxIngTox"</code> */
	public static final String PROP_TOX_ING_TOX = "bcpg:toxIngTox";
	/** Constant <code>PROP_TOX_ING_MAX_VALUE="bcpg:toxIngMaxValue"</code> */
	public static final String PROP_TOX_ING_MAX_VALUE = "bcpg:toxIngMaxValue";
	/** Constant <code>PROP_TOX_ING_SYSTEMIC_VALUE="bcpg:toxIngSystemicValue"</code> */
	public static final String PROP_TOX_ING_SYSTEMIC_VALUE = "bcpg:toxIngSystemicValue";
	/** Constant <code>PROP_TOX_CALCULATE_SYSTEMIC="bcpg:toxCalculateSystemic"</code> */
	public static final String PROP_TOX_CALCULATE_SYSTEMIC = "bcpg:toxCalculateSystemic";
	/** Constant <code>PROP_TOX_CALCULATE_MAX="bcpg:toxCalculateMax"</code> */
	public static final String PROP_TOX_CALCULATE_MAX = "bcpg:toxCalculateMax";
	/** Constant <code>PROP_ING_TOX_DATA="bcpg:ingToxData"</code> */
	public static final String PROP_ING_TOX_DATA = "bcpg:ingToxData";
	/** Constant <code>PROP_ING_TOX_SYSTEMIC_VALUES="bcpg:ingToxSystemicValues"</code> */
	public static final String PROP_ING_TOX_SYSTEMIC_VALUES = "bcpg:ingToxSystemicValues";
	/** Constant <code>PROP_ING_TOX_MAX_VALUES="bcpg:ingToxMaxValues"</code> */
	public static final String PROP_ING_TOX_MAX_VALUES = "bcpg:ingToxMaxValues";
	/** Constant <code>PROP_ING_TOX_POD_SYSTEMIC="bcpg:ingToxPodSystemic"</code> */
	public static final String PROP_ING_TOX_POD_SYSTEMIC = "bcpg:ingToxPodSystemic";
	/** Constant <code>PROP_ING_TOX_DERMAL_ABSORPTION="bcpg:ingToxDermalAbsorption"</code> */
	public static final String PROP_ING_TOX_DERMAL_ABSORPTION = "bcpg:ingToxDermalAbsorption";
	/** Constant <code>PROP_ING_TOX_ORAL_ABSORPTION="bcpg:ingToxOralAbsorption"</code> */
	public static final String PROP_ING_TOX_ORAL_ABSORPTION = "bcpg:ingToxOralAbsorption";
	/** Constant <code>PROP_TOX_ABSORPTION_TYPE="bcpg:toxAbsorptionType"</code> */
	public static final String PROP_TOX_ABSORPTION_TYPE = "bcpg:toxAbsorptionType";
	/** Constant <code>PROP_ING_TOX_MOS_MOE="bcpg:ingToxMosMoe"</code> */
	public static final String PROP_ING_TOX_MOS_MOE = "bcpg:ingToxMosMoe";
	/** Constant <code>PROP_ING_TOX_MAX_SKIN_IRRITATION_RINSE_OFF="bcpg:ingToxMaxSkinIrritationRinseOff"</code> */
	public static final String PROP_ING_TOX_MAX_SKIN_IRRITATION_RINSE_OFF = "bcpg:ingToxMaxSkinIrritationRinseOff";
	/** Constant <code>PROP_ING_TOX_MAX_SKIN_IRRITATION_LEAVE_ON="bcpg:ingToxMaxSkinIrritationLeaveOn"</code> */
	public static final String PROP_ING_TOX_MAX_SKIN_IRRITATION_LEAVE_ON = "bcpg:ingToxMaxSkinIrritationLeaveOn";
	/** Constant <code>PROP_ING_TOX_MAX_SENSITIZATION="bcpg:ingToxMaxSensitization"</code> */
	public static final String PROP_ING_TOX_MAX_SENSITIZATION = "bcpg:ingToxMaxSensitization";
	/** Constant <code>PROP_ING_TOX_MAX_OCULAR_IRRITATION="bcpg:ingToxMaxOcularIrritation"</code> */
	public static final String PROP_ING_TOX_MAX_OCULAR_IRRITATION = "bcpg:ingToxMaxOcularIrritation";
	/** Constant <code>PROP_ING_TOX_MAX_PHOTOTOXIC="bcpg:ingToxMaxPhototoxic"</code> */
	public static final String PROP_ING_TOX_MAX_PHOTOTOXIC = "bcpg:ingToxMaxPhototoxic";
	
	// Chemical Codes
	/** Constant <code>PROP_EC_NUMBER="bcpg:ecNumber"</code> */
	public static final String PROP_EC_NUMBER = "bcpg:ecNumber";
	/** Constant <code>PROP_FDA_NUMBER="bcpg:fdaNumber"</code> */
	public static final String PROP_FDA_NUMBER = "bcpg:fdaNumber";
	/** Constant <code>PROP_FEMA_NUMBER="bcpg:femaNumber"</code> */
	public static final String PROP_FEMA_NUMBER = "bcpg:femaNumber";
	/** Constant <code>PROP_FL_NUMBER="bcpg:flNumber"</code> */
	public static final String PROP_FL_NUMBER = "bcpg:flNumber";
	
	// Water and Evaporable Aspects
	/** Constant <code>ASPECT_WATER="bcpg:waterAspect"</code> */
	public static final String ASPECT_WATER = "bcpg:waterAspect";
	/** Constant <code>ASPECT_EVAPORABLE="bcpg:evaporableAspect"</code> */
	public static final String ASPECT_EVAPORABLE = "bcpg:evaporableAspect";
	/** Constant <code>PROP_EVAPORATED_RATE="bcpg:evaporatedRate"</code> */
	public static final String PROP_EVAPORATED_RATE = "bcpg:evaporatedRate";
	
	// Labeling Rule Aspect
	/** Constant <code>LABELING_RULE_ASPECT="bcpg:labelingRuleAspect"</code> */
	public static final String LABELING_RULE_ASPECT = "bcpg:labelingRuleAspect";
	/** Constant <code>PROP_MODIFIED_CATALOG1="bcpg:modifiedCatalog1"</code> */
	public static final String PROP_MODIFIED_CATALOG1 = "bcpg:modifiedCatalog1";
	/** Constant <code>PROP_MODIFIED_CATALOG2="bcpg:modifiedCatalog2"</code> */
	public static final String PROP_MODIFIED_CATALOG2 = "bcpg:modifiedCatalog2";
	/** Constant <code>PROP_MODIFIED_CATALOG3="bcpg:modifiedCatalog3"</code> */
	public static final String PROP_MODIFIED_CATALOG3 = "bcpg:modifiedCatalog3";
	
	// GLOP Properties
	/** Constant <code>PROP_GLOP_TARGET="bcpg:glopTarget"</code> */
	public static final String PROP_GLOP_TARGET = "bcpg:glopTarget";
	/** Constant <code>PROP_GLOP_VALUE="bcpg:glopValue"</code> */
	public static final String PROP_GLOP_VALUE = "bcpg:glopValue";
	/** Constant <code>ASPECT_GLOP_PRODUCT="bcpg:glopProductAspect"</code> */
	public static final String ASPECT_GLOP_PRODUCT = "bcpg:glopProductAspect";
	
	// Propagate Up Aspect
	/** Constant <code>PROP_IS_CHARACT_PROPAGATE_UP="bcpg:isCharactPropagateUp"</code> */
	public static final String PROP_IS_CHARACT_PROPAGATE_UP = "bcpg:isCharactPropagateUp";
	/** Constant <code>ASPECT_PROPAGATE_UP="bcpg:propagateUpAspect"</code> */
	public static final String ASPECT_PROPAGATE_UP = "bcpg:propagateUpAspect";
	/** Constant <code>ASSOC_PROPAGATED_CHARACTS="bcpg:propagatedCharacts"</code> */
	public static final String ASSOC_PROPAGATED_CHARACTS = "bcpg:propagatedCharacts";
	
	// Bio Origin
	/** Constant <code>PROP_BIO_ORIGIN_TYPE="bcpg:bioOriginType"</code> */
	public static final String PROP_BIO_ORIGIN_TYPE = "bcpg:bioOriginType";

	// --- Common Paths ---
	/** Constant <code>PATH_DOCUMENTS="Documents"</code> */
	public static final String PATH_DOCUMENTS = "Documents";
	/** Constant <code>PATH_SUPPLIER_DOCUMENTS="SupplierDocuments"</code> */
	public static final String PATH_SUPPLIER_DOCUMENTS = "SupplierDocuments"; 
	/** Constant <code>PATH_IMAGES="Images"</code> */
	public static final String PATH_IMAGES = "Images";
	/** Constant <code>PATH_INGS="/app:company_home/cm:System/cm:Characts"{trunked}</code> */
	public static final String PATH_INGS = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:Ings";
	/** Constant <code>PATH_REGULATORY_USAGES="/app:company_home/cm:System/cm:Characts"{trunked}</code> */
	public static final String PATH_REGULATORY_USAGES = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:RegulatoryUsages";
	/** Constant <code>PATH_GEO_ORIGINS="/app:company_home/cm:System/cm:Characts"{trunked}</code> */
	public static final String PATH_GEO_ORIGINS = "/app:company_home/cm:System/cm:Characts/bcpg:entityLists/cm:GeoOrigins";
}
