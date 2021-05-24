package webscada.api.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import webscada.api.dto.ValueReal;
import webscada.entity.Dev;
import webscada.entity.Value;

@Service
public interface IModbusTCP {

	Map<Long, ValueReal> start(Dev dev, List<Value> values);
}
