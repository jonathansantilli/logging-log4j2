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

import static org.apache.logging.log4j.test.SerializableMatchers.serializesRoundTrip;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.Serializable;

import org.apache.logging.log4j.core.test.junit.LoggerContextRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SerializeTest {

    private static final String CONFIG = "log4j-test1.xml";

    @ClassRule
    public static final LoggerContextRule CTX = new LoggerContextRule(CONFIG);

    Logger logger = LoggerFactory.getLogger("LoggerTest");

    @Test
    public void testLogger() throws Exception {
        assertThat((Serializable) logger, serializesRoundTrip());
    }
}
