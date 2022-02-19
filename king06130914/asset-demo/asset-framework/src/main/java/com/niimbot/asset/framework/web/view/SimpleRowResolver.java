package com.niimbot.asset.framework.web.view;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author xie.wei
 * @Date 2021/4/21
 */
public interface SimpleRowResolver<T> {
    /**
     * 设置行数据
     *
     * @param sheet sheet页
     * @param data  数据
     */
    void setRow(Sheet sheet, List<T> data);

    class DefaultSimpleRowResolver<T> implements SimpleRowResolver<T> {

        @Override
        public void setRow(Sheet sheet, List<T> data) {
            // 空
        }
    }

}
