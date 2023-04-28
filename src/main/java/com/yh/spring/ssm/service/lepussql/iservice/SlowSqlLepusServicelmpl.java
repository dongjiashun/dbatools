package com.yh.spring.ssm.service.lepussql.iservice;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.yh.spring.ssm.bean.SlowSqlLepus;
import com.yh.spring.ssm.public_dao.DaoSupport;
import com.yh.spring.ssm.service.lepussql.SlowSqlLepusService;
@ SuppressWarnings( "unchecked" )
@Component(value="SlowSqlLepusServicelmpl")
public class SlowSqlLepusServicelmpl implements SlowSqlLepusService {
	@ Resource( name = "daoSupport" )
	 private DaoSupport dao ;


	@Override
	public List<SlowSqlLepus> select_SlowSqlLepus(String date) throws Exception {
		List<SlowSqlLepus> list = (List<SlowSqlLepus>) dao.findForList("slowsqllepus.select_slowsqllepus", date);
		if(list != null){
			return list;
		}
		return list;
	}
	
}
