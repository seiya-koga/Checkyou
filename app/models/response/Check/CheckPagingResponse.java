package models.response.Check;

import java.util.List;

import models.entity.Check;
import models.setting.CheckYouStatusSetting;

// API用診断結果一覧のレスポンスモデル
public class CheckPagingResponse {

    public Integer code;
    public String status;
    public String message;
    public Integer maxPage;
    public List<Check> results;

    // BadRequestを取得
    public ChecksDefaultResponse badRequest(String message) {
    	 ChecksDefaultResponse result = new ChecksDefaultResponse();
         CheckYouStatusSetting status = CheckYouStatusSetting.NO_RESULT;
         result.code    = status.code;
         result.status  = status.message;
         result.message = message;
         return result;
    }
}