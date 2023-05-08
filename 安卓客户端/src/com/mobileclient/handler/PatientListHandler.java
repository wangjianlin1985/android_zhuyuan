package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Patient;
public class PatientListHandler extends DefaultHandler {
	private List<Patient> patientList = null;
	private Patient patient;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (patient != null) { 
            String valueString = new String(ch, start, length); 
            if ("patiendId".equals(tempString)) 
            	patient.setPatiendId(new Integer(valueString).intValue());
            else if ("name".equals(tempString)) 
            	patient.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	patient.setSex(valueString); 
            else if ("birthday".equals(tempString)) 
            	patient.setBirthday(Timestamp.valueOf(valueString));
            else if ("cardNo".equals(tempString)) 
            	patient.setCardNo(valueString); 
            else if ("originPlace".equals(tempString)) 
            	patient.setOriginPlace(valueString); 
            else if ("telephone".equals(tempString)) 
            	patient.setTelephone(valueString); 
            else if ("address".equals(tempString)) 
            	patient.setAddress(valueString); 
            else if ("caseHistory".equals(tempString)) 
            	patient.setCaseHistory(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Patient".equals(localName)&&patient!=null){
			patientList.add(patient);
			patient = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		patientList = new ArrayList<Patient>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Patient".equals(localName)) {
            patient = new Patient(); 
        }
        tempString = localName; 
	}

	public List<Patient> getPatientList() {
		return this.patientList;
	}
}
