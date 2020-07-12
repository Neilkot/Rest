package com.bharath.restws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.bharath.restws.exceptions.PatientBusinessException;
import com.bharath.restws.model.Patient;

@Service
public class PatienServiceImpl implements PatientService {

	Map<Long, Patient> patients = new HashMap<>();
	long currentId = 123;

	public PatienServiceImpl() {
		init();
	}

	void init() {
		Patient patient = new Patient();
		patient.setId(currentId);
		patient.setName("John");
		patients.put(patient.getId(), patient);
	}

	@Override
	public List<Patient> getPatients() {
		Collection<Patient> results = patients.values();
		return new ArrayList<Patient>(results);

	}

	@Override
	public Patient getPatient(long id) {
		if( patients.get(id) == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return patients.get(id);
	}

	@Override
	public Response createPatient(Patient patient) {
		patient.setId(++currentId);
		patients.put(patient.getId(), patient);
		return Response.ok(patient).build();
	}

	@Override
	public Response updatePatients(Patient patient) {
		Response response;
		Patient currentPatient = patients.get(patient.getId());
		if (currentPatient != null) {
			patients.put(patient.getId(), patient);
			 response = Response.ok().build();
		} else {
			throw new PatientBusinessException();
		}
		return response;
	}

	@Override
	public Response deletePatient(long id) {
		Response response;

		if (patients.remove(id) == null) {
			response = Response.notModified().build();
		} else {
			response = Response.ok().build();
		}
		return response;
	}
}
