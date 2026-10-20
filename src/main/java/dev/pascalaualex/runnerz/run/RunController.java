package dev.pascalaualex.runnerz.run;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/api/runs") // Base URI
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository){
        this.runRepository = runRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Run> findAll(){
        return runRepository.findAll();
    }

    // Get by id
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id){
        Optional<Run> run =runRepository.findById(id);
        if(run.isEmpty()){
            throw new RunNotFoundException();
        }
        return run.get();
    }

    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run){
        runRepository.create(run);
    }

    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id){
        runRepository.update(run, id);
    }


    //delete
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id){
        runRepository.delete(id);
    }
}

