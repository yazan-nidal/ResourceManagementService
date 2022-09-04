package exp.exalt.ps.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Transient
    public static final String SEQUENCE_NAME = "sequence";
    @Id
    @NotNull
    private Long id;
    @Field
    @NotNull
    private  String name;
    @Field
    @NotNull
    @Indexed(unique = true)
    private String username;
    @Field
    @NotNull
    private String password;
    @DocumentReference(lazy=true)
    @Field
    private List<Server> servers;
}
