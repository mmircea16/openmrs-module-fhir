/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fhir.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu.resource.Patient;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.api.BundleEntry;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import java.util.List;

public class PatientServiceTest extends BaseModuleContextSensitiveTest {

    protected static final String PAT_INITIAL_DATA_XML = "org/openmrs/api/include/PatientServiceTest-createPatient.xml";
    protected static final String PAT_SEARCH_DATA_XML = "org/openmrs/api/include/PatientServiceTest-findPatients.xml";


    public PatientService getService() {
        return Context.getService(PatientService.class);
    }

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(PAT_INITIAL_DATA_XML);
        executeDataSet(PAT_SEARCH_DATA_XML);
    }

    @Test
    public void shouldSetupContext() {
        assertNotNull(getService());
    }

    @Test
    public void getPatient_shouldReturnResourceIfExists(){
        String patientUuid = "61b38324-e2fd-4feb-95b7-9e9a2a4400df";
        Patient fhirPatient = getService().getPatient(patientUuid);
        assertNotNull(fhirPatient);
        assertEquals(fhirPatient.getId().toString(),patientUuid);

    }

    @Test
    public void getPatient_shouldReturnOperationOutcomeIfDoesNotExist(){

    }

    @Test
    public void getPatientsById_shouldReturnBundleIfExists(){
        String patientUuid = "61b38324-e2fd-4feb-95b7-9e9a2a4400df";
        Bundle bundle = getService().getPatientsById(patientUuid);

        assertNotNull(bundle);
        assertEquals(bundle.getEntries().size(), 1);

        BundleEntry entry = bundle.getEntries().get(0);
        IResource resource = entry.getResource();

        assertNotNull(resource);
        assertTrue(resource instanceof Patient);
        Patient fhirPatient = (Patient) resource;
        assertEquals(fhirPatient.getId().toString(),patientUuid);
    }

    @Test
    public void getPatientsById_shouldReturnEmptyBundleIfDoesNotExist(){

    }

    @Test
    public void getPatientsByIdentifier_shouldReturnBundleIfExists(){

    }

    @Test
    public void getPatientsByIdentifier_shouldReturnEmptyBundleIfDoesNotExist(){

    }




}
