package exp.exalt.ps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @NotNull
    private Long id;
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @NotNull
    private  String name;
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @NotNull
    private String username;
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @NotNull
    private String password;
}
