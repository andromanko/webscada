package webscada.api.utils;

import webscada.entity.Dev;
import webscada.entity.DevType;

public interface IDevComm {
	//TODO пока что тут один интегер получаем. Доделать до общего случая!
	int DataReceive(Dev dev, DevType devType);
	//TODO пока тут один интегер передаем. Доделать до общего случая!
	void DataSend(Dev dev, DevType devType, int dataSend);
}
