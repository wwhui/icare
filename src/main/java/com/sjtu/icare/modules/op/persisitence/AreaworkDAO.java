package com.sjtu.icare.modules.op.persisitence;

import java.util.List;

import com.sjtu.icare.common.persistence.annotation.MyBatisDao;
import com.sjtu.icare.modules.op.entity.AreaworkEntity;

/**
 * @Description 房间护工工作职责的 Mapper
 * @author lzl
 * @date 2015-03-13
 */

@MyBatisDao
public interface AreaworkDAO {

	AreaworkEntity getCareworkEntityById(int id);
	
	List<AreaworkEntity> getCareworkEntitiesByCarerid(int carerId);
	
	AreaworkEntity getCareworkEntityByElderid(int areaId);
	
}