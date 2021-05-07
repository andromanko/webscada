package webscada.services.services;

import java.util.ArrayList;
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
import webscada.api.dao.IUserJPADao;
import webscada.api.dao.IValueJPADao;
import webscada.api.dto.DevDto;
import webscada.api.dto.ValueDto;
import webscada.api.dto.DevTypeDto;
import webscada.api.dto.DevTypeIdsDto;
import webscada.api.dto.UserDto;
import webscada.api.dto.ValDevIdsDto;
import webscada.api.mappers.DevMapper;
import webscada.api.mappers.ValueMapper;
import webscada.api.services.IValueService;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;
import webscada.entity.Value;
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

	//private final ValueMapper valueMapper;
	
	@Autowired
	private IValueJPADao valueJPADao;
	
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private volatile boolean started = false;

	@Autowired
	private IDevService devService;

//TODO  тут нужно сделать JPAdao - чтобы вытягивать инфо о данных которые нужно прочитать
	@Autowired
	private IDevJPADao devDao;

	@Override
	public List<ValueDto> readAllData(List<DevDto> devices) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<Long, Number> readDevData(DevDto devDto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean writeData(ValueDto dataDto, DevDto devDto) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
