package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Drug;
public class DrugListHandler extends DefaultHandler {
	private List<Drug> drugList = null;
	private Drug drug;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (drug != null) { 
            String valueString = new String(ch, start, length); 
            if ("drugId".equals(tempString)) 
            	drug.setDrugId(new Integer(valueString).intValue());
            else if ("drugName".equals(tempString)) 
            	drug.setDrugName(valueString); 
            else if ("unit".equals(tempString)) 
            	drug.setUnit(valueString); 
            else if ("price".equals(tempString)) 
            	drug.setPrice(new Float(valueString).floatValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Drug".equals(localName)&&drug!=null){
			drugList.add(drug);
			drug = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		drugList = new ArrayList<Drug>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Drug".equals(localName)) {
            drug = new Drug(); 
        }
        tempString = localName; 
	}

	public List<Drug> getDrugList() {
		return this.drugList;
	}
}
