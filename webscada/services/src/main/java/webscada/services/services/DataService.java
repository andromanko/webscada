package webscada.services.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;

import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IDevJPADao;
import webscada.api.dto.DataDto;
import webscada.api.dto.DevDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;
import webscada.entity.Dev;
import webscada.entity.DevType;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;

@Slf4j
@Service
public class DataService implements IDataService {
	
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	// показывает СТАРТОВАЛО ЛИ ВООБЩЕ ПРИЛАГА? /ТАК БЫЛО В ПРИМЕРЕ. если не
	// стартовало - то и читать не будем
	private volatile boolean started = false;

	@Autowired
	private IDevService devService;

	@Autowired
	private IDevJPADao devDao; // тут нужно сделать JPAdao - чтобы вытягивать инфо о данных которые нужно
								// прочитать

	// здесь будут данные о текущем состоянии устройств. Скооннекчен/не сконнекчен
	// DevID и Bool
	private Map<Long, Boolean> devState = new HashMap<>();
	// TODO сделать данные общими. Может быть Number?
	private Map<Long, Number> currentData = new HashMap<>();
	
	int i; //
	public DataDto readData(DevDto devDto) {
		// вытащить всё что читаем из Dev
		// пробежаться for each
		// читать каждую переменную
		//устанавливаем коннект если коннекта нет
		DevDto device = devService.findDev(devDto.getId());
//	        }, Modbus.sharedExecutor());
		// а для начала - считаем одну переменную!
		
		// Establish a connection to the plc using the url provided as first argument
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection("modbus:tcp://"+device.getIP()+":"+device.getPort())) {
	            // Check if this connection support reading of data.
	            if (!plcConnection.getMetadata().canRead()) {
                log.error("This connection doesn't support reading.");
                return null;
            }
         
            //TODO get выборка данных по определенному устройству
           // List<Value> data = dataService. 
            
            // Create a new read request:
            // - Give the single item requested the alias name "value"
            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
            //for (int i = 0; i < options.getFieldAddress().length; i++) {
                builder.addItem("value-" + 1, "42080:UINT[10]");
                //builder.addItem("value-" + 2, "400001:UINT[10]");
            //}
            PlcReadRequest readRequest = builder.build();
            
//////////////////////////////////////////////////////////
// Read synchronously ...
// NOTICE: the ".get()" immediately lets this thread pause until
// the response is processed and available.
log.info("Synchronous request ...");
PlcReadResponse syncResponse = readRequest.execute().get();
// Simply iterating over the field names returned in the response.
i=syncResponse.getInteger("value-1");
        }
        catch(Exception e) {
        	  log.error("connEtction error");
        	}
        DataDto dataDto = new DataDto();
        
		dataDto.setDataName("dataName");
		dataDto.setDataUnit("мегапаскалейТЕСТ!");
		dataDto.setId(1);
		int data = i;
		dataDto.setValue(data);

		return dataDto;
	};

	@Override
	public boolean writeData(DataDto dataDto, DevDto devDto) {
		// TODO Auto-generated method stub
		return false;
	}

	// прочитать данные со всех устройств
	@Override
	public List<DataDto> readAllData(List<DevDto> devices) {

		//if (!started) DevicesConnect();
			//return null;
		// получаем список всех устройств
		// List<Dev> devices = devDao.findAll();
		List<DataDto> data = new LinkedList<DataDto>();
		// пробегаемся по списку и из каждого устройства читаем данные
		for (DevDto device : devices) {
			//if (devState.get(device.getId())) {
				DataDto dataDto = readData(device);
				data.add(dataDto);
			//}

		}
		return data;
	}

}
