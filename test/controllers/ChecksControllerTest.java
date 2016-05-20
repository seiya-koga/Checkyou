package controllers;
import apps.FakeApp;
import models.entity.Check;
import models.service.Check.CheckModelService;
import models.service.Check.CheckModelServiceTest;

import com.avaje.ebean.Ebean;
import org.junit.Test;

import play.libs.F.Option;
import play.libs.F.Some;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeApplication;

import java.util.List;

public class ChecksControllerTest extends FakeApp {

    // 通常のルートへのアクセス
    @Test
    public void testIndexIsOk() throws Exception {
        Result result = route(fakeRequest(GET, "/"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Twitterのユーザー名を入れてください");
    }
//    // Idつきの/resultへのアクセス
//    @Test
//    public void testResultIdWithId() throws Exception {
//      // 要実装（確認内容はindexアクションテスト内容と一緒、表示されるべき文言はなにか１つ確認すればよい）
//    	Ebean.execute(Ebean.createSqlUpdate(
//				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
//
//    	  Result result = route(fakeRequest(GET, "/result/1"));
//    assertThat(status(result)).isEqualTo(OK);
//    assertThat(contentType(result)).isEqualTo("text/html");
//    assertThat(charset(result)).isEqualTo("utf-8");
//    assertThat(contentAsString(result)).contains("kara_d");
//    }



    // 1ページ目には1件目のデータ、５件目のデータの任意の文字列がそれぞれ含まれる
    @Test
    public void testRecentShouldContainAAndE() throws Exception {
        // 要実装
    	CheckModelServiceTest.prepareSaveData();
    	Option<List<Check>> result = new CheckModelService().findWithPage(1);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().size()).isEqualTo(5);
		assertThat(result.get().get(0).getId()).isEqualTo(1L);
		assertThat(result.get().get(4).getId()).isEqualTo(5L);


    }

//    // 1ページ目には６件目のデータの任意の文字列は含まれない
//    @Test
//    public void testRecentShouldNotContainF() throws Exception {
//        // 要実装
//    }
//
//
//    // 2ページ目は、６件目のデータの任意の文字列を含む, ５件目のデータの任意の文字列は含まれない
//    @Test
//    public void testRecentPage2ShouldContainF() throws Exception {
//        // 要実装
//    }
}