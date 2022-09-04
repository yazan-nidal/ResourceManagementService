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
public class ServerDtoResponse {
    @JsonInclude(JsonInclude.Include.CUSTOM)
    @NotNull
    private Long serverId;
    @JsonInclude(JsonInclude.Include.CUSTOM)
    private String status;
    @JsonInclude(JsonInclude.Include.CUSTOM)
    private Long remainTime;
}
