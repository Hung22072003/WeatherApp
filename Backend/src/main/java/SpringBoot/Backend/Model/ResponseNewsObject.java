package SpringBoot.Backend.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseNewsObject {
    private String status;
    private String message;
    private Object info;

    public ResponseNewsObject(String status, String message, Object info) {
        this.status = status;
        this.message = message;
        this.info = info;
    }
}
