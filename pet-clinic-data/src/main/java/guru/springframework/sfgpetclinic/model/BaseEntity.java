package guru.springframework.sfgpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

//will not map to database; other classes derived from BaseEntity
@MappedSuperclass
public class BaseEntity implements Serializable {

    //Serializable only identifies serializable semantics

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //use box types; they can be null

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
