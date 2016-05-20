package controllers.api;

import play.mvc.*;

import utils.ConfigUtil;
import views.html.*;
import play.*;
import play.libs.Json;
import play.libs.F.Option;

import java.sql.Date;
import java.util.List;

import models.entity.Check;
import models.response.Check.CheckPagingResponse;
import models.response.Check.CheckResponse;
import models.response.Check.ChecksDefaultResponse;
import models.setting.CheckYouStatusSetting;
import models.service.api.Check.CheckResponseService;

public class ChecksAPIController extends Controller {

	// 診断結果取得
	public static Result checks(Long id) {
		ChecksDefaultResponse result = new ChecksDefaultResponse();
		Option<Check> checkOps = new Check(id).unique();
		// 要実装（resultのプロパティに値セット）
		if (checkOps.isDefined()) {
			CheckYouStatusSetting status = CheckYouStatusSetting.OK;
			result.code = status.code;
			result.status = status.message;
			result.message = "test";
			CheckResponseService CRS = new CheckResponseService();
			result.result = CRS.getCheckResponse(checkOps.get()).get();
			return ok(Json.toJson(result));
		}
		return badRequest(Json
				.toJson(result.badRequest(ConfigUtil.get("checkYou.setting.message.error.noResult").getOrElse(""))));

	}

	// 診断結果の一覧取得
	public static Result getList(Integer page) {
		CheckPagingResponse result = new CheckPagingResponse();
		Option<List<Check>> list = new Check().entitiesList(page);
		Integer maxPage = new Check().entitiesMaxPage(0);
		if (list.isDefined()) {
			CheckYouStatusSetting status = CheckYouStatusSetting.OK;
			result.code = status.code;
			result.message = status.message;
			result.maxPage = page;
			result.results = list.get();
			return ok(Json.toJson(result));
		}
		return badRequest(Json.toJson
				(result.badRequest(ConfigUtil.get("checkYou.setting.message.error.noResultList").getOrElse(""))));
	}
}
