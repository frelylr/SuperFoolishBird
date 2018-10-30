package org.frelylr.sfb.excel;


import org.frelylr.sfb.common.ExcelColumn;

import lombok.Data;

/**
 * Excel数据List的Bean类型
 */
@Data
public class ExcelBeanXXX {

    @ExcelColumn(value = "第1列名称", col = 1)
    private String distributorCompanyName;

    @ExcelColumn(value = "第2列名称", col = 2)
    private String sn;

    @ExcelColumn(value = "第3列名称", col = 3)
    private String modality;

    @ExcelColumn(value = "第4列名称", col = 4)
    private String model;

    @ExcelColumn(value = "第5列名称", col = 5)
    private String status;
}
