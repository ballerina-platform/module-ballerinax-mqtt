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

package io.ballerina.stdlib.mqtt.caller;

import io.ballerina.runtime.api.values.BObject;
import io.ballerina.stdlib.mqtt.utils.MqttConstants;
import io.ballerina.stdlib.mqtt.utils.MqttUtils;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

/**
 * Class containing the external methods of the caller.
 */
public class CallerActions {

    public static Object complete(BObject callerObject) {
        MqttClient subscriber = (MqttClient) callerObject.getNativeData(MqttConstants.SUBSCRIBER);
        int messageId = (int) callerObject.getNativeData(MqttConstants.MESSAGE_ID);
        int qos = (int) callerObject.getNativeData(MqttConstants.QOS);
        try {
            subscriber.messageArrivedComplete(messageId, qos);
        } catch (MqttException e) {
            return MqttUtils.createMqttError(e);
        }
        return null;
    }

}