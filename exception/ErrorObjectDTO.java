
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorObjectDTO {

    private final String message;
    private final String field;
    private final Object parameter;
}
