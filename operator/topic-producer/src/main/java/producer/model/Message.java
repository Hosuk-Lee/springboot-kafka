package producer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;

public record Message(String code, String message) {

    @JsonCreator
    public Message(@JsonProperty("code") String code,
                  @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "code='" + code + '\'' +
                ", message=" + message +
                '}';
    }
}
