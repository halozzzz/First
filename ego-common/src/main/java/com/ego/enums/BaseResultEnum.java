package com.ego.enums;

/**
 * @see(功能介绍) : 为什么使用枚举类？
 *                  1.提升代码的阅读性，避免硬编码。
 *                  2.程序解耦
 * @version(版本号) : 1.0
 * @author(创建人) : Dylan
 * @since : JDK 1.8
 */
public enum BaseResultEnum {
    // 自定义枚举类
    SUCESS(200, "成功"),
    ERROR(400, "失败"),
    PASS_ERROR_01(501, "两次输入的密码不一致"),
    PASS_ERROR_02(502, "输入的原始密码不正确"),
    PASS_ERROR_03(503, "输入的验证码不正确"),
    PASS_ERROR_04(504, "输入的验证码为空"),
    PASS_ERROR_05(505, "用户名或者密码错误");

    private Integer code;
    private String message;

    private BaseResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
