package neordinary.dofarming.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import neordinary.dofarming.common.code.BaseErrorCode;
import neordinary.dofarming.common.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
