package com.project.cinema.api.model.request.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.cinema.api.base.OperationInput;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectionsByDateRequest implements OperationInput {
    @JsonFormat(shape =JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startProjectionDate;
    @JsonFormat(shape =JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endProjectionDate;
}
