package com.boco.framework.model.workflow.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("投诉单")
public class Complaint implements Serializable {
    /**
     * 表主键
     */
    @ApiModelProperty("表主键")
    private Integer pkid = 0;
    /**
     * 来电人
     */
    @ApiModelProperty("来电人")
    private String caller = "";
    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String telephone = "";
    /**
     * 车牌号码
     */
    @ApiModelProperty("车牌号码")
    private String plateNumber = "";
    /**
     * 卡类型
     */
    @ApiModelProperty("卡类型")
    private String cardType = "";
    /**
     * 卡号
     */
    @ApiModelProperty("卡号")
    private String cardNumber = "";
    /**
     * 标签型号
     */
    @ApiModelProperty("标签型号")
    private String tagType = "";
    /**
     * 标签号
     */
    @ApiModelProperty("标签号")
    private String tagNumber = "";
    /**
     * 投诉类型，请参考JKPT_COMM_PARAMDIC表GROUPTYPE=ComplaintType
     */
    @ApiModelProperty("投诉类型")
    private String complaintType;

    /**
     * 投诉父类型
     */
    @ApiModelProperty("投诉父类型")
    private String parentType;
    /**
     * 投诉等级，请参考JKPT_COMM_PARAMDIC表GROUPTYPE=ComplaintLevel
     */
    @ApiModelProperty("投诉等级")
    private String complaintLevel;
    /**
     * 投诉对象
     */
    @ApiModelProperty("投诉对象")
    private String complaintTarget = "";
    /**
     * 事件时间
     */
    @ApiModelProperty("事件时间")
    private String eventTime = "";
    /**
     * 事件地点
     */
    @ApiModelProperty("事件地点")
    private String eventPlace = "";
    /**
     * 是否现金交费,请参考JKPT_COMM_PARAMDIC表GROUPTYPE=WhetherOrNot
     */
    @ApiModelProperty("是否现金交费")
    private String isCashPayment;
    /**
     * 事件描述
     */
    @ApiModelProperty("事件描述")
    private String eventDescribe = "";
    /**
     * 用户诉求
     */
    @ApiModelProperty("用户诉求")
    private String userAppeal = "";
    /**
     * 创建人userid
     */
    @ApiModelProperty("创建人userid")
    private String creationUserId;
    /**
     * 登陆名
     */
    @ApiModelProperty("登陆名")
    private String loginId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String creationTime;
    /**
     * 创建机构ID
     */
    @ApiModelProperty("创建机构ID")
    private String creationOrgId;
    /**
     * 完成时效(截止到当前时间整个呼叫流转的总体天数，如果已完成或放到回收站则取usedasy,否则取sysdate-creationtime所差天数)
     */
    @ApiModelProperty("完成时效")
    private Integer usedDays;
    /**
     * 是否归档：1-是，0-否
     */
    @ApiModelProperty("是否归档：1-是，0-否")
    private String isArchive;
    /**
     * 归档时间
     */
    @ApiModelProperty("归档时间")
    private String archiveTime;
    /**
     * 是否处理完成：1-是，0-否
     */
    @ApiModelProperty("是否处理完成：1-是，0-否")
    private String isFinish;
    /**
     * 是否放入回收站：1-是，0-否
     */
    @ApiModelProperty("是否放入回收站：1-是，0-否")
    private String isRecyclebin;
    /**
     * 回收站时间
     */
    @ApiModelProperty("回收站时间")
    private String recyclebinTime;
    /**
     * 该投诉当前处理机构id
     */
    @ApiModelProperty("该投诉当前处理机构id")
    private String currentOrgId;
    /**
     * 当前状态描述
     */
    @ApiModelProperty("当前状态描述")
    private String currentStatusDesc = "";

    /**
     * etc 受理单号
     */
    @ApiModelProperty("etc 受理单号")
    private String etcBillNo;

    /**
     * etc 受理时间
     */
    @ApiModelProperty("etc 受理时间")
    private String etcAcceptTime;

    /**
     * etc 发生时间
     */
    @ApiModelProperty("etc 发生时间")
    private String etcEventTime;

    /**
     * etc 受理次数
     */
    @ApiModelProperty("etc 受理次数")
    private Integer etcAcceptCount;

    /**
     * etc 投诉代理机构 对应字典 EtcAgency
     */
    @ApiModelProperty("etc 投诉代理机构 对应字典 EtcAgency")
    private String etcAgency;

    /**
     * etc 受理人
     */
    @ApiModelProperty("etc 受理人")
    private String etcAcceptUser;

    /**
     * etc 蒙通卡客户姓名
     */
    @ApiModelProperty("etc 蒙通卡客户姓名")
    private String etcUserName;

    /**
     * 证件类型
     */
    @ApiModelProperty("证件类型")
    private String idType;

    /**
     * 证件号码
     */
    @ApiModelProperty("证件号码")
    private String idNo;

    /**
     * 银行名称
     */
    @ApiModelProperty("银行名称")
    private String bankName;

    /**
     * 银行账号
     */
    @ApiModelProperty("银行账号")
    private String bankAccount;

    /**
     * etc充值单号
     */
    @ApiModelProperty("etc充值单号")
    private String etcRechargeBillNo;

    /**
     * 支付方式 字典 ComplaintPayType
     */
    @ApiModelProperty("支付方式 ")
    private String payType;

    // 扩展字段 begin
    /**
     * 投诉类型描述
     */
    @ApiModelProperty("投诉类型描述")
    private String complaintTypeDesc;
    /**
     * 投诉等级描述
     */
    @ApiModelProperty("投诉等级描述")
    private String complaintLevelDesc;
    /**
     * 是否现金交费描述
     */
    @ApiModelProperty("是否现金交费描述")
    private String isCashPaymentDesc;
    /**
     * 最后一次创建时间
     */
    @ApiModelProperty("最后一次创建时间")
    private String lastTimeCreationTime;
    /**
     * 当前时间
     */
    @ApiModelProperty("当前时间")
    private String currentTime;
    /**
     * 处理时效（最后一次流转所用天数 即 当前时间-最后一次创建时间）
     */
    @ApiModelProperty("处理时效")
    private Integer singleUsedDays;
    /**
     * 摘要=事件描述+诉求
     */
    @ApiModelProperty("摘要=事件描述+诉求")
    private String remark;
    /**
     * 当前步骤状态找要信息
     */
    @ApiModelProperty("当前步骤状态找要信息")
    private String currentStepStatusInfoPart1 = "";
    /**
     * 当前步骤状态找要信息
     */
    @ApiModelProperty("当前步骤状态找要信息")
    private String currentStepStatusInfoPart2 = "";
    /**
     * 当前次数的id
     */
    @ApiModelProperty("当前次数的id")
    private Integer currentAuditId = 0;
    /**
     * 投诉编号
     */
    @ApiModelProperty("投诉编号")
    private String complaintCode = "";
    /**
     * 附件列表字符串
     */
    @ApiModelProperty("附件列表字符串")
    private String attachments = "";
    /**
     * 资料文件信息
     */
    @ApiModelProperty("资料文件信息")
    private String fileInfo = "";
    /**
     * 发送人LoginId
     */
    @ApiModelProperty("发送人LoginId")
    private String sendUserLoginId;
    /**
     * 代理机构
     */
    @ApiModelProperty("代理机构")
    private String etcAgencyDesc;
    /**
     * 扩展字段--证件类型
     */
    @ApiModelProperty("扩展字段--证件类型")
    private String idTypeDesc;
    /**
     * 扩展字段--支付方式
     */
    @ApiModelProperty("扩展字段--支付方式")
    private String payTypeDesc;
    /**
     * 扩展字段--附件
     */

    //TODO
   // private List<Attachment> attachmentList;
    // 扩展字段 end

    // 备用字段 begin
    /**
     * 备用字段
     */
    @ApiModelProperty("备用字段")
    private String remark_1;
    @ApiModelProperty("备用字段")
    private String remark_2;
    @ApiModelProperty("备用字段")
    private String remark_3;
    @ApiModelProperty("备用字段")
    private String remark_4;
    @ApiModelProperty("备用字段")
    private String remark_5;
    @ApiModelProperty("备用字段")
    private String remark_6;
    @ApiModelProperty("备用字段")
    private String remark_7;
    @ApiModelProperty("备用字段")
    private String remark_8;

}
