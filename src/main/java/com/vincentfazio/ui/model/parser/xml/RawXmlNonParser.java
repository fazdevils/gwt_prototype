package com.vincentfazio.ui.model.parser.xml;

public class RawXmlNonParser extends XmlParser<String> {

	@Override
	public String parse(String rawXml) {
		return rawXml;
	}

	@Override
	public String createXml(String rawXml) {
		return rawXml;
	}

}
