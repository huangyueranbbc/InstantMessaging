package com.hyr.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.3.0 Mon Oct 19 14:31:54 CST 2015
 * Generated source version: 2.3.0
 * 
 */

@WebService(targetNamespace = "http://webservice.hyr.com/", name = "HelloWebService")
@XmlSeeAlso(
{ ObjectFactory.class })
public interface HelloWebService
{

	@WebResult(name = "return", targetNamespace = "")
	@Action(input = "http://webservice.hyr.com/HelloWebService/sayHiRequest", output = "http://webservice.hyr.com/HelloWebService/sayHiResponse")
	@RequestWrapper(localName = "sayHi", targetNamespace = "http://webservice.hyr.com/", className = "com.hyr.webservice.SayHi")
	@WebMethod
	@ResponseWrapper(localName = "sayHiResponse", targetNamespace = "http://webservice.hyr.com/", className = "com.hyr.webservice.SayHiResponse")
	public java.lang.String sayHi(
			@WebParam(name = "arg0", targetNamespace = "") java.lang.String arg0);

	@WebResult(name = "return", targetNamespace = "")
	@Action(input = "http://webservice.hyr.com/HelloWebService/getCatByUserRequest", output = "http://webservice.hyr.com/HelloWebService/getCatByUserResponse")
	@RequestWrapper(localName = "getCatByUser", targetNamespace = "http://webservice.hyr.com/", className = "com.hyr.webservice.GetCatByUser")
	@WebMethod
	@ResponseWrapper(localName = "getCatByUserResponse", targetNamespace = "http://webservice.hyr.com/", className = "com.hyr.webservice.GetCatByUserResponse")
	public java.util.List<com.hyr.webservice.Cat> getCatByUser(
			@WebParam(name = "arg0", targetNamespace = "") com.hyr.webservice.User arg0);
}
