
package com.leakyabstractions.result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DefaultSuccess#filter(Predicate, Function)}.
 * 
 * @author Guillermo Calvo
 */
@DisplayName("DefaultSuccess filter")
class DefaultSuccess_filter_Test {

    private static final String FAILURE = "FAILURE";

    @Test
    void should_return_itself_when_filter_returns_true() {
        // Given
        final Result<String, Integer> success = new DefaultSuccess<>("SUCCESS");
        final Predicate<String> filter = s -> true;
        final Function<String, Integer> mapper = s -> fail("Should not happen");
        // When
        final Result<String, Integer> result = success.filter(filter, mapper);
        // Then
        assertThat(result).isSameAs(success);
    }

    @Test
    void should_return_failure_when_filter_returns_false() {
        // Given
        final Result<Integer, String> success = new DefaultSuccess<>(321);
        final Predicate<Integer> filter = s -> false;
        final Function<Integer, String> mapper = s -> FAILURE;
        final Result<Integer, String> expected = new DefaultFailure<>(FAILURE);
        // When
        final Result<Integer, String> result = success.filter(filter, mapper);
        // Then
        assertThat(result).isEqualTo(expected);
    }
}
