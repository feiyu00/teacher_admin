package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

public class ExcelReadData {
    public static void main(String[] args) {
        String fileName = "G:\\write.xlsx";
        EasyExcel.read(fileName,ExcelData.class,new ExcelListener()).sheet().doRead();

    }
}
