package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ZhuYuan;
public class ZhuYuanListHandler extends DefaultHandler {
	private List<ZhuYuan> zhuYuanList = null;
	private ZhuYuan zhuYuan;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (zhuYuan != null) { 
            String valueString = new String(ch, start, length); 
            if ("zhuyuanId".equals(tempString)) 
            	zhuYuan.setZhuyuanId(new Integer(valueString).intValue());
            else if ("patientObj".equals(tempString)) 
            	zhuYuan.setPatientObj(new Integer(valueString).intValue());
            else if ("age".equals(tempString)) 
            	zhuYuan.setAge(new Integer(valueString).intValue());
            else if ("inDate".equals(tempString)) 
            	zhuYuan.setInDate(Timestamp.valueOf(valueString));
            else if ("inDays".equals(tempString)) 
            	zhuYuan.setInDays(new Integer(valueString).intValue());
            else if ("bedNum".equals(tempString)) 
            	zhuYuan.setBedNum(valueString); 
            else if ("doctorObj".equals(tempString)) 
            	zhuYuan.setDoctorObj(valueString); 
            else if ("memo".equals(tempString)) 
            	zhuYuan.setMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ZhuYuan".equals(localName)&&zhuYuan!=null){
			zhuYuanList.add(zhuYuan);
			zhuYuan = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		zhuYuanList = new ArrayList<ZhuYuan>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ZhuYuan".equals(localName)) {
            zhuYuan = new ZhuYuan(); 
        }
        tempString = localName; 
	}

	public List<ZhuYuan> getZhuYuanList() {
		return this.zhuYuanList;
	}
}
