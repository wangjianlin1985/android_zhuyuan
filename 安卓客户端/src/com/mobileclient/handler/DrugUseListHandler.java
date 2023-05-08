package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.DrugUse;
public class DrugUseListHandler extends DefaultHandler {
	private List<DrugUse> drugUseList = null;
	private DrugUse drugUse;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (drugUse != null) { 
            String valueString = new String(ch, start, length); 
            if ("drugUseId".equals(tempString)) 
            	drugUse.setDrugUseId(new Integer(valueString).intValue());
            else if ("treatObj".equals(tempString)) 
            	drugUse.setTreatObj(new Integer(valueString).intValue());
            else if ("drugObj".equals(tempString)) 
            	drugUse.setDrugObj(new Integer(valueString).intValue());
            else if ("drugCount".equals(tempString)) 
            	drugUse.setDrugCount(new Float(valueString).floatValue());
            else if ("drugMoney".equals(tempString)) 
            	drugUse.setDrugMoney(new Float(valueString).floatValue());
            else if ("useTime".equals(tempString)) 
            	drugUse.setUseTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("DrugUse".equals(localName)&&drugUse!=null){
			drugUseList.add(drugUse);
			drugUse = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		drugUseList = new ArrayList<DrugUse>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("DrugUse".equals(localName)) {
            drugUse = new DrugUse(); 
        }
        tempString = localName; 
	}

	public List<DrugUse> getDrugUseList() {
		return this.drugUseList;
	}
}
