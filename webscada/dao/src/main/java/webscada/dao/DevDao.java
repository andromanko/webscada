package webscada.dao;

import org.springframework.stereotype.Repository;
import webscada.api.dao.IDevDao;
import webscada.entity.Dev;

@Repository
public class DevDao extends AGenericDao<Dev> implements IDevDao {

    public DevDao() {
        super(Dev.class);
    }
}
