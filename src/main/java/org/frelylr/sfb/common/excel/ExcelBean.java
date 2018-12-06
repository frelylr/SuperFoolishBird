package org.frelylr.sfb.common.excel;

import lombok.Data;

/**
 * Excel数据List的Bean类型
 */
@Data
public class ExcelBean {

    @ExcelColumn(value = "ID", col = 1)
    private String id;

    @ExcelColumn(value = "名称", col = 2)
    private String name;

    @ExcelColumn(value = "创建日期", col = 3)
    private String createTime;

    @ExcelColumn(value = "更新日期", col = 4)
    private String updateTime;
}
