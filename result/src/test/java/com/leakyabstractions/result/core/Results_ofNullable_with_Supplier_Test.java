/*
 * Copyright 2025 Guillermo Calvo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leakyabstractions.result.core;

import static com.leakyabstractions.result.core.Results.failure;
import static com.leakyabstractions.result.core.Results.ofNullable;
import static com.leakyabstractions.result.core.Results.success;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.leakyabstractions.result.api.Result;

/**
 * Tests for {@link Results#ofNullable(Object, Supplier)}.
 *
 * @author Guillermo Calvo
 */
@DisplayName("Results ofNullable with supplier")
class Results_ofNullable_with_Supplier_Test {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @Test
    void should_return_success_when_not_null() {
        // Given
        final String nullable = SUCCESS;
        final Supplier<String> failureSupplier = () -> FAILURE;
        // When
        final Result<String, String> result = ofNullable(nullable, failureSupplier);
        // Then
        assertThat(result).isEqualTo(success(SUCCESS));
    }

    @Test
    void should_return_failure_when_null() {
        // Given
        final Integer nullable = null;
        final Supplier<String> failureSupplier = () -> FAILURE;
        // When
        final Result<Integer, String> result = ofNullable(nullable, failureSupplier);
        // Then
        assertThat(result).isEqualTo(failure(FAILURE));
    }
}
