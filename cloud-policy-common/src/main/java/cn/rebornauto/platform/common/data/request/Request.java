package cn.rebornauto.platform.common.data.request;

import lombok.Data;

import javax.validation.Valid;

@Data
public class Request<F extends Form,Q extends Query> {

    @Valid
    private F form;
    @Valid
    private Q query;
    @Valid
    private Pagination pagination;
}
