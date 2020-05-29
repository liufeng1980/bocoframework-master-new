package com.boco.framework.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("参数表")
public class JkptCommParamdic implements Serializable {
    /**
     * 标识
     */
    @ApiModelProperty("标识")
    private Integer id;

    /**
     * 字典名称
     */
    @ApiModelProperty("标识")
    private String dicname;

    /**
     * 字典值
     */
    @ApiModelProperty("字典值")
    private String dicvalue;

    /**
     * 子典具有父子关系时使用，此值表示父类型DicValue
     */
    @ApiModelProperty("子典具有父子关系时使用")
    private String dicparentid;

    /**
     * 组类型
     */
    @ApiModelProperty("组类型")
    private String grouptype;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    /**
     * 排序扩展
     */
    @ApiModelProperty("排序扩展")
    private Integer orderext;

    /**
     * 扩宽
     */
    @ApiModelProperty("扩宽")
    private String ext;


}
