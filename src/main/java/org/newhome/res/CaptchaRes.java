package org.newhome.res;

import lombok.Data;

@Data
public class CaptchaRes {
    String base64;

    String code;
}
