package com.sjtu.icare.modules.elder.service;

import java.sql.Date;
import java.util.List;

import com.sjtu.icare.modules.elder.entity.ElderBloodPressureEntity;
import com.sjtu.icare.modules.elder.entity.ElderEntity;
import com.sjtu.icare.modules.elder.entity.ElderHeartRateEntity;
import com.sjtu.icare.modules.elder.entity.ElderTemperatureEntity;

/**
 * @Description 老人体温对应的 service 类
 * @author WangQi
 * @date 2015-03-06
 */
public interface IElderHealthDataService {
	
	ElderEntity getElderEntity(int id);

	List<ElderTemperatureEntity> getElderTemperatureEntities(int elderId, String startDate, String endDate);

	void insertElderTemperatureRecord(int elderId, int doctorId,
			String temperature, String time);

	List<ElderBloodPressureEntity> getElderBloodPressureEntities(
			int elderId, String startDate, String endDate);

	void insertElderBloodPressureRecord(int elderId, Integer doctorId,
			String diastolicPressure, String systolicPressure, String time);
	
	List<ElderHeartRateEntity> getElderHeartRateEntity(int elderId, String startDate, String endDate);

	void insertElderHeartRateRecord(int elderId, Integer doctorId,
			String heartRate, String time);
	
	String getElderPhotoUrl();
	
	
	
}