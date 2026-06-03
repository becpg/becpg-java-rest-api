package fr.becpg.api.model;

/**
 * Defines constants for the beCPG Project model (pjt namespace) QNames:
 * Types, Aspects, Properties, and Associations.
 * Prevents hardcoding string literals throughout the application.
 *
 * @author matthieu
 */
public final class ProjectAPIModel
{

	private ProjectAPIModel()
	{
		// Private constructor to prevent instantiation of this utility class
	}

	// --- Project Types ---
	/** Constant <code>TYPE_PROJECT="pjt:project"</code> */
	public static final String TYPE_PROJECT = "pjt:project";
	/** Constant <code>TYPE_TASK_LIST="pjt:taskList"</code> */
	public static final String TYPE_TASK_LIST = "pjt:taskList";
	/** Constant <code>TYPE_DELIVERABLE_LIST="pjt:deliverableList"</code> */
	public static final String TYPE_DELIVERABLE_LIST = "pjt:deliverableList";
	/** Constant <code>TYPE_BUDGET_LIST="pjt:budgetList"</code> */
	public static final String TYPE_BUDGET_LIST = "pjt:budgetList";
	/** Constant <code>TYPE_SCORE_LIST="pjt:scoreList"</code> */
	public static final String TYPE_SCORE_LIST = "pjt:scoreList";
	/** Constant <code>TYPE_LOG_TIME_LIST="pjt:logTimeList"</code> */
	public static final String TYPE_LOG_TIME_LIST = "pjt:logTimeList";
	/** Constant <code>TYPE_INVOICE_LIST="pjt:invoiceList"</code> */
	public static final String TYPE_INVOICE_LIST = "pjt:invoiceList";

	// --- Project Properties ---
	/** Constant <code>PROP_PROJECT_STATE="pjt:projectState"</code> */
	public static final String PROP_PROJECT_STATE = "pjt:projectState";
	/** Constant <code>PROP_PROJECT_START_DATE="pjt:projectStartDate"</code> */
	public static final String PROP_PROJECT_START_DATE = "pjt:projectStartDate";
	/** Constant <code>PROP_PROJECT_DUE_DATE="pjt:projectDueDate"</code> */
	public static final String PROP_PROJECT_DUE_DATE = "pjt:projectDueDate";
	/** Constant <code>PROP_PROJECT_COMPLETION_DATE="pjt:projectCompletionDate"</code> */
	public static final String PROP_PROJECT_COMPLETION_DATE = "pjt:projectCompletionDate";
	/** Constant <code>PROP_PROJECT_PRIORITY="pjt:projectPriority"</code> */
	public static final String PROP_PROJECT_PRIORITY = "pjt:projectPriority";
	/** Constant <code>PROP_PROJECT_PLANNING_MODE="pjt:projectPlanningMode"</code> */
	public static final String PROP_PROJECT_PLANNING_MODE = "pjt:projectPlanningMode";
	/** Constant <code>PROP_PROJECT_SPONSOR="pjt:projectSponsor"</code> */
	public static final String PROP_PROJECT_SPONSOR = "pjt:projectSponsor";
	/** Constant <code>PROP_PROJECT_ORIGIN="pjt:projectOrigin"</code> */
	public static final String PROP_PROJECT_ORIGIN = "pjt:projectOrigin";
	/** Constant <code>PROP_PROJECT_OVERDUE="pjt:projectOverdue"</code> */
	public static final String PROP_PROJECT_OVERDUE = "pjt:projectOverdue";
	/** Constant <code>PROP_PROJECT_BUDGETED_COST="pjt:projectBudgetedCost"</code> */
	public static final String PROP_PROJECT_BUDGETED_COST = "pjt:projectBudgetedCost";
	/** Constant <code>PROP_PROJECT_WORK="pjt:projectWork"</code> */
	public static final String PROP_PROJECT_WORK = "pjt:projectWork";
	/** Constant <code>PROP_PROJECT_LOGGED_TIME="pjt:projectLoggedTime"</code> */
	public static final String PROP_PROJECT_LOGGED_TIME = "pjt:projectLoggedTime";
	/** Constant <code>PROP_PROJECT_OWNERS="pjt:projectOwners"</code> */
	public static final String PROP_PROJECT_OWNERS = "pjt:projectOwners";

	// --- Project completion Aspect ---
	/** Constant <code>PROP_COMPLETION_PERCENT="pjt:completionPercent"</code> */
	public static final String PROP_COMPLETION_PERCENT = "pjt:completionPercent";

	// --- Project Associations ---
	/** Constant <code>ASSOC_PROJECT_MANAGER="pjt:projectManager"</code> */
	public static final String ASSOC_PROJECT_MANAGER = "pjt:projectManager";
	/** Constant <code>ASSOC_PROJECT_OBSERVERS="pjt:projectObservers"</code> */
	public static final String ASSOC_PROJECT_OBSERVERS = "pjt:projectObservers";
	/** Constant <code>ASSOC_PROJECT_ENTITY="pjt:projectEntity"</code> */
	public static final String ASSOC_PROJECT_ENTITY = "pjt:projectEntity";
	/** Constant <code>ASSOC_PROJECT_CURRENT_TASKS="pjt:projectCurrentTasks"</code> */
	public static final String ASSOC_PROJECT_CURRENT_TASKS = "pjt:projectCurrentTasks";

	// --- Task List Properties ---
	/** Constant <code>PROP_TL_TASK_NAME="pjt:tlTaskName"</code> */
	public static final String PROP_TL_TASK_NAME = "pjt:tlTaskName";
	/** Constant <code>PROP_TL_TASK_DESCRIPTION="pjt:tlTaskDescription"</code> */
	public static final String PROP_TL_TASK_DESCRIPTION = "pjt:tlTaskDescription";
	/** Constant <code>PROP_TL_TASK_COMMENT="pjt:tlTaskComment"</code> */
	public static final String PROP_TL_TASK_COMMENT = "pjt:tlTaskComment";
	/** Constant <code>PROP_TL_STATE="pjt:tlState"</code> */
	public static final String PROP_TL_STATE = "pjt:tlState";
	/** Constant <code>PROP_TL_DURATION="pjt:tlDuration"</code> */
	public static final String PROP_TL_DURATION = "pjt:tlDuration";
	/** Constant <code>PROP_TL_REAL_DURATION="pjt:tlRealDuration"</code> */
	public static final String PROP_TL_REAL_DURATION = "pjt:tlRealDuration";
	/** Constant <code>PROP_TL_CAPACITY="pjt:tlCapacity"</code> */
	public static final String PROP_TL_CAPACITY = "pjt:tlCapacity";
	/** Constant <code>PROP_TL_WORK="pjt:tlWork"</code> */
	public static final String PROP_TL_WORK = "pjt:tlWork";
	/** Constant <code>PROP_TL_LOGGED_TIME="pjt:tlLoggedTime"</code> */
	public static final String PROP_TL_LOGGED_TIME = "pjt:tlLoggedTime";
	/** Constant <code>PROP_TL_START="pjt:tlStart"</code> */
	public static final String PROP_TL_START = "pjt:tlStart";
	/** Constant <code>PROP_TL_END="pjt:tlEnd"</code> */
	public static final String PROP_TL_END = "pjt:tlEnd";
	/** Constant <code>PROP_TL_DUE="pjt:tlDue"</code> */
	public static final String PROP_TL_DUE = "pjt:tlDue";
	/** Constant <code>PROP_TL_TARGET_START="pjt:tlTargetStart"</code> */
	public static final String PROP_TL_TARGET_START = "pjt:tlTargetStart";
	/** Constant <code>PROP_TL_TARGET_END="pjt:tlTargetEnd"</code> */
	public static final String PROP_TL_TARGET_END = "pjt:tlTargetEnd";
	/** Constant <code>PROP_TL_IS_MILESTONE="pjt:tlIsMilestone"</code> */
	public static final String PROP_TL_IS_MILESTONE = "pjt:tlIsMilestone";
	/** Constant <code>PROP_TL_IS_CRITICAL="pjt:tlIsCritical"</code> */
	public static final String PROP_TL_IS_CRITICAL = "pjt:tlIsCritical";
	/** Constant <code>PROP_TL_IS_GROUP="pjt:tlIsGroup"</code> */
	public static final String PROP_TL_IS_GROUP = "pjt:tlIsGroup";
	/** Constant <code>PROP_TL_IS_REFUSED="pjt:tlIsRefused"</code> */
	public static final String PROP_TL_IS_REFUSED = "pjt:tlIsRefused";
	/** Constant <code>PROP_TL_MANUAL_DATE="pjt:tlManualDate"</code> */
	public static final String PROP_TL_MANUAL_DATE = "pjt:tlManualDate";
	/** Constant <code>PROP_TL_FIXED_COST="pjt:tlFixedCost"</code> */
	public static final String PROP_TL_FIXED_COST = "pjt:tlFixedCost";
	/** Constant <code>PROP_TL_WORKFLOW_NAME="pjt:tlWorkflowName"</code> */
	public static final String PROP_TL_WORKFLOW_NAME = "pjt:tlWorkflowName";
	/**
	 * Indexed noderef property used for AFTS filtering on task resources.
	 * Constant <code>PROP_TL_RESOURCES_ASSOC_INDEX="pjt:tlResourcesAssocIndex"</code>
	 */
	public static final String PROP_TL_RESOURCES_ASSOC_INDEX = "pjt:tlResourcesAssocIndex";

	// --- Task List Associations ---
	/** Constant <code>ASSOC_TL_RESOURCES="pjt:tlResources"</code> */
	public static final String ASSOC_TL_RESOURCES = "pjt:tlResources";
	/** Constant <code>ASSOC_TL_OBSERVERS="pjt:tlObservers"</code> */
	public static final String ASSOC_TL_OBSERVERS = "pjt:tlObservers";
	/** Constant <code>ASSOC_TL_PREV_TASKS="pjt:tlPrevTasks"</code> */
	public static final String ASSOC_TL_PREV_TASKS = "pjt:tlPrevTasks";
	/**
	 * Alias kept for compatibility with pjt:tlPrevTasks property-style access.
	 * Constant <code>PROP_TL_PREV_TASKS="pjt:tlPrevTasks"</code>
	 */
	public static final String PROP_TL_PREV_TASKS = "pjt:tlPrevTasks";
	/** Constant <code>ASSOC_TL_REFUSED_TASK_REF="pjt:tlRefusedTaskRef"</code> */
	public static final String ASSOC_TL_REFUSED_TASK_REF = "pjt:tlRefusedTaskRef";
	/** Constant <code>ASSOC_TL_REFUSED_TASKS_TO_REOPEN="pjt:tlRefusedTasksToReopen"</code> */
	public static final String ASSOC_TL_REFUSED_TASKS_TO_REOPEN = "pjt:tlRefusedTasksToReopen";
	/** Constant <code>ASSOC_TL_BUDGET_REF="pjt:tlBudgetRef"</code> */
	public static final String ASSOC_TL_BUDGET_REF = "pjt:tlBudgetRef";
	/** Constant <code>ASSOC_TL_TASK_LEGEND="pjt:tlTaskLegend"</code> */
	public static final String ASSOC_TL_TASK_LEGEND = "pjt:tlTaskLegend";

	// --- Deliverable List Properties ---
	/** Constant <code>PROP_DL_DESCRIPTION="pjt:dlDescription"</code> */
	public static final String PROP_DL_DESCRIPTION = "pjt:dlDescription";
	/** Constant <code>PROP_DL_STATE="pjt:dlState"</code> */
	public static final String PROP_DL_STATE = "pjt:dlState";
	/** Constant <code>PROP_DL_SCRIPT_EXEC_ORDER="pjt:dlScriptExecOrder"</code> */
	public static final String PROP_DL_SCRIPT_EXEC_ORDER = "pjt:dlScriptExecOrder";
	/** Constant <code>PROP_DL_URL="pjt:dlUrl"</code> */
	public static final String PROP_DL_URL = "pjt:dlUrl";

	// --- Deliverable List Associations ---
	/** Constant <code>ASSOC_DL_TASK="pjt:dlTask"</code> */
	public static final String ASSOC_DL_TASK = "pjt:dlTask";
	/** Constant <code>ASSOC_DL_CONTENT="pjt:dlContent"</code> */
	public static final String ASSOC_DL_CONTENT = "pjt:dlContent";

	// --- Score List Properties ---
	/** Constant <code>PROP_SL_WEIGHT="pjt:slWeight"</code> */
	public static final String PROP_SL_WEIGHT = "pjt:slWeight";
	/** Constant <code>PROP_SL_SCORE="pjt:slScore"</code> */
	public static final String PROP_SL_SCORE = "pjt:slScore";
	/** Constant <code>PROP_SL_SCREENING="pjt:slScreening"</code> */
	public static final String PROP_SL_SCREENING = "pjt:slScreening";
	/** Constant <code>PROP_SL_SCORE_RANGE="pjt:slScoreRange"</code> */
	public static final String PROP_SL_SCORE_RANGE = "pjt:slScoreRange";
	/** Constant <code>PROP_SL_CRITERION="pjt:slCriterion"</code> */
	public static final String PROP_SL_CRITERION = "pjt:slCriterion";

	// --- Score List Associations ---
	/** Constant <code>ASSOC_SL_SCORE_CRITERION="pjt:slScoreCriterion"</code> */
	public static final String ASSOC_SL_SCORE_CRITERION = "pjt:slScoreCriterion";

	// --- Log Time List Properties ---
	/** Constant <code>PROP_LTL_DATE="pjt:ltlDate"</code> */
	public static final String PROP_LTL_DATE = "pjt:ltlDate";
	/** Constant <code>PROP_LTL_TIME="pjt:ltlTime"</code> */
	public static final String PROP_LTL_TIME = "pjt:ltlTime";
	/** Constant <code>PROP_LTL_COMMENT="pjt:ltlComment"</code> */
	public static final String PROP_LTL_COMMENT = "pjt:ltlComment";
	/** Constant <code>PROP_LTL_TYPE="pjt:ltlType"</code> */
	public static final String PROP_LTL_TYPE = "pjt:ltlType";

	// --- Log Time List Associations ---
	/** Constant <code>ASSOC_LTL_TASK="pjt:ltlTask"</code> */
	public static final String ASSOC_LTL_TASK = "pjt:ltlTask";

	// --- Budget List Properties ---
	/** Constant <code>PROP_BL_BUDGETED_EXPENSE="pjt:blBudgetedExpense"</code> */
	public static final String PROP_BL_BUDGETED_EXPENSE = "pjt:blBudgetedExpense";
	/** Constant <code>PROP_BL_BUDGETED_INVOICE="pjt:blBudgetedInvoice"</code> */
	public static final String PROP_BL_BUDGETED_INVOICE = "pjt:blBudgetedInvoice";
	/** Constant <code>PROP_BL_ITEM="pjt:blItem"</code> */
	public static final String PROP_BL_ITEM = "pjt:blItem";
	/** Constant <code>PROP_BL_PROFIT="pjt:blProfit"</code> */
	public static final String PROP_BL_PROFIT = "pjt:blProfit";

	// --- Invoice List Properties ---
	/** Constant <code>PROP_IL_INVOICE_NAME="pjt:ilInvoiceName"</code> */
	public static final String PROP_IL_INVOICE_NAME = "pjt:ilInvoiceName";
	/** Constant <code>PROP_IL_DATE="pjt:ilDate"</code> */
	public static final String PROP_IL_DATE = "pjt:ilDate";
	/** Constant <code>PROP_IL_INVOICE="pjt:invoice"</code> */
	public static final String PROP_IL_INVOICE = "pjt:invoice";

	// --- Invoice List Associations ---
	/** Constant <code>ASSOC_IL_BUDGET_REF="pjt:ilBudgetRef"</code> */
	public static final String ASSOC_IL_BUDGET_REF = "pjt:ilBudgetRef";
	/** Constant <code>ASSOC_IL_TASK_REF="pjt:ilTaskRef"</code> */
	public static final String ASSOC_IL_TASK_REF = "pjt:ilTaskRef";

	// --- Project State Values ---
	/** Constant <code>STATE_PLANNED="Planned"</code> */
	public static final String STATE_PLANNED = "Planned";
	/** Constant <code>STATE_IN_PROGRESS="InProgress"</code> */
	public static final String STATE_IN_PROGRESS = "InProgress";
	/** Constant <code>STATE_ON_HOLD="OnHold"</code> */
	public static final String STATE_ON_HOLD = "OnHold";
	/** Constant <code>STATE_CANCELLED="Cancelled"</code> */
	public static final String STATE_CANCELLED = "Cancelled";
	/** Constant <code>STATE_COMPLETED="Completed"</code> */
	public static final String STATE_COMPLETED = "Completed";
	/** Constant <code>STATE_REFUSED="Refused"</code> */
	public static final String STATE_REFUSED = "Refused";
}
