package com.csu.nopablog.common.ustils;

import lombok.Data;

/**
 * @Author: nopa
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 503: 不具备角色功能
 * 555：异常抛出信息
 * @Date: Create in 16:02 2020/10/18
 */
@Data
public class BlogResponser {
    private String msg;

    private Integer status;

    private Object data;

    public BlogResponser() {

    }

    public BlogResponser(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BlogResponser(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public static BlogResponser build(Integer status, String msg, Object data) {
        return new BlogResponser(status, msg, data);
    }

    public static BlogResponser ok(Object data) {
        return new BlogResponser(data);
    }

    public static BlogResponser ok() {
        return new BlogResponser(null);
    }

    public static BlogResponser errorMsg(String msg) {
        return new BlogResponser(500, msg, null);
    }

    public static BlogResponser errorMap(Object data) {
        return new BlogResponser(501, "error", data);
    }

    public static BlogResponser errorTokenMsg(String msg) {
        return new BlogResponser(502, msg, null);
    }

    public static BlogResponser errorRolesMsg(String msg) {
        return new BlogResponser(503, msg, null);
    }

    public static BlogResponser errorException(String msg) {
        return new BlogResponser(555, msg, null);
    }


}
