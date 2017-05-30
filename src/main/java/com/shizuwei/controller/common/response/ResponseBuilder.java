package com.shizuwei.controller.common.response;

import com.shizuwei.controller.common.dto.PageDto;

public class ResponseBuilder {

    private Object data;
    private PageDto pageDto;
    private int status;
    private ResponseError error;

    public ResponseBuilder() {
        this.status = ResponseStatus.OK.getValue();
    }

    public ResponseBuilder(Response response) {
        this.status = response.getStatus();
        this.data = response.getData();
        this.pageDto = response.getPageDto();
        this.error = response.getError();
    }

    public ResponseBuilder(ResponseStatus status) {
        this.status = status.getValue();
    }

    public ResponseBuilder(ResponseStatus status, Object data) {
        this(status);
        this.data = data;
    }

    public ResponseBuilder(ResponseStatus status, Object data, ResponseError error) {
        this(status, data);
        this.error = error;
    }

    public ResponseBuilder(ResponseStatus status, Object data, ResponseError error, PageDto pageDto) {
        this(status, data, error);
        this.pageDto = pageDto;
    }

    public ResponseBuilder(ResponseStatus status, ResponseError error) {
        this();
        this.status = status.getValue();
        this.error = error;
    }

    public ResponseBuilder setData(Object data) {
        this.data = data;
        return this;
    }

    public ResponseBuilder setPage(PageDto pageDto) {
        this.pageDto = pageDto;
        return this;
    }

    public ResponseBuilder setStatus(ResponseStatus status) {
        this.status = status.getValue();
        return this;
    }

    public ResponseBuilder setError(ResponseError error) {
        this.error = error;
        return this;
    }

    public Response build() {
    	Response response = new Response();
        response.setStatus(status);
        response.setData(data);
        response.setPageDto(pageDto);
        response.setError(error);
        return response;
    }
}
