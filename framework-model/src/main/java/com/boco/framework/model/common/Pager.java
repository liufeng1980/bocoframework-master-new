package com.boco.framework.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页")
public class Pager {

    /**
     *
     */
    private static final long serialVersionUID = 1199616651273546935L;
    // 当前访问的页码，默认是第一页
    @ApiModelProperty(value = "总当前访问的页码，默认是第一页")
    private int currentPage = 1;
    // 每页显示最大行数，是固定值
    @ApiModelProperty(value = "每页显示最大行数，是固定值")
    private int pageSize = 5;

    // 需要在执行SQL语句前运算出begin和end，在执行SQL时利用这个运算结果。
    // 由于#{}执行时调用属性的get方法取值，所以在get方法中运算
    private int begin;
    private int end;

    // 总行数,需要外接传入
    @ApiModelProperty(value = "总行数,需要外接传入")
    private int rows;
    // 总页数，在get方法中自动计算，方便复用
    @ApiModelProperty(value = "总页数，在get方法中自动计算，方便复用")
    private int totalPage;
    // 访问url
    private String url;

    // 查询结果以哪一页开始,需要外界传入
    @ApiModelProperty(value = "查询结果以哪一页开始,需要外界传入")
    private int beginPageIndex;
    /**
     * 界面上显示的页的总数量
     */
    @ApiModelProperty(value = "界面上显示的页的总数量")
    private final int disPageAmount = 18;
    /**
     * 界面上显示的开始页页号
     */
    @ApiModelProperty(value = "界面上显示的开始页页号")
    private Integer startPage;
    /**
     * 界面上显示的截止页号
     */
    @ApiModelProperty(value = "界面上显示的截止页号")
    private Integer endPage;
    /**
     * 排序列
     */
    @ApiModelProperty(value = "排序列")
    private String orderString = "";

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;

        if (this.pageSize == 0) {
            return;
        }

        // 计算总页数
        if (rows % this.pageSize == 0) {
            this.totalPage = rows / this.pageSize;
        } else {
            this.totalPage = rows / this.pageSize + 1;
        }
        // 计算界面上显示的开始页和截止页
        if (this.totalPage <= this.disPageAmount) {
            this.startPage = 1;
            this.endPage = this.totalPage;
        } else {
            this.startPage = this.currentPage - (this.disPageAmount / 2 - 1);
            this.endPage = this.currentPage + (this.disPageAmount / 2);

            if (this.startPage < 1) {
                this.startPage = 1;
                this.endPage = this.disPageAmount;
            }

            if (this.endPage > this.totalPage) {
                this.endPage = this.totalPage;
                this.startPage = this.totalPage - (this.disPageAmount - 1);
            }

        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getBegin() {
        begin = (currentPage - 1) * pageSize + 1;
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        end = currentPage * pageSize;
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    /**
     * 界面上显示的开始页页号
     */
    public Integer getStartPage() {
        return startPage;
    }

    /**
     * 界面上显示的开始页页号
     */
    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    /**
     * 界面上显示的截止页号
     */
    public Integer getEndPage() {
        return endPage;
    }

    /**
     * 界面上显示的截止页号
     */
    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    /**
     * @return the 界面上显示的页的总数量
     */
    public int getDisPageAmount() {
        return disPageAmount;
    }

    /**
     * 获取 排序列
     *
     * @return orderString 排序列
     */
    public String getOrderString() {
        return orderString;
    }

    /**
     * 设置 排序列
     *
     * @param orderString
     *            排序列
     */
    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }
}
