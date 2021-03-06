package org.openmrs.module.pihmalawi.validator.impl;

import org.openmrs.Location;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.LocationService;
import org.openmrs.module.pihmalawi.models.YendaNafePatientRegistrationModel;
import org.openmrs.module.pihmalawi.patient.ExtendedPatientService;
import org.openmrs.module.pihmalawi.validator.YendaNafePatientRegistrationValidator;
import org.openmrs.module.reporting.common.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  Class implements validation adding patient from medic mobile data model for patient registration
 */
@Component
public class YendaNafePatientRegistrationValidatorImpl implements YendaNafePatientRegistrationValidator {
    public String validateRegistrationModel(YendaNafePatientRegistrationModel yendaNafePatientRequestBody, ExtendedPatientService extendedPatientService, LocationService locationService)
    {
        PatientIdentifier patientIdentifierInDB = extendedPatientService.getPatientIdentifierByIdentifier(yendaNafePatientRequestBody._id);
        if(patientIdentifierInDB != null)
        {
            return "_id "+ yendaNafePatientRequestBody._id +" already exist in the system. _id has to be unique";
        }
        if(yendaNafePatientRequestBody.name.equals(""))
        {
            return "Name of patient not given.";
        }

        String[] parts = yendaNafePatientRequestBody.name.split(" ", 2);
        if(parts.length == 1)
        {
            return "Name is incomplete. Please provide first name and last name.";
        }
        String sex = yendaNafePatientRequestBody.sex.toLowerCase().trim();
        if(!(sex.equals("male") || sex.equals("female")  || sex.equals("unknown")))
        {
            return "Invalid sex given "+ yendaNafePatientRequestBody.sex+". Sex should be male, female or unknown.";
        }
        String birthdate = yendaNafePatientRequestBody.date_of_birth;
        if(birthdate.trim().equals("") || birthdate.trim().isEmpty())
        {
            return "Date of birth is not given.";
        }
        if(birthdate.length() > 0)
        {
            try {
                //Do these if there's a value in the birthdate string
                Date birthdateFormatted = (Date)  DateUtil.parseYmd(birthdate);

            }
            catch (Exception ex) {
                return "Invalid date format. Given date is "+ birthdate+".";
            }
        }
        String locationUUID = yendaNafePatientRequestBody.location_uuid.trim();
        Location locationInDB = locationService.getLocationByUuid(locationUUID);
        if(locationInDB == null)
        {
            return "Given location uuid "+ locationUUID +" does not exist in the EMR";
        }
        if(!(yendaNafePatientRequestBody.ncds.trim().equals("")) || !yendaNafePatientRequestBody.ncds_other.trim().equals("") ||
                !yendaNafePatientRequestBody.mental_health_type.trim().equals("") || !yendaNafePatientRequestBody.mental_health_type_other.trim().equals("") ||
                !yendaNafePatientRequestBody.eid_emr_id_source.trim().equals("") || !yendaNafePatientRequestBody.eid_emr_id_source_other.trim().equals("")||
                !yendaNafePatientRequestBody.art_emr_id_source.trim().equals("") || !yendaNafePatientRequestBody.art_emr_id_source_other.trim().equals("") ||
                !yendaNafePatientRequestBody.ncd_emr_id_source.equals("") || !yendaNafePatientRequestBody.ncd_emr_id_source_other.equals("") ||
                !yendaNafePatientRequestBody.eid_id.trim().equals("") || !yendaNafePatientRequestBody.art_id.trim().equals("") ||
                !yendaNafePatientRequestBody.ncd_id.trim().equals(""))
        {
            return "User is registered in EMR";
        }
        return "";
    }
}
