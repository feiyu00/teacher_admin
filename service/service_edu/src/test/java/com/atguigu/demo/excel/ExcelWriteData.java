package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelWriteData {
    public static void main(String[] args) {
        //实现excel写操作
        //1.设置写入的文件夹的地址和excel文件名称
        String fileName = "G:\\write.xlsx";
        //2.调用easyexcel实现写操作,第一个参数是文件名称，第二个参数为实体类名称
        EasyExcel.write(fileName, ExcelData.class).sheet("学生列表").doWrite(getData());
    }
    //创建一个方法，方法返回List
    private static List<ExcelData> getData(){
        List<ExcelData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData demoData = new ExcelData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            list.add(demoData);
        }
        return list;
    }

}
