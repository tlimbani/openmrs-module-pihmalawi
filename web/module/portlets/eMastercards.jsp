<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib prefix="pihmalawi" uri="/WEB-INF/view/module/pihmalawi/taglib/pihmalawi.tld" %>

<c:set var="personId" value="${model.personId}" />
<c:set var="patientId" value="${model.patientId}" />


<table cellspacing="0" cellpadding="2">
	<tr>
		<td>ART Mastercard:</td>
		<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="57" initialEncounterTypeId="9" followupEncounterTypeId="10"/></td>
	</tr>
	<tr>
		<c:set var="artInitialEncounter" value="" />
		<openmrs:forEachEncounter encounters="${model.patientEncounters}"
			type="9" num="1" var="enc">
			<c:if test="${ not empty enc }">
				<c:set var="artInitialEncounter" value="true" />
			</c:if>
		</openmrs:forEachEncounter>
		<td>Pre-ART Mastercard:</td>
		<c:choose>
			<c:when test="${ not empty artInitialEncounter }">
				<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="58" initialEncounterTypeId="11" followupEncounterTypeId="12" readonly="true"/> (readonly because of ART Initial Encounter)</td>
			</c:when>
			<c:otherwise>
				<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="58" initialEncounterTypeId="11" followupEncounterTypeId="11"/></td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<c:set var="artInitialEncounter" value="" />
		<openmrs:forEachEncounter encounters="${model.patientEncounters}"
			type="9" num="1" var="enc">
			<c:if test="${ not empty enc }">
				<c:set var="artInitialEncounter" value="true" />
			</c:if>
		</openmrs:forEachEncounter>
		<td>EID Mastercard:</td>
		<c:choose>
			<c:when test="${ not empty artInitialEncounter }">
				<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="28" initialEncounterTypeId="20" followupEncounterTypeId="21" readonly="true"/> (readonly because of ART Initial Encounter)</td>
			</c:when>
			<c:otherwise>
				<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="28" initialEncounterTypeId="20" followupEncounterTypeId="21"/></td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td><br /></td>
	</tr>
	<tr>
		<td>Chronic Care Record:</td>
		<td><pihmalawi:eMastercardAccess patientId="${model.patientId}" formId="54" initialEncounterTypeId="67" followupEncounterTypeId="69"/></td>
	</tr>
	<tr>
		<td><br /></td>
	</tr>
	<tr>
		<td>TB Treatment Card:</td>
		<td>(todo)</td>
	</tr>
	<tr>
		<td><br /></td>
	</tr>
	<tr>
		<openmrs:forEachEncounter encounters="${model.patientEncounters}" type="24" num="1" var="enc">
			<c:if test="${ not empty enc }">
				<c:set var="ctEncounter" value="true" />
			</c:if>
		</openmrs:forEachEncounter>
		<openmrs:forEachEncounter encounters="${model.patientEncounters}" type="17" num="1" var="enc">
			<c:if test="${ not empty enc }">
				<c:set var="evaluationEncounter" value="true" />
			</c:if>
		</openmrs:forEachEncounter>
		<td>Kaposis Sarcoma Flowsheet:</td>
		<c:choose>
			<c:set var="eMastercardFormId" value="62" />
			<c:when test="${ evaluationEncounter || ctEncounter }">
				<td><a href="${pageContext.request.contextPath}/module/htmlformentry/htmlFormEntry.form?personId=${personId}&patientId=${patientId}&returnUrl=%2fopenmrs%2fpatientDashboard.form&formId=${eMastercardFormId}">Edit KS file</a></td>
			</c:when>
			<c:otherwise>
				<td><a href="${pageContext.request.contextPath}/module/htmlformentry/htmlFormEntry.form?personId=${personId}&patientId=${patientId}&returnUrl=%2fopenmrs%2fpatientDashboard.form&formId=${eMastercardFormId}">Create new KS file</a></td>
			</c:otherwise>
		</c:choose>
	</tr>
</table>
