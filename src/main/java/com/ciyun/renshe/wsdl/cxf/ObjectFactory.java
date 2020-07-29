
package com.ciyun.renshe.wsdl.cxf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ciyun.renshe.wsdl.cxf package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CloseSession_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "closeSession");
    private final static QName _CloseSessionResponse_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "closeSessionResponse");
    private final static QName _Deliver_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "deliver");
    private final static QName _DeliverResponse_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "deliverResponse");
    private final static QName _OpenSession_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "openSession");
    private final static QName _OpenSessionResponse_QNAME = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "openSessionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ciyun.renshe.wsdl.cxf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CloseSession }
     * 
     */
    public CloseSession createCloseSession() {
        return new CloseSession();
    }

    /**
     * Create an instance of {@link CloseSessionResponse }
     * 
     */
    public CloseSessionResponse createCloseSessionResponse() {
        return new CloseSessionResponse();
    }

    /**
     * Create an instance of {@link Deliver }
     * 
     */
    public Deliver createDeliver() {
        return new Deliver();
    }

    /**
     * Create an instance of {@link DeliverResponse }
     * 
     */
    public DeliverResponse createDeliverResponse() {
        return new DeliverResponse();
    }

    /**
     * Create an instance of {@link OpenSession }
     * 
     */
    public OpenSession createOpenSession() {
        return new OpenSession();
    }

    /**
     * Create an instance of {@link OpenSessionResponse }
     * 
     */
    public OpenSessionResponse createOpenSessionResponse() {
        return new OpenSessionResponse();
    }

    /**
     * Create an instance of {@link RobotRequest }
     * 
     */
    public RobotRequest createRobotRequest() {
        return new RobotRequest();
    }

    /**
     * Create an instance of {@link UserAttribute }
     * 
     */
    public UserAttribute createUserAttribute() {
        return new UserAttribute();
    }

    /**
     * Create an instance of {@link RobotResponse }
     * 
     */
    public RobotResponse createRobotResponse() {
        return new RobotResponse();
    }

    /**
     * Create an instance of {@link RobotCommand }
     * 
     */
    public RobotCommand createRobotCommand() {
        return new RobotCommand();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSession }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CloseSession }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "closeSession")
    public JAXBElement<CloseSession> createCloseSession(CloseSession value) {
        return new JAXBElement<CloseSession>(_CloseSession_QNAME, CloseSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSessionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CloseSessionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "closeSessionResponse")
    public JAXBElement<CloseSessionResponse> createCloseSessionResponse(CloseSessionResponse value) {
        return new JAXBElement<CloseSessionResponse>(_CloseSessionResponse_QNAME, CloseSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Deliver }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Deliver }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "deliver")
    public JAXBElement<Deliver> createDeliver(Deliver value) {
        return new JAXBElement<Deliver>(_Deliver_QNAME, Deliver.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeliverResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeliverResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "deliverResponse")
    public JAXBElement<DeliverResponse> createDeliverResponse(DeliverResponse value) {
        return new JAXBElement<DeliverResponse>(_DeliverResponse_QNAME, DeliverResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpenSession }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OpenSession }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "openSession")
    public JAXBElement<OpenSession> createOpenSession(OpenSession value) {
        return new JAXBElement<OpenSession>(_OpenSession_QNAME, OpenSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpenSessionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link OpenSessionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.eastrobot.cn/ws/RobotServiceEx", name = "openSessionResponse")
    public JAXBElement<OpenSessionResponse> createOpenSessionResponse(OpenSessionResponse value) {
        return new JAXBElement<OpenSessionResponse>(_OpenSessionResponse_QNAME, OpenSessionResponse.class, null, value);
    }

}
