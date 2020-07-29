
package com.ciyun.renshe.wsdl.cxf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>RobotResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RobotResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.eastrobot.cn/ws/RobotServiceEx}RobotCommand" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="moduleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nodeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="relatedQuestions" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="similarity" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="tags" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RobotResponse", propOrder = {
    "commands",
    "content",
    "moduleId",
    "nodeId",
    "relatedQuestions",
    "similarity",
    "tags",
    "type"
})
public class RobotResponse {

    @XmlElement(nillable = true)
    protected List<RobotCommand> commands;
    protected String content;
    protected String moduleId;
    protected String nodeId;
    @XmlElement(nillable = true)
    protected List<String> relatedQuestions;
    protected float similarity;
    @XmlElement(nillable = true)
    protected List<String> tags;
    protected int type;

    /**
     * Gets the value of the commands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RobotCommand }
     * 
     * 
     */
    public List<RobotCommand> getCommands() {
        if (commands == null) {
            commands = new ArrayList<RobotCommand>();
        }
        return this.commands;
    }

    /**
     * 获取content属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * 获取moduleId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * 设置moduleId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModuleId(String value) {
        this.moduleId = value;
    }

    /**
     * 获取nodeId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 设置nodeId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeId(String value) {
        this.nodeId = value;
    }

    /**
     * Gets the value of the relatedQuestions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedQuestions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedQuestions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRelatedQuestions() {
        if (relatedQuestions == null) {
            relatedQuestions = new ArrayList<String>();
        }
        return this.relatedQuestions;
    }

    /**
     * 获取similarity属性的值。
     * 
     */
    public float getSimilarity() {
        return similarity;
    }

    /**
     * 设置similarity属性的值。
     * 
     */
    public void setSimilarity(float value) {
        this.similarity = value;
    }

    /**
     * Gets the value of the tags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<String>();
        }
        return this.tags;
    }

    /**
     * 获取type属性的值。
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

}
