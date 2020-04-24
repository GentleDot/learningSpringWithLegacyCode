package net.gentledot.springcodeproject.controllers;

import net.gentledot.springcodeproject.errors.TargetNotFoundException;
import net.gentledot.springcodeproject.errors.TransactionFailException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class,
            TransactionFailException.class,
            TargetNotFoundException.class})
    public ModelAndView apiErrorHandler(Exception e) {
        log.debug("API 오류 발생 : {}", e.getMessage(), e);

        return setErrorPageView(e);
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionHandler(Exception e) {
        log.warn("Null 참조 오류 발생! : {}", e.getMessage(), e);

        return setErrorPageView(e);
    }

    @ExceptionHandler({MyBatisSystemException.class
            , PersistenceException.class})
    public ModelAndView myBatisSystemExceptionHandler(Exception e) {
        log.warn("MyBatis 구동에 문제가 발생하였습니다. : {}", e.getMessage(), e);

        return setErrorPageView(e);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ModelAndView unexpectedExceptionHandler(Exception e) {
        log.error("예상하지 못한 예외가 발생하였습니다. {}", e.getMessage(), e);

        return setErrorPageView(e);
    }

    private ModelAndView setErrorPageView(Exception e) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/error_common");
        model.addObject("exception", e);

        return model;
    }
}
