package com.x.cms.assemble.control.jaxrs.appinfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.cache.ApplicationCache;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WoId;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.base.core.project.tools.ListTools;
import com.x.cms.assemble.control.service.LogService;
import com.x.cms.core.entity.AppInfo;
import com.x.cms.core.entity.CategoryInfo;
import com.x.cms.core.entity.Document;
import com.x.cms.core.entity.element.AppDict;
import com.x.cms.core.entity.element.AppDictItem;
import com.x.cms.core.entity.element.View;
import com.x.cms.core.entity.element.ViewCategory;
import com.x.cms.core.entity.element.ViewFieldConfig;

/**
 * 保存CMS栏目信息设置
 * @author O2LEE
 *
 */
public class ActionSave extends BaseAction {

	private static  Logger logger = LoggerFactory.getLogger(ActionSave.class);

	protected ActionResult<Wo> execute(HttpServletRequest request, EffectivePerson effectivePerson, JsonElement jsonElement ) throws Exception {
		ActionResult<Wo> result = new ActionResult<>();
		AppInfo appInfo = null;
		List<String> ids = null;
		String identityName = null;
		String unitName = null;
		String topUnitName = null;
		Wi wi = null;
		Boolean check = true;

		try {
			wi = this.convertToWrapIn( jsonElement, Wi.class );
			identityName = wi.getIdentity();
		} catch (Exception e) {
			check = false;
			Exception exception = new ExceptionAppInfoProcess(e, "系统在将JSON信息转换为对象时发生异常。JSON:" + jsonElement.toString());
			result.error(exception);
			logger.error(e, effectivePerson, request, null);
		}
		
		if (check) {
			if( "cipher".equalsIgnoreCase( effectivePerson.getDistinguishedName() )) {
				identityName = "cipher";
				unitName = "cipher";
				topUnitName = "cipher";
			}else if( "xadmin".equalsIgnoreCase( effectivePerson.getDistinguishedName() )) {
				identityName = "xadmin";
				unitName = "xadmin";
				topUnitName = "xadmin";
			}else {
				try {
					identityName = userManagerService.getPersonIdentity( effectivePerson.getDistinguishedName(), identityName );
				} catch (Exception e) {
					check = false;
					Exception exception = new ExceptionAppInfoProcess( e, "系统获取人员身份名称时发生异常，指定身份：" + identityName );
					result.error(exception);
					logger.error(e, effectivePerson, request, null);
				}
			}
		}

		if (check && !"xadmin".equals(identityName)&& !"cipher".equals(identityName)) {
			try {
				unitName = userManagerService.getUnitNameByIdentity( identityName );
			} catch (Exception e) {
				check = false;
				Exception exception = new ExceptionAppInfoProcess(e, "系统在根据用户身份信息查询所属组织名称时发生异常。Identity:" + identityName);
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}
		if (check && !"xadmin".equals(identityName)&& !"cipher".equals(identityName)) {
			try {
				topUnitName = userManagerService.getTopUnitNameByIdentity( identityName );
			} catch (Exception e) {
				check = false;
				Exception exception = new ExceptionAppInfoProcess(e, "系统在根据用户身份信息查询所属顶层组织名称时发生异常。Identity:" + identityName);
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}

		if (check) {
			if ( StringUtils.isEmpty( wi.getAppName() ) ) {
				check = false;
				Exception exception = new ExceptionAppInfoNameEmpty();
				result.error(exception);
			}
		}
		
		if (check) {
			if( "数据".equals( wi.getDocumentType() )) {
				wi.setDocumentType( "数据" );
			}else {
				wi.setDocumentType( "信息" );
			}
		}
		
		if (check) {
			try {
				//判断栏目信息是否已经存在，栏目信息不允许重名
				ids = appInfoServiceAdv.listByAppName( wi.getAppName());
				if ( ListTools.isNotEmpty( ids ) ) {
					for (String tmp : ids) {
						if (tmp != null && !tmp.trim().equals( wi.getId())) {
							check = false;
							Exception exception = new ExceptionAppInfoNameAlreadyExists( wi.getAppName());
							result.error(exception);
						}
					}
				}
			} catch (Exception e) {
				check = false;
				Exception exception = new ExceptionAppInfoProcess(e, "系统根据应用栏目名称查询应用栏目信息对象时发生异常。AppName:" + wi.getAppName());
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}
		if (check) {
			wi.setCreatorIdentity(identityName);
			wi.setCreatorPerson(effectivePerson.getDistinguishedName());
			wi.setCreatorUnitName( unitName );
			wi.setCreatorTopUnitName( topUnitName );
			
			try {
				//保存CMS栏目信息
				appInfo = appInfoServiceAdv.save( wi, effectivePerson );

				// 更新缓存
				ApplicationCache.notify(AppInfo.class);
				ApplicationCache.notify(AppDict.class);
				ApplicationCache.notify(AppDictItem.class);
				ApplicationCache.notify(CategoryInfo.class);
				ApplicationCache.notify(View.class);
				ApplicationCache.notify(ViewCategory.class);
				ApplicationCache.notify(ViewFieldConfig.class);
				ApplicationCache.notify(Document.class);

				new LogService().log(null, effectivePerson.getDistinguishedName(), appInfo.getAppName(), appInfo.getId(), "", "", "", "APPINFO", "保存");
				Wo wo = new Wo();
				wo.setId( appInfo.getId() );
				result.setData( wo );
			} catch (Exception e) {
				check = false;
				Exception exception = new ExceptionAppInfoProcess(e, "应用栏目信息保存时发生异常。");
				result.error(exception);
				logger.error(e, effectivePerson, request, null);
			}
		}
		return result;
	}

	public static class Wi extends AppInfo {
		
		private static final long serialVersionUID = -6314932919066148113L;
		
		private String identity = null;

		public String getIdentity() {
			return identity;
		}

		public void setIdentity(String identity) {
			this.identity = identity;
		}
		public static WrapCopier<Wi, AppInfo> copier = WrapCopierFactory.wi( Wi.class, AppInfo.class, null, null );
	}

	public static class Wo extends WoId {

	}
	
}