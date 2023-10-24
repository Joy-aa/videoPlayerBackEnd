package org.newhome.config;

public enum FilterType {

    anno(1, "开放地址"),
    auth(2, "需要登录"),
    sta(3, "静态地址"),
    login(4, "登录地址"),
    regist(5, "注册地址");

    private final int type;
    private final String desc;

    FilterType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
