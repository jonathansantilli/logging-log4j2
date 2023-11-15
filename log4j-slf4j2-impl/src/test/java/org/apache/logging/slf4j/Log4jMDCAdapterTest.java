/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Log4jMDCAdapterTest {

    private static final Log4jMDCAdapter MDC_ADAPTER = new Log4jMDCAdapter();
    private static final String KEY = "Log4j2";

    private static Deque<String> createDeque(int size) {
        final Deque<String> result = new ArrayDeque<>(size);
        IntStream.range(0, size).mapToObj(Integer::toString).forEach(result::addLast);
        return result;
    }

    private static Deque<String> popDeque(String key) {
        final Deque<String> result = new ArrayDeque<>();
        String value;
        while ((value = MDC_ADAPTER.popByKey(key)) != null) {
            result.addLast(value);
        }
        return result;
    }

    static Stream<String> keys() {
        return Stream.of(KEY, "", null);
    }

    @ParameterizedTest
    @MethodSource("keys")
    public void testPushPopByKey(final String key) {
        MDC_ADAPTER.clearDequeByKey(key);
        final Deque<String> expectedValues = createDeque(100);
        expectedValues.descendingIterator().forEachRemaining(v -> MDC_ADAPTER.pushByKey(key, v));
        assertThat(MDC_ADAPTER.getCopyOfDequeByKey(key)).containsExactlyElementsOf(expectedValues);
        assertThat(popDeque(key)).containsExactlyElementsOf(expectedValues);
    }

}