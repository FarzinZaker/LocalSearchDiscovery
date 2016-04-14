/**
 * Sms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sms1000;

public interface Sms extends javax.xml.rpc.Service {
    public String getsmsSoapAddress();

    public sms1000.SmsSoap_PortType getsmsSoap() throws javax.xml.rpc.ServiceException;

    public sms1000.SmsSoap_PortType getsmsSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
