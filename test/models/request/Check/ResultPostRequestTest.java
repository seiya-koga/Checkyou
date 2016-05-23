package models.request.Check;

import org.junit.Test;
import play.data.Form;
import static org.fest.assertions.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;

import static play.data.Form.form;

public class ResultPostRequestTest {

// OKケース
    /**
     * 正しいId形式
     */
    @Test
    public void testValidationToRightParam() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "test_v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }

    // 要実装
    // アンダーバーなし（testv）
    @Test
    public void testValidationToRightParam2() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "testv");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isFalse();
    }
    // 数字のみ
    @Test
    public void testValidationToRightParam3() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "12345");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(!form.hasErrors());
    }
    // 数値、英数、アンダーバー混合
    @Test
    public void testValidationToRightParam4() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "1_a");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(!form.hasErrors());
    }

// NGケース
    /**
     * ハイフンつき
     */
    @Test
    public void testValidationToWrongParamWithHyphen() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "<test-v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors()).isTrue();
    }

    // 要実装
    // 禁止文字">"を含む
    @Test
    public void testValidationToWrongParam2() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "test>v");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors());
    }
    // ひらがなを含む
    @Test
    public void testValidationToWrongParamWithKana() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "testあv");
        Form<ResultPostRequest> form = form(ResultPostRequest.class).bind(map);
        assertThat(form.hasErrors());
    }
}