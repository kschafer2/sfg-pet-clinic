package guru.springframework.sfgpetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass //will not map to database; other classes derived from BaseEntity
public class BaseEntity implements Serializable {

    //Serializable only identifies serializable semantics

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //use box types; they can be null
}
