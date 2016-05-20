package controllers.api;

import com.avaje.ebean.Ebean;
import apps.FakeApp;
import org.junit.Test;
import play.mvc.Result;
import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ChecksAPIControllerTest extends FakeApp{

    // 要実装
    // 取得したID=1のデータにname, resultの文言が含まれる
    // コンテンツタイプは"application/json"

    // 要実装
    // データ一覧取得API, 指定したページ（１ページ目）の１件目、５件目データの文字列（name, result）が含まれていること、６件目のデータの文言が含まれていないこと
    

    @Test
    public void testGetList002() throws Exception {
      // 要実装
      // データ一覧取得API, 指定したページ（３ページ目）の結果がエラー値であること、１，２ページ目のデータの文言（name, result）が含まれていないこと
    }
}
