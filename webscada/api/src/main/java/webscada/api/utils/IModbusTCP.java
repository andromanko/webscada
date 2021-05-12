package webscada.api.utils;

import org.springframework.stereotype.Service;

import webscada.entity.Dev;
import webscada.entity.Value;
import java.util.List;

@Service
public interface IModbusTCP {
	public void start(Dev dev, List<Value> values);
	public void stop();
	
}
