package com.shizuwei.controller.common.response;

import java.io.Serializable;

import com.shizuwei.controller.common.dto.PageDto;

public class Response implements Serializable {

    private static final long serialVersionUID = -3458675766341550281L;
    private int status;
    private Object data;
    private ResponseError error;
    private PageDto pageDto;

    public Response() {
        status = ResponseStatus.OK.getValue();
    }

    public Response(ResponseStatus status, Object data) {
        super();
        this.status = status.getValue();
        this.data = data;
    }

    public Response(ResponseStatus status, Object data, ResponseError error) {
        super();
        this.status = status.getValue();
        this.data = data;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    /**
     * @return the pageDto
     */

    public PageDto getPageDto() {

        return pageDto;

    }

    /**
     * @param pageDto the pageDto to set
     */

    public void setPageDto(PageDto pageDto) {

        this.pageDto = pageDto;

    }
}
