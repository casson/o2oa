/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package com.x.processplatform.core.entity.element;

import com.x.base.core.entity.SliceJpaObject_;
import java.lang.Boolean;
import java.lang.String;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=com.x.processplatform.core.entity.element.Cancel.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Mon Dec 24 19:04:55 CST 2018")
public class Cancel_ extends SliceJpaObject_  {
    public static volatile SingularAttribute<Cancel,String> afterArriveScript;
    public static volatile SingularAttribute<Cancel,String> afterArriveScriptText;
    public static volatile SingularAttribute<Cancel,String> afterExecuteScript;
    public static volatile SingularAttribute<Cancel,String> afterExecuteScriptText;
    public static volatile SingularAttribute<Cancel,String> afterInquireScript;
    public static volatile SingularAttribute<Cancel,String> afterInquireScriptText;
    public static volatile SingularAttribute<Cancel,String> alias;
    public static volatile SingularAttribute<Cancel,Boolean> allowReroute;
    public static volatile SingularAttribute<Cancel,Boolean> allowRerouteTo;
    public static volatile SingularAttribute<Cancel,String> beforeArriveScript;
    public static volatile SingularAttribute<Cancel,String> beforeArriveScriptText;
    public static volatile SingularAttribute<Cancel,String> beforeExecuteScript;
    public static volatile SingularAttribute<Cancel,String> beforeExecuteScriptText;
    public static volatile SingularAttribute<Cancel,String> beforeInquireScript;
    public static volatile SingularAttribute<Cancel,String> beforeInquireScriptText;
    public static volatile SingularAttribute<Cancel,String> description;
    public static volatile SingularAttribute<Cancel,String> extension;
    public static volatile SingularAttribute<Cancel,String> form;
    public static volatile SingularAttribute<Cancel,String> id;
    public static volatile SingularAttribute<Cancel,String> name;
    public static volatile SingularAttribute<Cancel,String> position;
    public static volatile SingularAttribute<Cancel,String> process;
    public static volatile ListAttribute<Cancel,String> readDataPathList;
    public static volatile SingularAttribute<Cancel,String> readDuty;
    public static volatile ListAttribute<Cancel,String> readGroupList;
    public static volatile ListAttribute<Cancel,String> readIdentityList;
    public static volatile SingularAttribute<Cancel,String> readScript;
    public static volatile SingularAttribute<Cancel,String> readScriptText;
    public static volatile ListAttribute<Cancel,String> readUnitList;
    public static volatile ListAttribute<Cancel,String> reviewDataPathList;
    public static volatile SingularAttribute<Cancel,String> reviewDuty;
    public static volatile ListAttribute<Cancel,String> reviewGroupList;
    public static volatile ListAttribute<Cancel,String> reviewIdentityList;
    public static volatile SingularAttribute<Cancel,String> reviewScript;
    public static volatile SingularAttribute<Cancel,String> reviewScriptText;
    public static volatile ListAttribute<Cancel,String> reviewUnitList;
}
