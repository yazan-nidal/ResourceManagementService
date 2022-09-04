package exp.exalt.ps.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    @Transient
    public static final String SEQUENCE_NAME = "sequence";
    @Id
    private Long id;
    @Field
    private Long state = State.CREATING.getState();
    @Field
    private Long capacity;
    @Field
    private Long available;
    @DocumentReference(lazy=true)
    @Field
    private List<User> users;
}
