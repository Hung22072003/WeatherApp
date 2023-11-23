package SpringBoot.Backend.Exception;

import SpringBoot.Backend.Model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(RegionNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage RegionNotFoundException(RegionNotFoundException ex) {
        return new ErrorMessage("Failed", "Cannot find region by id");
    }


    @ExceptionHandler(DateFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage DateFormatException(DateFormatException ex) {
        return new ErrorMessage("Failed", "Required date format: YYYY-MM-DD");
    }

    @ExceptionHandler(DateOrderException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage DateOrderException(DateOrderException ex) {
        return new ErrorMessage("Failed", "Start date is before end date");
    }

    @ExceptionHandler(NotDataException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage NotDataException(NotDataException ex) {
        return new ErrorMessage("Failed", "Cannot find data");
    }

}
