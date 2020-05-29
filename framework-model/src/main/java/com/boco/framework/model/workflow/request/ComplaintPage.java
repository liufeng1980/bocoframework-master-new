package com.boco.framework.model.workflow.request;

import com.boco.framework.model.common.Pager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("投诉列表")
public class ComplaintPage extends Pager {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Integer complaintId = 0;
    /**
     * 审核表主键
     */
    @ApiModelProperty("审核表主键")
    private Integer auditInfoId = 0;

    /**
     * 查查询类型:0-快捷查询,1-详细查询,2-关键字查询
     */
    @ApiModelProperty("查查询类型:0-快捷查询,1-详细查询,2-关键字查询")
    private int queryType = 0;
    /**
     * 最快速查询:1-全部,2-需要我处理的,3-流转中投诉,4-已完成投诉,5-回收站投诉,6-被驳回的
     */
    @ApiModelProperty("最快速查询:1-全部,2-需要我处理的,3-流转中投诉,4-已完成投诉,5-回收站投诉,6-被驳回的")
    private Integer dayCount = 1;
    /**
     * 车牌号或手机号
     */
    @ApiModelProperty("车牌号或手机号")
    private String searchInput = "";
    /**
     * 投诉编号
     */
    @ApiModelProperty("投诉编号")
    private String complaintCode = "";
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String beginTime = "";
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime = "";
    /**
     * 投诉等级
     */
    @ApiModelProperty("投诉等级")
    private String complaintLevel = "";
    /**
     * 投诉对象
     */
    @ApiModelProperty("投诉对象")
    private String complaintTarget = "";
    /**
     * 排序规则
     */
    @ApiModelProperty("排序规则")
    private String sortStr = "";
    /**
     * 登录用户机构Id
     */
    @ApiModelProperty("主键id")
    private String loginUserOrgId = "";
    /**
     * 点击来源刷新不同的列表desktop-桌面快捷方式list-列表
     */
    @ApiModelProperty("点击来源刷新不同的列表desktop-桌面快捷方式list-列表")
    private String refrushTarget = "list";

    /**
     * 投诉类型
     */
    @ApiModelProperty("投诉类型")
    private String complaintType = "";

}
