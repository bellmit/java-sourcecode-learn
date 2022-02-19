package com.niimbot.asset.framework.web.view;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.net.HttpHeaders;
import com.niimbot.asset.framework.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.manager.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 检查excel导出视图
 *
 * @author xie.wei
 */
public abstract class AbstractSimpleExcelView<T> extends AbstractXlsxView {

    @Setter
    @Getter
    protected String fileExtension = ".xlsx";
    protected CellStyle headerCellStyle;
    protected CellStyle rowCellStyle;
    private String fileName;

    @Getter
    @Setter
    private int rowStartIndex = 1;

    @Setter
    @Getter
    private List<String> headers;

    @Setter
    @Getter
    private List<T> data;

    protected AbstractSimpleExcelView(String fileName, List<T> data) {
        this.fileName = fileName;
        this.data = data;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(this.fileName)) {
            String requestName = request.getParameter("fileName");
            this.fileName = Optional.ofNullable(requestName).orElse("");
        }
        this.fileName = this.fileName + "-" +
                LocalDateTimeUtil.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")) + fileExtension;
        String excelName = URLEncoder.encode(this.fileName, StandardCharsets.UTF_8.toString());
        response.setContentType("application/vnd.ms-excel;charset=" + Constants.CHARSET);
        response.setHeader("Content-Disposition", "attachment; filename=" + excelName);
        response.setHeader("fileName", excelName);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "fileName");
        Sheet sheet = workbook.createSheet();
        this.setStyle(sheet);
        this.setHeader(sheet);
        this.setRow(sheet);
    }

    /**
     * 设置excel row
     *
     * @param sheet
     */
    protected abstract void setRow(Sheet sheet);

    /**
     * 设置 excel header
     *
     * @param sheet
     */
    protected void setHeader(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK1.getIndex());
        cellStyle.setFont(font);
        this.headerCellStyle = cellStyle;
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < this.headers.size(); i++) {
            headerRow.createCell(i).setCellValue(this.headers.get(i));
            headerRow.getCell(i).setCellStyle(headerCellStyle);
        }
    }

    protected void setStyle(Sheet sheet) {
        sheet.setDefaultColumnWidth(20);
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        return new SXSSFWorkbook(100);
    }
}