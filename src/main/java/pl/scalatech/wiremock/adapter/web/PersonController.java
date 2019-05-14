package pl.scalatech.wiremock.adapter.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.scalatech.wiremock.adapter.db.PersonRepo;
import pl.scalatech.wiremock.domain.Person;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

//TODO only for test
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/persons", produces = APPLICATION_JSON_VALUE)
class PersonController {

    private final PersonRepo personRepo;

    @PostMapping
    ResponseEntity<String> save(@RequestBody Person person) {
        personRepo.save(person);
        return ok(person.toString());
    }


    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Person> person = personRepo.findById(id);
        return person.map(response -> ok(response.toString()))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
