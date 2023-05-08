package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Treat;
public class TreatListHandler extends DefaultHandler {
	private List<Treat> treatList = null;
	private Treat treat;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (treat != null) { 
            String valueString = new String(ch, start, length); 
            if ("treatId".equals(tempString)) 
            	treat.setTreatId(new Integer(valueString).intValue());
            else if ("treatName".equals(tempString)) 
            	treat.setTreatName(valueString); 
            else if ("patientObj".equals(tempString)) 
            	treat.setPatientObj(new Integer(valueString).intValue());
            else if ("diagnosis".equals(tempString)) 
            	treat.setDiagnosis(valueString); 
            else if ("treatContent".equals(tempString)) 
            	treat.setTreatContent(valueString); 
            else if ("treatResult".equals(tempString)) 
            	treat.setTreatResult(valueString); 
            else if ("doctorObj".equals(tempString)) 
            	treat.setDoctorObj(valueString); 
            else if ("startTime".equals(tempString)) 
            	treat.setStartTime(valueString); 
            else if ("timeLong".equals(tempString)) 
            	treat.setTimeLong(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Treat".equals(localName)&&treat!=null){
			treatList.add(treat);
			treat = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		treatList = new ArrayList<Treat>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Treat".equals(localName)) {
            treat = new Treat(); 
        }
        tempString = localName; 
	}

	public List<Treat> getTreatList() {
		return this.treatList;
	}
}
