<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>PIH Malawi Customizations</h2>

<h3>HIV Reports</h3>
HIV Weekly Outcome Report: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_hivweeklyoutcome.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_hivweeklyoutcome.form">Remove</a>
<br/>

Pre-ART Weekly Report: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_preartweekly.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_preartweekly.form">Remove</a>
<br/>

HIV Program Changes Report: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_hivprogramchanges.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_hivprogramchanges.form">Remove</a>
<br/>

ARV Quarterly: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_arvquarterly.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_arvquarterly.form">Remove</a>
<br/>

<h4>Upper Neno</h4>
ART Missed Appointments: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_artmissedappointment.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_artmissedappointment.form">Remove</a>
<br/>

Pre-ART Missed Appointments: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_partmissedappointment.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_partmissedappointment.form">Remove</a>
<br/>

<h4>Lower Neno</h4>
ART Missed Appointments Lower Neno: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_artmissedappointment_lowerneno.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_artmissedappointment_lowerneno.form">Remove</a>
<br/>

Pre-ART Missed Appointments Lower Neno: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_partmissedappointment_lowerneno.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_partmissedappointment_lowerneno.form">Remove</a>
<br/>

<h3>HIV Data cleanup</h3>
HIV Data Quality: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_hivdataquality.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_hivdataquality.form">Remove</a>
<br/>

Duplicate HIV Patients: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_duplicatehivpatients.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_duplicatehivpatients.form">Remove</a>
<br/>

<h3>EMR Summary</h3>
Weekly Encounter Report: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_weeklyencounter.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_weeklyencounter.form">Remove</a>
<br/>

<h3>Chronic Care</h3>
Chronic Care Missed Appointments: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_chroniccaremissedappointment.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_chroniccaremissedappointment.form">Remove</a>
<br/>

Chronic Care Register: <a href="${pageContext.request.contextPath}/module/pihmalawi/register_chroniccareregister.form">(Re) register</a>
<a href="${pageContext.request.contextPath}/module/pihmalawi/remove_chroniccareregister.form">Remove</a>
<br/>

<%@ include file="/WEB-INF/template/footer.jsp"%>