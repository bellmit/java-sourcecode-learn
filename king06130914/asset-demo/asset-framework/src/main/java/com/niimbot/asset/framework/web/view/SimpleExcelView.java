package com.niimbot.asset.framework.web.view;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Optional;

/**
 * 检查excel导出视图
 *
 * @param <T>
 * @author xie.wei
 */
public class SimpleExcelView<T> extends AbstractSimpleExcelView<T> {

    private SimpleRowResolver<T> resolver;

    public SimpleExcelView(List<T> data, List<String> header, String fileName, SimpleRowResolver<T> resolver) {
        super(fileName, data);
        super.setHeaders(header);
        this.resolver = Optional.ofNullable(resolver).orElse(new SimpleRowResolver.DefaultSimpleRowResolver<>());
    }

    @Override
    protected void setRow(Sheet sheet) {
        this.resolver.setRow(sheet, getData());
    }
}