package dev.pascalaualex.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;


public record Run(
        @Id
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer miles,
        Location location,
        Integer version
) {
    public Run{
        if(completedOn.isBefore(startedOn)){
            throw new IllegalArgumentException("Completed on must be after Started On");
        }
    }
}




