package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    private static final String VIEW_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService,
                         PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initPetCreationForm(Owner owner, Model model) {

        model.addAttribute("pet", Pet.builder().owner(owner).build());

        return VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processPetCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
        owner.addPet(pet);

        if(StringUtils.hasLength(pet.getName()) && pet.isNew()
                && owner.getPet(pet.getName(), true) != null) {

            result.rejectValue("name", "duplicate", "already exists");
        }

        if(result.hasErrors()) {
            model.put("pet", pet);

            return VIEW_PETS_CREATE_OR_UPDATE_FORM;

        } else {
            petService.save(pet);
            ownerService.save(owner);

            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/{petId}/edit")
    public String initUpdatePetForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));

        return VIEW_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{petId}/edit")
    public String processUpdatePetForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
        if(result.hasErrors()) {
            pet.setOwner(owner);

            model.addAttribute("pet", pet);

            return VIEW_PETS_CREATE_OR_UPDATE_FORM;

        } else {

            pet.setOwner(owner);
            petService.save(pet);

            return "redirect:/owners/" + owner.getId();
        }


    }
}
