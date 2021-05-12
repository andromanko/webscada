package webscada.utils.communication;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletionStage;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.apache.plc4x.java.api.value.PlcValue;

import lombok.extern.slf4j.Slf4j;
import webscada.api.utils.IModbusTCP;
import webscada.entity.Dev;
import webscada.entity.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class modbusTCP implements IModbusTCP {
//было в примере:
//    public static void main(String[] args) {
//        new MasterExample(100, 100).start();
//    }

	// private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//TODO здесь один мастер будет!
//    private final List<ModbusTcpMaster> masters = new CopyOnWriteArrayList<>();
	private volatile boolean started = false;

//    private final int nMasters;
//    private final int nRequests;
//конструктор Вероятно прибить
//    public MasterExample(int nMasters, int nRequests) {
//        this.nMasters = nMasters;
//        this.nRequests = nRequests;
//    }

	public void start(Dev dev, List<Value> values) {
		started = true;

		// --------------------------------------------------
		// Establish a connection to the plc using the url provided as first argument
		try (PlcConnection plcConnection = new PlcDriverManager().getConnection("modbus:tcp://128.65.22.153:502")) {

			// Check if this connection support reading of data.
			if (!plcConnection.getMetadata().canRead()) {
				log.error("This connection doesn't support reading.");
				return;
			}

			// Create a new read request:
			// - Give the single item requested the alias name "value"
			PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();

			// for (int i = 0; i < options.getFieldAddress().length; i++) {

			builder.addItem("value-" + "200", "1");
			// }
			PlcReadRequest readRequest = builder.build();

			//////////////////////////////////////////////////////////
			// Read synchronously ...
			// NOTICE: the ".get()" immediately lets this thread pause until
			// the response is processed and available.
			log.info("Synchronous request ...");
			PlcReadResponse syncResponse = readRequest.execute().get();
			// Simply iterating over the field names returned in the response.
			printResponse(syncResponse);

			PlcValue asPlcValue = syncResponse.getAsPlcValue();
			System.out.println(asPlcValue.toString());

			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// --------------------------------------------------

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
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}