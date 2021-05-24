package webscada.utils.communication;

import java.util.HashMap;

/*
 * Copyright 2016 Kevin Herron
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

import java.util.List;
import java.util.Map;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.apache.plc4x.java.api.value.PlcValue;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dto.ValueReal;
import webscada.api.mappers.ValueRealMapper;
import webscada.api.utils.IModbusTCP;
import webscada.entity.Dev;
import webscada.entity.Value;

@Slf4j
@Service
public class ModbusTCP implements IModbusTCP {

	public Map<Long, ValueReal> start(Dev dev, List<Value> values) {

		Map<Long, ValueReal> data = new HashMap<>();
		// --------------------------------------------------
		// Establish a connection to the plc using the url provided as first argument
		String connectionName = "modbus:tcp://" + dev.getIp() + ":" + dev.getPort();
		try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionName)) {

			if (!plcConnection.getMetadata().canRead()) {
				log.error("This connection doesn't support reading.");
				return null;
			}
			PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();

			for (Value value : values) {
				builder.addItem(String.valueOf(value.getId()), String.valueOf(value.getAddr()) + ":INT[1]");
			}

			PlcReadRequest readRequest = builder.build();

			log.info("Synchronous request ...");
			PlcReadResponse syncResponse = readRequest.execute().get();
			// Simply iterating over the field names returned in the response.
			printResponse(syncResponse);

			PlcValue asPlcValue = syncResponse.getAsPlcValue();

			for (Value value : values) {
				ValueReal valueReal = ValueRealMapper.mapValueReal(value);
				valueReal.setNumber(asPlcValue.getValue(value.getId().toString()).getInteger());
				data.put(valueReal.getId(), valueReal);
			}
			log.info(asPlcValue.toString());

		} catch (Exception e) {
			log.error("no connection with" + dev.getDevName()+e.getMessage());
		}
		return data;
	}

	private static void printResponse(PlcReadResponse response) {
		for (String fieldName : response.getFieldNames()) {
			if (response.getResponseCode(fieldName) == PlcResponseCode.OK) {
				int numValues = response.getNumberOfValues(fieldName);
				// If it's just one element, output just one single line.
				if (numValues == 1) {
					log.info("Value[" + fieldName + "]: " + response.getObject(fieldName));
				}
				// If it's more than one element, output each in a single row.
				else {
					log.info("Value[" + fieldName + "]:");
					for (int i = 0; i < numValues; i++) {
						log.info(" - " + response.getObject(fieldName, i));
					}
				}
			}
			// Something went wrong, to output an error message instead.
			else {
				log.error("Error[" + fieldName + "]: " + response.getResponseCode(fieldName).name());
			}
		}
	}
}