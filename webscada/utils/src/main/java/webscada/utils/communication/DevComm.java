package webscada.utils.communication;

import webscada.api.utils.IDevComm;
import webscada.entity.Dev;
import webscada.entity.DevType;

public class DevComm implements IDevComm {

	@Override
	public int DataReceive(Dev dev, DevType devType) {
		// TODO в зависимости от devType раскидываем по методам для разных коммуникаций.
		//пока что делаем для devType Modbus tcp
		
		return 0;
	}

	@Override
	public void DataSend(Dev dev, DevType devType, int dataSend) {
		// TODO Auto-generated method stub
		
	}

}
