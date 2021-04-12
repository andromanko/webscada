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

import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import webscada.entity.Dev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class modbusTCP{
//было в примере:
//    public static void main(String[] args) {
//        new MasterExample(100, 100).start();
//    }

    //private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//TODO здесь один мастер будет!
    private final List<ModbusTcpMaster> masters = new CopyOnWriteArrayList<>();
    private volatile boolean started = false;

//    private final int nMasters;
//    private final int nRequests;
//конструктор Вероятно прибить
//    public MasterExample(int nMasters, int nRequests) {
//        this.nMasters = nMasters;
//        this.nRequests = nRequests;
//    }

    public void start(Dev dev) {
        started = true;
        //TODO modbus Dev Config! IP,port
        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder(dev.getIP())
            .setPort(dev.getPort())
            .build();

        new Thread(() -> {
            while (started) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                double mean = 0.0;
                double oneMinute = 0.0;

                for (ModbusTcpMaster master : masters) {
                    mean += master.getResponseTimer().getMeanRate();
                    oneMinute += master.getResponseTimer().getOneMinuteRate();
                }

                log.info("Mean rate={}, 1m rate={}", mean, oneMinute);
            }
        }).start();

//        for (int i = 0; i < nMasters; i++) {
        	//TODO config ModbusTCPMaster
        	ModbusTcpMaster master = new ModbusTcpMaster(config);
            master.connect();

            masters.add(master);

//            for (int j = 0; j < nRequests; j++) {
            	//TODO send&Receive Master вот тут и делается обмен!!! Может вырезать это и вставить себе?
                sendAndReceive(master);
//            }
//        }
    }

    private void sendAndReceive(ModbusTcpMaster master) {
        if (!started) return;
//TODO здесь нужно вставит адреса!!!  (аддресс 0 , количество 10) 0 - unitID - хз что это 
        CompletableFuture<ReadHoldingRegistersResponse> future =
            master.sendRequest(new ReadHoldingRegistersRequest(0, 10), 0);

        future.whenCompleteAsync((response, ex) -> {
            if (response != null) {
                ReferenceCountUtil.release(response);
            } else {
                log.error("Completed exceptionally, message={}", ex.getMessage(), ex);
            }
            scheduler.schedule(() -> sendAndReceive(master), 1, TimeUnit.SECONDS);
        }, Modbus.sharedExecutor());
    }

    public void stop() {
        started = false;
        masters.forEach(ModbusTcpMaster::disconnect);
        masters.clear();
    }

}