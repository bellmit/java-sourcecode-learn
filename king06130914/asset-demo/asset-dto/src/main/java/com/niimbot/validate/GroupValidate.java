package com.niimbot.validate;

import java.io.Serializable;

/**
 * @author XieKong
 * @date 2020/12/14 下午5:50
 */
public interface GroupValidate extends Serializable {
    interface Save {}

    interface Update {}

    interface SaveOrUpdate {}

    interface Delete {}
}
