package com.boco.framework.model.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 17:01
 */

public class AuditBase {
    public AuditBase() {
        modifiedProperties = new ArrayList<String>();
    }

    /**
     * 赋值过的属性列表
     */
    private List<String> modifiedProperties;

    private boolean isAudit;

    public List<String> getModifiedProperties() {
        return modifiedProperties;
    }

    public void setModifiedProperties(List<String> modifiedProperties) {
        this.modifiedProperties = modifiedProperties;
    }

    public Boolean getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Boolean isAudit) {
        this.isAudit = isAudit;
    }

    private Object getFieldValue(Object thisClass, String fieldName) {
        Object value = new Object();
        Method method = null;
        try {
            String methodName = fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            method = thisClass.getClass().getMethod("get" + methodName);
            value = method.invoke(thisClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void Audit(Object oldValue, Object newValue, String propertyName) {
        //由于springmvc的controller接收类型为类的参数时
        //对于这个类的属性的赋值是按字母a->z的顺序赋值的
        //导致属性首字母在i之前的在setIsAudit(true)之前赋值了
//		if (!isAudit) {
//			return;
//		}

        if (oldValue == null && newValue == null) {
            return;
        } else {
            if ((oldValue == null && newValue != null)
                    || (newValue == null && oldValue != null)) {
                this.modifiedProperties.add(propertyName);
            } else {
                Class type = oldValue.getClass();
                if (type.getClasses() != null) {
                    Field[] propertyinfos = type.getDeclaredFields();

                    if (propertyinfos != null && propertyinfos.length > 0) {
                        Field idProperty = null;
                        for (Field item : propertyinfos) {
                            if (item.getName().equals("id")) {
                                idProperty = item;
                                break;
                            }
                        }

                        if (idProperty != null) {
                            int oldId = (Integer) getFieldValue(oldValue,
                                    idProperty.getName());

                            int newId = (Integer) getFieldValue(newValue,
                                    idProperty.getName());

                            if (oldId != newId) {
                                this.modifiedProperties.add(propertyName);
                            }
                        } else {
                            this.modifiedProperties.add(propertyName);
                        }
                    }
                } else {
                    if (!oldValue.equals(newValue)) {
                        this.modifiedProperties.add(propertyName);
                    }
                }
            }
        }
    }

    public boolean PropertyChanged(String propertyName) {
        if (modifiedProperties != null && modifiedProperties.size() > 0) {
            for (String item : modifiedProperties) {
                if (item.equals(propertyName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
