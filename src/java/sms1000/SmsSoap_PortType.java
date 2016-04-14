/**
 * SmsSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sms1000;

public interface SmsSoap_PortType extends java.rmi.Remote {
    public String helloWorld() throws java.rmi.RemoteException;
    public String doGetInfo(String uUsername, String uPassword) throws java.rmi.RemoteException;
    public String doSendSMS(String uUsername, String uPassword, String uNumber, String uCellphones, String uMessage, boolean uFarsi, boolean uTopic, boolean uFlash, String uUDH) throws java.rmi.RemoteException;
    public String doSendArraySMS(String uUsername, String uPassword, String[] uNumber, String[] uCellphone, String[] uMessage, boolean[] uFarsi, boolean[] uTopic, boolean[] uFlash, String[] uUDH) throws java.rmi.RemoteException;
    public String doGetDelivery(String uUsername, String uReturnIDs) throws java.rmi.RemoteException;
    public String doReceiveSMS(String uUsername, String uPassword, long uLastRowID) throws java.rmi.RemoteException;
    public sms1000.MessageReceive[] doReceiveSMSArray(String uUsername, String uPassword, long uLastRowID) throws java.rmi.RemoteException;
    public String getInfoXML(String uUsername, String uPassword) throws java.rmi.RemoteException;
}
