package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;

public class Visit extends BaseEntity {

    private LocalDate date;
    private String description;
    private Pet Pet;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public guru.springframework.sfgpetclinic.model.Pet getPet() {
        return Pet;
    }

    public void setPet(guru.springframework.sfgpetclinic.model.Pet pet) {
        Pet = pet;
    }
}
