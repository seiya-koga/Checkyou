package controllers;

import play.mvc.*;
import utils.ConfigUtil;
import utils.OptionUtil;
import views.html.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import models.entity.*;
import models.service.Check.CheckModelService;
import play.*;
import play.data.Form;
import play.libs.F.Option;
import models.request.Check.ResultPostRequest;

public class ChecksController extends Application {

	public static Result index() throws UnsupportedEncodingException {
		session("sessionText", "せっしょん");
		response().setCookie("myName",URLEncoder.encode("くっきー", "UTF-8"));

		// 要実装
		// 文字列は、application.confで設定したcheckyou.setting.message.title、checkyou.setting.message.questionを渡す
		String title = ConfigUtil.get("checkyou.setting.message.title").get();
		String message = ConfigUtil.get("checkyou.setting.message.question").get();

		return ok(index.render(title, message, new Form<ResultPostRequest>(ResultPostRequest.class)));
	}

	public static Result result() throws UnsupportedEncodingException {

		Form<ResultPostRequest> form = Form.form(ResultPostRequest.class).bindFromRequest();

		// バリデーションチェック
		// error.required
		if (form.error("name") != null && form.error("name").message().contains("error.required")) {
			return validationErrorIndexResult("名前欄が空です", form);
		}
		//// // error.pattern
		//// // 要実装（返す文言："Twitter id形式ではありません")
		if (form.error("name") != null && form.error("name").message().contains("error.pattern")) {
			return validationErrorIndexResult("Twitter id形式ではありません", form);
		}
		//// // error.maxLength
		//// // 要実装（返す文言："文字数が15文字を超えています")
		if (form.error("name") != null && form.error("name").message().contains("error.maxLength")) {
			return validationErrorIndexResult("文字数が１５文字を超えています", form);
		}
		// // 要実装
		String name = form.data().get("name");
		Check check = new Check(name);
		String result = check.result().get();
		Check check1 = new Check(name, result);
		check1.save();
		Option<String> mySession = OptionUtil.apply(session("sessionText"));
		String cookie = URLDecoder.decode(request().cookie("myName").value(),"UTF-8");


		String title = mySession.get() + ConfigUtil.get("checkyou.setting.message.resultTitle").getOrElse("") + cookie;
		return ok(views.html.result.render(title,check1));
	}

	// バリデーションエラーメッセージを表示し、トップページへ戻る
	public static Result validationErrorIndexResult(String message, Form<ResultPostRequest> form) {
		flash("error", message);
		return badRequest(index.render(ConfigUtil.get("checkyou.setting.message.title").getOrElse(""),
				ConfigUtil.get("checkyou.setting.message.question").getOrElse(""), form));
	}
	// TwitterID取得
	// TwitterIDに基いて作成したCheckインスタンス保存
	// 保存したOption型のCheckインスタンスが存在する場合、「Twitter名 +
	// checkYou.setting.message.resultTitle」, 保存したCheckインスタンスを返す
	// 保存したOption型のCheckインスタンスが存在しない場合、Applicationクラスのfail呼び出し（redirect先：indexビュー、key:"error",
	// message: "診断エラーです"

	// 診断結果ページシェア用
	public static Result resultId(Long id) {
		Option<Check> check = new Check(id).unique();
		return check.isDefined() ? ok(result.render(
				check.get().name + ConfigUtil.get("checkyou.setting.message.resultTitle").getOrElse(""), check.get()))
				: Application.fail(routes.ChecksController.index(), "error", "診断エラーです");
	}

	public static Result recent(Integer page) {
		Option<List<Check>> result = new Check().entitiesList(page);
		Integer maxPage = new Check().entitiesMaxPage(1);
		return result.isDefined()
				? ok(recent.render(ConfigUtil.get("checkyou.setting.message.recentTitle").getOrElse(""),
						ConfigUtil.get("checkyou.setting.message.recentDescription").getOrElse(""), result.get(), page,
						maxPage))
				: ok(recentEmpty.render(ConfigUtil.get("checkYou.setting.message.recentTitle").getOrElse(""),
						ConfigUtil.get("checkYou.setting.message.recentDescriptionNone").getOrElse("")));
		// 最大ページ数取得できない場合、valueを返す
		// isDefined()で判定しなくても診断件数０でもresultはtrueになるのでget()で取得してサイズ判定
		// return result.get().size() != 0 ? 有件データをrecentビューに返す :
		// checkyou.setting.message.recentTitle,
		// checkyou.setting.message.recentDescriptionNoneをrecentEmptyビューに返す
	}
}
