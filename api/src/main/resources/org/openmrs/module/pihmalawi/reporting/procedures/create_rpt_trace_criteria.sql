DELIMITER $$

/************************************************************************
  Get the trace criteria for each patient
*************************************************************************/

DROP PROCEDURE IF EXISTS create_rpt_trace_criteria;
$$
CREATE PROCEDURE create_rpt_trace_criteria(IN _endDate DATE, IN _location VARCHAR(255), IN _minWks INT, IN _maxWks INT, IN _phase1Only BOOLEAN) BEGIN

  DROP TEMPORARY TABLE IF EXISTS rpt_trace_criteria;
  CREATE TEMPORARY TABLE rpt_trace_criteria (
    patient_id        INT NOT NULL,
    criteria          VARCHAR(50)
  );
  CREATE INDEX rpt_trace_criteria_patient_id_idx ON rpt_trace_criteria(patient_id);

  -- Late ART
  INSERT INTO rpt_trace_criteria(patient_id, criteria)
    SELECT  patient_id, 'LATE_ART'
    FROM    rpt_active_art
    WHERE   days_late_appt IS NOT NULL
    AND     days_late_appt >= (_minWks*7)
    AND     (_maxWks IS NULL OR days_late_appt < (_maxWks*7))
  ;

  -- Late EID
  INSERT INTO rpt_trace_criteria(patient_id, criteria)
    SELECT  patient_id, 'LATE_EID'
    FROM    rpt_active_eid
    WHERE   days_late_appt IS NOT NULL
    AND     days_late_appt >= (_minWks*7)
    AND     (_maxWks IS NULL OR days_late_appt < (_maxWks*7))
  ;

  -- High Viral Load
  -- 2 wk: Active patients who have a viral load > 1000 with entry date <= 14 days
  -- 6 wk: Active patients who have a viral load > 1000 with entry date <= 56 days and no subsequent visit
  INSERT INTO   rpt_trace_criteria(patient_id, criteria)
    SELECT      r.patient_id, 'HIGH_VIRAL_LOAD'
    FROM        rpt_active_art r
    INNER JOIN  mw_lab_tests t on r.patient_id = t.patient_id
    WHERE       t.lab_test_id = latest_test_result_by_date_entered(r.patient_id, 'Viral Load', _endDate)
    AND         (
                  ( _minWks = 2 AND
                    datediff(_endDate, t.date_result_entered) <= 14
                    AND t.result_numeric > 1000
                  )
                  OR
                  ( _minWks = 6 AND
                    datediff(_endDate, t.date_result_entered) <= 56
                    AND t.result_numeric > 1000
                    AND t.date_result_entered > r.last_visit_date
                  )
                )
  ;

END
$$

DELIMITER ;