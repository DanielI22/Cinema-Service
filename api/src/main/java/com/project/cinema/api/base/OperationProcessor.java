package com.project.cinema.api.base;

import io.vavr.control.Either;

public interface OperationProcessor<I extends OperationInput, R extends OperationResult> {
    Either<Error, R> process(I operationInput);
}
