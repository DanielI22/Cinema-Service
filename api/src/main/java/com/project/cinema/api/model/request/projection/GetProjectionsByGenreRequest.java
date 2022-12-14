package com.project.cinema.api.model.request.projection;

import com.project.cinema.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectionsByGenreRequest implements OperationInput {
    private String genreName;
}
