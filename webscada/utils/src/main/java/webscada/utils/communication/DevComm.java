package webscada.utils.communication;

import lombok.experimental.UtilityClass;
import webscada.api.utils.IDevComm;
import webscada.entity.Dev;
import webscada.entity.DevType;

@UtilityClass
public class DevComm {// implements IDevComm {

	//@Override
	public int DataReceive(Dev dev) {
		// TODO в зависимости от devType раскидываем по методам для разных коммуникаций.
		//пока что делаем для devType Modbus tcp
		
		
		
		return 0;
	}

	//@Override
	public void DataSend(Dev dev, int dataSend) {
		// TODO Auto-generated method stub
		
	}

}
