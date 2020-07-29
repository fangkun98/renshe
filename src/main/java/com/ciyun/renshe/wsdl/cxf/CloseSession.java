
package com.ciyun.renshe.wsdl.cxf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>closeSession complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="closeSession"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="robotRequest" type="{http://www.eastrobot.cn/ws/RobotServiceEx}RobotRequest" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "closeSession", propOrder = {
    "robotRequest"
})
public class CloseSession {

    protected RobotRequest robotRequest;

    /**
     * 获取robotRequest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RobotRequest }
     *     
     */
    public RobotRequest getRobotRequest() {
        return robotRequest;
    }

    /**
     * 设置robotRequest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RobotRequest }
     *     
     */
    public void setRobotRequest(RobotRequest value) {
        this.robotRequest = value;
    }

}
