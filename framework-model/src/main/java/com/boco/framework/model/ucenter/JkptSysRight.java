package com.boco.framework.model.ucenter;

import lombok.Data;

import java.io.Serializable;

@Data
public class JkptSysRight implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 老编码
     */
    private String funcode;

    private String funname;

    /**
     * 1-数据2-按钮-3-菜单
     */
    private String funtypeid;

    private String fundesc;
}
