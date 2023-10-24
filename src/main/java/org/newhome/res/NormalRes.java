package org.newhome.res;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class NormalRes {
    @JSONField(name = "CODE")
    private int code;
    @JSONField(name = "MESSAGE")
    private String msg;

    public NormalRes(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
