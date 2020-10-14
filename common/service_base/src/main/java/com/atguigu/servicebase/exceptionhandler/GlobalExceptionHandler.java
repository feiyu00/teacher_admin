package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 *
 *
 * 全局异常处理类
 * */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }
    //自定义异常处理
    @ExceptionHandler(GuiLiException.class)
    @ResponseBody //返回数据
    public R error(GuiLiException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
