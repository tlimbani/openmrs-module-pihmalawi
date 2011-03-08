package org.openmrs.module.pihmalawi.reporting.extension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.Cohort;
import org.openmrs.Location;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflowState;

public class HibernatePihMalawiQueryDao {

	protected static final Log log = LogFactory
			.getLog(HibernatePihMalawiQueryDao.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	public Cohort getPatientsInStatesAtLocation(
			List<ProgramWorkflowState> programWorkflowStates, Date onOrAfter,
			Date onOrBefore, Location location) {
		
		List<Location> locationList = new ArrayList<Location>();
		if(location != null) {
			locationList.add(location);
		}
		
		return getPatientsInStatesAtLocations(programWorkflowStates, onOrAfter, onOrBefore, locationList);
	}
	
	public Cohort getPatientsInStateAtLocations(
			ProgramWorkflowState programWorkflowState, Date onOrAfter,
			Date onOrBefore, List<Location> locations) {
		
		List<ProgramWorkflowState> programWorkflowStateList = new ArrayList<ProgramWorkflowState>();
		if(programWorkflowState != null) {
			programWorkflowStateList.add(programWorkflowState);
		}
		
		return getPatientsInStatesAtLocations(programWorkflowStateList, onOrAfter, onOrBefore, locations);
	}
	
	public Cohort getPatientsInStateAtLocation(
			ProgramWorkflowState programWorkflowState, Date onOrAfter,
			Date onOrBefore, Location location) {
		
		List<ProgramWorkflowState> programWorkflowStateList = new ArrayList<ProgramWorkflowState>();
		if(programWorkflowState != null) {
			programWorkflowStateList.add(programWorkflowState);
		}
		
		return getPatientsInStatesAtLocation(programWorkflowStateList, onOrAfter, onOrBefore, location);
	}

	public Cohort getPatientsInStatesAtLocations(
			List<ProgramWorkflowState> programWorkflowStates, Date onOrAfter,
			Date onOrBefore, List<Location> locations) {
		
		List<Integer> stateIds = new ArrayList<Integer>();
		if(programWorkflowStates != null) {
			for (ProgramWorkflowState state : programWorkflowStates) {
				stateIds.add(state.getId());
			}
		}
		
		List<Integer> locationIds = new ArrayList<Integer>();
		if(locations != null) {
			for (Location location : locations) {
				locationIds.add(location.getId());
			}
		}

		// Create SQL query
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.patient_id ");
		sql.append("from patient_state ps ");
		sql.append("  inner join patient_program pp on ps.patient_program_id = pp.patient_program_id ");
		sql.append("  inner join patient p on pp.patient_id = p.patient_id ");
		sql.append("where ps.voided = false and pp.voided = false and p.voided = false ");

		// optional clauses
		if (stateIds != null && !stateIds.isEmpty())
			sql.append(" and ps.state in (:stateIds) ");
		if (onOrAfter != null)
			sql.append(" and (ps.end_date is null or ps.end_date >= :onOrAfter) ");
		if (onOrBefore != null)
			sql.append(" and (ps.start_date is null or ps.start_date <= :onOrBefore) ");
		if (locationIds != null && !locationIds.isEmpty())
			sql.append(" and pp.location_id in (:locationIds) ");

		sql.append(" group by pp.patient_id ");

		// Execute query
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (stateIds != null && !stateIds.isEmpty())
			query.setParameterList("stateIds", stateIds);
		if (onOrAfter != null)
			query.setDate("onOrAfter", onOrAfter);
		if (onOrBefore != null)
			query.setDate("onOrBefore", onOrBefore);
		if (locationIds != null && !locationIds.isEmpty())
			query.setParameterList("locationIds", locationIds);
		
		return new Cohort(query.list());
	}

	public Cohort getPatientsHavingStatesAtLocation(
			ProgramWorkflowState programWorkflowState, Date startedOnOrAfter,
			Date startedOnOrBefore, Date endedOnOrAfter, Date endedOnOrBefore,
			Location location) {
		
		List<Integer> stateIds = new ArrayList<Integer>();
		stateIds.add(programWorkflowState.getId());

		// Create SQL query
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.patient_id ");
		sql.append("from patient_state ps ");
		sql.append("  inner join patient_program pp on ps.patient_program_id = pp.patient_program_id ");
		sql.append("  inner join patient p on pp.patient_id = p.patient_id ");
		sql.append("where ps.voided = false and pp.voided = false and p.voided = false ");

		// Create a list of clauses
		if (stateIds != null && !stateIds.isEmpty())
			sql.append(" and ps.state in (:stateIds) ");
		if (startedOnOrAfter != null)
			sql.append(" and ps.start_date >= :startedOnOrAfter ");
		if (startedOnOrBefore != null)
			sql.append(" and ps.start_date <= :startedOnOrBefore ");
		if (endedOnOrAfter != null)
			sql.append(" and ps.end_date >= :endedOnOrAfter ");
		if (endedOnOrBefore != null)
			sql.append(" and ps.end_date <= :endedOnOrBefore ");
		if (location != null)
			sql.append(" and pp.location_id = :location ");

		sql.append(" group by pp.patient_id ");
		log.debug("query: " + sql);

		// Execute query
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());

		if (stateIds != null && !stateIds.isEmpty())
			query.setParameterList("stateIds", stateIds);
		if (startedOnOrAfter != null)
			query.setDate("startedOnOrAfter", startedOnOrAfter);
		if (startedOnOrBefore != null)
			query.setDate("startedOnOrBefore", startedOnOrBefore);
		if (endedOnOrAfter != null)
			query.setDate("endedOnOrAfter", endedOnOrAfter);
		if (endedOnOrBefore != null)
			query.setDate("endedOnOrBefore", endedOnOrBefore);
		if (location != null)
			query.setInteger("location", location.getId());

		return new Cohort(query.list());
	}

	public Cohort getPatientsInProgramAtLocation(List<Program> programs,
			Date onOrAfter, Date onOrBefore, Location location) {
		List<Integer> programIds = new ArrayList<Integer>();
		for (Program program : programs)
			programIds.add(program.getProgramId());

		// Create SQL query
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.patient_id ");
		sql.append("from patient_program pp ");
		sql.append("  inner join patient p on pp.patient_id = p.patient_id ");
		sql.append("where pp.voided = false and p.voided = false ");

		// optional clauses
		if (programIds != null && !programIds.isEmpty())
			sql.append(" and pp.program_id in (:programIds) ");
		if (onOrAfter != null)
			sql.append(" and (pp.date_completed is null or pp.date_completed >= :onOrAfter) ");
		if (onOrBefore != null)
			sql.append(" and (pp.date_enrolled is null or pp.date_enrolled <= :onOrBefore) ");
		if (location != null)
			sql.append(" and pp.location_id = :location ");

		sql.append(" group by pp.patient_id ");
		log.debug("query: " + sql);

		// Execute query
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				sql.toString());
		if (programIds != null && !programIds.isEmpty())
			query.setParameterList("programIds", programIds);
		if (onOrAfter != null)
			query.setDate("onOrAfter", onOrAfter);
		if (onOrBefore != null)
			query.setDate("onOrBefore", onOrBefore);
		if (location != null)
			query.setInteger("location", location.getId());
		return new Cohort(query.list());
	}

}