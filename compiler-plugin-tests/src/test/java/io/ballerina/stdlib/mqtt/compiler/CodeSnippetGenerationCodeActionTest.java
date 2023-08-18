/*
 * Copyright (c) 2023, WSO2 LLC. (https://www.wso2.com) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.mqtt.compiler;

import io.ballerina.projects.plugins.codeaction.CodeActionArgument;
import io.ballerina.projects.plugins.codeaction.CodeActionInfo;
import io.ballerina.tools.text.LinePosition;
import io.ballerina.tools.text.LineRange;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static io.ballerina.stdlib.mqtt.compiler.CompilerPluginTestUtils.BALLERINA_SOURCES;
import static io.ballerina.stdlib.mqtt.compiler.CompilerPluginTestUtils.EXPECTED_SOURCES;
import static io.ballerina.stdlib.mqtt.compiler.CompilerPluginTestUtils.RESOURCE_DIRECTORY;
import static io.ballerina.stdlib.mqtt.compiler.PluginConstants.CODE_TEMPLATE_NAME_WITHOUT_CALLER;
import static io.ballerina.stdlib.mqtt.compiler.PluginConstants.CODE_TEMPLATE_NAME_WITH_CALLER;
import static io.ballerina.stdlib.mqtt.compiler.PluginConstants.NODE_LOCATION;

/**
 * A class for testing code actions.
 */
public class CodeSnippetGenerationCodeActionTest extends AbstractCodeActionTest {
    @Test
    public void testEmptyServiceCodeAction() throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_1")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_1")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(13, 0),
                getExpectedCodeAction("service.bal", 13, 28, "Insert service template with caller",
                        CODE_TEMPLATE_NAME_WITH_CALLER), resultPath);
    }

    @Test
    public void testServiceWithVariablesCodeAction()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_2")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_2")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(13, 0),
                getExpectedCodeAction("service.bal", 16, 1, "Insert service template with caller",
                        CODE_TEMPLATE_NAME_WITH_CALLER), resultPath);
    }

    @Test
    public void testEmptyServiceCodeActionWithoutCaller()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_1")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_3")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(13, 0),
                getExpectedCodeAction("service.bal", 13, 28, "Insert service template without caller",
                        CODE_TEMPLATE_NAME_WITHOUT_CALLER), resultPath);
    }

    @Test
    public void testServiceWithVariablesCodeActionWithoutCaller()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_2")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_4")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(13, 0),
                getExpectedCodeAction("service.bal", 16, 1, "Insert service template without caller",
                        CODE_TEMPLATE_NAME_WITHOUT_CALLER), resultPath);
    }

    private CodeActionInfo getExpectedCodeAction(String filePath, int line, int offset,
                                                 String actionName, String templateName) {
        LineRange lineRange = LineRange.from(filePath, LinePosition.from(13, 0),
                LinePosition.from(line, offset));
        CodeActionArgument locationArg = CodeActionArgument.from(NODE_LOCATION, lineRange);
        CodeActionInfo codeAction = CodeActionInfo.from(actionName, List.of(locationArg));
        codeAction.setProviderName("MQTT_114/ballerina/mqtt/" + templateName);
        return codeAction;
    }
}
