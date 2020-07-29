package com.ciyun.renshe.wsdl.cxf;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 3.3.4
 * 2020-07-06T09:47:30.445+08:00
 * Generated source version: 3.3.4
 *
 */
@WebServiceClient(name = "RobotServiceEx",
                  wsdlLocation = "http://192.168.149.153:8000/robot/ws/RobotServiceEx?wsdl",
                  targetNamespace = "http://www.eastrobot.cn/ws/RobotServiceEx")
public class RobotServiceEx_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "RobotServiceEx");
    public final static QName DefaultRobotServiceExPort = new QName("http://www.eastrobot.cn/ws/RobotServiceEx", "DefaultRobotServiceExPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.149.153:8000/robot/ws/RobotServiceEx?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RobotServiceEx_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://192.168.149.153:8000/robot/ws/RobotServiceEx?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RobotServiceEx_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RobotServiceEx_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RobotServiceEx_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public RobotServiceEx_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public RobotServiceEx_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public RobotServiceEx_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns RobotServiceEx
     */
    @WebEndpoint(name = "DefaultRobotServiceExPort")
    public RobotServiceEx getDefaultRobotServiceExPort() {
        return super.getPort(DefaultRobotServiceExPort, RobotServiceEx.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RobotServiceEx
     */
    @WebEndpoint(name = "DefaultRobotServiceExPort")
    public RobotServiceEx getDefaultRobotServiceExPort(WebServiceFeature... features) {
        return super.getPort(DefaultRobotServiceExPort, RobotServiceEx.class, features);
    }

}
