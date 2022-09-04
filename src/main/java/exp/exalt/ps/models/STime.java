package exp.exalt.ps.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class STime {
    @Id
    @NotNull
    private Long id;
    @Field
    @NotNull
    private long time;
}
