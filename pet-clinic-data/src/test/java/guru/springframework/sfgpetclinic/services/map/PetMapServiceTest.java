package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class PetMapServiceTest {

    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 2L;
    private PetMapService petMapService;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();
        petMapService.save(Pet.builder().id(EXISTING_ID).build());
    }

    @Test
    void findAllTest() {
        Set<Pet> pets = petMapService.findAll();

        assertEquals(1, pets.size());
    }

    @Test
    void findByIdExistingIdTest() {
        Pet pet = petMapService.findById(EXISTING_ID);

        assertEquals(EXISTING_ID, pet.getId());
    }

    @Test
    void findByIdNotExistingIdTest() {
        Pet pet = petMapService.findById(NOT_EXISTING_ID);

        assertNull(pet);
    }

    @Test
    void findByIdNullIdTest() {
        Pet pet = petMapService.findById(null);

        assertNull(pet);
    }

    @Test
    void saveDuplicateIdTest() {
        Pet pet = Pet.builder().id(EXISTING_ID).build();
        Pet savedPet = petMapService.save(pet);

        assertEquals(EXISTING_ID, savedPet.getId());
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void saveNoIdTest() {
        Pet savedPet = petMapService.save(Pet.builder().build());

        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals(2, petMapService.findAll().size());
    }

    @Test
    void deletePetCorrectIdTest() {
        petMapService.delete(petMapService.findById(EXISTING_ID));

        assertEquals(0, petMapService.findAll().size());

    }

    @Test
    void deleteWithWrongIdTest() {
        Pet pet = Pet.builder().id(NOT_EXISTING_ID).build();

        petMapService.delete(pet);

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteWithNullIdTest() {
        Pet pet = Pet.builder().build();

        petMapService.delete(pet);

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteNullTest() {
        petMapService.delete(null);

        assertEquals(1, petMapService.findAll().size());

    }

    @Test
    void deleteByIdWrongIdTest() {
        petMapService.deleteById(NOT_EXISTING_ID);

        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteByIdNullIdTest() {
        petMapService.deleteById(null);

        assertEquals(1, petMapService.findAll().size());
    }
}