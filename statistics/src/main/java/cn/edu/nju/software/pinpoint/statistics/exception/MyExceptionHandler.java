package cn.edu.nju.software.pinpoint.statistics.exception;

import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public JSONResult defaultErrorHandler(HttpServletRequest req,
                                          Exception e) throws Exception {

        e.printStackTrace();
        return JSONResult.errorException(e.getMessage());
    }
}
