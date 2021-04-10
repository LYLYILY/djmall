package com.dj.mall.common.base;

import lombok.Data;

/**
 * API接口统一返回
 *
 * @param <T> 数据泛型
 */
@Data
public class ResultModel<T> {
    // 后期可优化为枚举
    /**
     * 默认处理失败状态码
     */
    private static final Integer DEFAULT_ERROR_CODE = -1;

    /**
     * 默认处理成功状态码
     */
    private static final Integer DEFAULT_SUCCESS_CODE = 200;

    /**
     * 返回状态码
     */
    private Integer code = DEFAULT_SUCCESS_CODE;

    /**
     * 返回消息
     */
    private String msg = "业务处理成功";

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功-默认
     *
     * @return
     */
    public ResultModel<T> success() {
        return this;
    }

    /**
     * 成功-返回数据
     *
     * @param data 返回数据
     * @return
     */
    public ResultModel<T> success(T data) {
        this.data = data;
        return this;
    }

    /**
     * 成功-返回状态码+数据
     *
     * @param code 返回状态码
     * @param data 返回数据
     * @return
     */
    public ResultModel<T> success(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    /**
     * 成功-返回状态码+消息+数据
     *
     * @param code 返回状态码
     * @param msg  返回消息
     * @param data 返回数据
     * @return
     */
    public ResultModel<T> success(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

    /**
     * 失败-返回消息
     *
     * @param msg 返回消息
     * @return
     */
    public ResultModel<T> error(String msg) {
        this.code = DEFAULT_ERROR_CODE;
        this.msg = msg;
        return this;
    }

    /**
     * 失败- 返回状态码+消息
     *
     * @param code 返回状态码
     * @param msg  返回消息
     * @return
     */
    public ResultModel<T> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    /**
     * 失败-返回状态码+消息+数据
     *
     * @param code 返回状态码
     * @param msg  返回消息
     * @param data 返回数据
     * @return
     */
    public ResultModel<T> error(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }

}
