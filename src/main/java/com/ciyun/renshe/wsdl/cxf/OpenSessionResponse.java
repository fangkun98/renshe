
package com.ciyun.renshe.wsdl.cxf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>openSessionResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="openSessionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="robotResponse" type="{http://www.eastrobot.cn/ws/RobotServiceEx}RobotResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "openSessionResponse", propOrder = {
    "robotResponse"
})
public class OpenSessionResponse {

    protected RobotResponse robotResponse;

    /**
     * 获取robotResponse属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RobotResponse }
     *     
     */
    public RobotResponse getRobotResponse() {
        return robotResponse;
    }

    /**
     * 设置robotResponse属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RobotResponse }
     *     
     */
    public void setRobotResponse(RobotResponse value) {
        this.robotResponse = value;
    }

}
