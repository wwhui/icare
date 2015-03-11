/**
 * @Package com.sjtu.icare.modules.gero.webservice
 * @Description gero area controller
 * @date Mar 9, 2015 4:12:24 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.gero.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.utils.BasicReturnedJson;
import com.sjtu.icare.common.utils.CommonUtils;
import com.sjtu.icare.common.utils.ParamUtils;
import com.sjtu.icare.common.web.rest.MediaTypes;
import com.sjtu.icare.common.web.rest.RestException;
import com.sjtu.icare.modules.elder.webservice.ElderTemperatureRestController;
import com.sjtu.icare.modules.gero.entity.GeroAreaEntity;
import com.sjtu.icare.modules.gero.service.IGeroAreaService;

@RestController
@RequestMapping("/gero/{gid}/area")
public class GeroAreaRestController {
	private static Logger logger = Logger.getLogger(ElderTemperatureRestController.class);

	@Autowired
	private IGeroAreaService geroAreaService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroAreas(
			@PathVariable("gid") int geroId
			) {
		
		// 获取基础的 JSON返回
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
	
		// 参数检查
		// 参数预处理
		
		try {
			
			List<GeroAreaEntity> geroAreaEntities = geroAreaService.getGeroAreas(geroId);
			
			// 构造返回的 JSON
			for (GeroAreaEntity geroAreaEntity : geroAreaEntities) {
				Map<String, Object> resultMap = new HashMap<String, Object>(); 
				resultMap.put("id", geroId); 
				resultMap.put("parent_id", geroAreaEntity.getParentId()); 
				resultMap.put("parent_ids", geroAreaEntity.getParentIds()); 
				resultMap.put("type", geroAreaEntity.getType()); 
				resultMap.put("level", geroAreaEntity.getLevel()); 
				resultMap.put("name", geroAreaEntity.getName()); 

				basicReturnedJson.addEntity(resultMap);
			}

			return basicReturnedJson.getMap();
			
		} catch (Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_GET_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}
	
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Object postGeroArea(
			@PathVariable("gid") int geroId,
			@RequestBody String inJson
			) {	
		// 将参数转化成驼峰格式的 Map
		Map<String, Object> tempRquestParamMap = ParamUtils.getMapByJson(inJson, logger);
		Map<String, Object> requestParamMap = CommonUtils.convertMapToCamelStyle(tempRquestParamMap);
		
		Integer parentId;
		Integer type;
		Integer level;
		String name;
		
		try {
			parentId = (Integer) requestParamMap.get("parentId");
			type = (Integer) requestParamMap.get("type");
			level = (Integer) requestParamMap.get("level");
			name = (String) requestParamMap.get("name");
			
			if (parentId == null || type == null || level == null || name == null)
				throw new Exception();
			
			// 参数详细验证
			// TODO
			
		} catch(Exception e) {
			String otherMessage = "[parent_id=" + requestParamMap.get("parentId") + "]" +
					"[type=" + requestParamMap.get("type") + "]" +
					"[name=" + requestParamMap.get("name") + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_POST_PARAM_INVALID, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		
		// 获取基础的 JSON
		BasicReturnedJson basicReturnedJson = new BasicReturnedJson();
		
		// TODO
		// 插入数据
		try {
			GeroAreaEntity postEntity = JSONObject.toJavaObject((JSON) requestParamMap, GeroAreaEntity.class);
			geroAreaService.insertGeroAreaRecord(postEntity);
		} catch(Exception e) {
			String otherMessage = "[" + e.getMessage() + "]";
			String message = ErrorConstants.format(ErrorConstants.GERO_AREA_POST_SERVICE_FAILED, otherMessage);
			logger.error(message);
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
		}

		return basicReturnedJson.getMap();
		
	}
	
	// TODO
	@RequestMapping(value = "/{aid}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Object getGeroAreaAndSubareas(
			@PathVariable("gid") int geroId,
			@PathVariable("aid") int areaId,
			@RequestParam(value="sub_level", required=false) String subLevel
			) {
					
		return null;
		
	}
}
