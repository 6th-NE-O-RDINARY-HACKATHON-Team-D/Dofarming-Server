package neordinary.dofarming.common.exceptions.handler;


import neordinary.dofarming.common.code.BaseErrorCode;
import neordinary.dofarming.common.exceptions.BaseException;

public class ExceptionHandler extends BaseException {
    public ExceptionHandler(BaseErrorCode errorCode) {super(errorCode);}
}
