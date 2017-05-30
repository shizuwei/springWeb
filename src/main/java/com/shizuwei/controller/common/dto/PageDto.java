package com.shizuwei.controller.common.dto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageDto implements Cloneable {
    public static final int DEF_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEF_PAGE_NUM = 1;

    private static final PageDto DEF_PAGE_DTO = new PageDto();

    public static final PageDto getDefPageDto() {
        try {
            return (PageDto) DEF_PAGE_DTO.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    private Integer count;
    private Integer curPageCount;
    private Integer pageNum;
    private Integer pageSize;

    public PageDto() {
        this.count = 0;
        this.curPageCount = 0;
        this.pageNum = DEF_PAGE_NUM;
        this.pageSize = DEF_PAGE_SIZE;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        if (count == null) {
            count = 0;
        }
        this.count = count;
        validate();
    }

    public Integer getCurPageCount() {
        return curPageCount;
    }

    public void setCurPageCount(Integer curPageCount) {
        if (curPageCount == null) {
            curPageCount = 0;
        }
        this.curPageCount = curPageCount;
    }

    public Integer getPageNum() {
        if (pageNum == null || pageNum <= 0) {
            pageNum = DEF_PAGE_NUM;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = DEF_PAGE_NUM;
        }
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = DEF_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = DEF_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public void validate() {
        int pageNum = getPageNum();
        int pageSize = getPageSize();
        int count = getCount();
    
        if (count <= 0) {
            setPageNum(1);
            return;
        }
     
        int lastPageNum = (count - 1) / pageSize + 1;
        if (lastPageNum > pageNum) {
            setCurPageCount(pageSize);
            return;
        }
        int lastPageSize = (count - 1) % pageSize + 1;
        setCurPageCount(lastPageSize);
        if (lastPageNum < pageNum) {
            setPageNum(lastPageNum);
            return;
        }
    }

    public int firstNum() {
        int pageNum = getPageNum();
        int pageSize = getPageSize();
        return (pageNum - 1) * pageSize;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        PageDto pageDto = new PageDto();
        pageDto.count = this.count;
        pageDto.curPageCount = this.curPageCount;
        pageDto.pageNum = this.pageNum;
        pageDto.pageSize = this.pageSize;
        return pageDto;
    }
}
