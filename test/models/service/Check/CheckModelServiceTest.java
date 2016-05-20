package models.service.Check;

import com.avaje.ebean.Ebean;
import apps.FakeApp;
import models.entity.Check;
import org.junit.Test;
import java.util.List;

import static play.libs.F.*;
import static org.fest.assertions.Assertions.assertThat;

public class CheckModelServiceTest extends FakeApp {

	// 正常系（Some）：1件のレコードから1つ取り出す
	@Test
	public void testFindByIdTo1ReturnSome() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
		Option<Check> model = new CheckModelService().findById(1L);
		assertThat(model.getClass()).isEqualTo(Some.class);
		assertThat(model.get().getId()).isEqualTo(1L);
		assertThat(model.get().getName()).isEqualTo("test_t");
		assertThat(model.get().getResult()).isEqualTo("test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。");
	}

	// 要実装
	// 正常系（None）：1件のレコードから該当しないIdのものを取り出す
	@Test
	public void testFindByIdTo2ReturnSome() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
		Option<Check> model = new CheckModelService().findById(2L);
		assertThat(model.getClass()).isEqualTo(None.class);
	}

	// 要実装
	// 異常系（None）：1件のレコードから検索のキーとしてnullを渡す

	// 要実装
	// OKケース
	// Checkモデルのインスタンスを作成し、データベースに保存する（Option型, ID確認）
	@Test
	public void testSaveToRightParamSuccess() throws Exception {
		Check entry = new Check("kara_d", "kara_dさんにオススメなplay frameworkのバージョンは、2.1.3 Javaです。");
		Option<Check> result = new CheckModelService().save(entry);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().getId()).isEqualTo(1L);
	}

	// 要実装
	// NGケース
	// Checkモデルのインスタンスがnullだが、データベースに保存する
	@Test
	public void testSaveToNullFaill() throws Exception {
		Check entry = null;
		Option<Check> result = new CheckModelService().save(entry);
		assertThat(result.getClass()).isEqualTo(None.class);
	}

	// 要実装
	// ページ1に5件存在し、Idが1と5が存在する（Some型、件数、ID=1L, 5Lを確認）
	@Test
	public void testFindWithPage1Contains1And5() throws Exception {
		prepareSaveData();
		Option<List<Check>> result = new CheckModelService().findWithPage(1);
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get().size()).isEqualTo(5);
		assertThat(result.get().get(0).getId()).isEqualTo(1L);
		assertThat(result.get().get(4).getId()).isEqualTo(5L);
	}
	//
	// // 要実装（Some型、件数、ID=6Lを確認）
	// // ページ2に1件存在し、Idが6が存在している
	// @Test
	// public void testFindWithPage2Contains1And5() throws Exception {
	// prepareSaveData();
	// Option<List<Check>> result = new CheckModelService().findWithPage(2);
	// assertThat(result.getClass()).isEqualTo(Some.class);
	// assertThat(result.get().size()).isEqualTo(1);
	// assertThat(result.get().get(0).getId()).isEqualTo(1L);
	// }

	// 要実装
	// ページ3は存在しない

	public static void prepareSaveData() {
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_a',  'test_aさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:11', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('2',  'test_b',  'test_bさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:12', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('3',  'test_c',  'test_cさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:13', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('4',  'test_d',  'test_dさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:14', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('5',  'test_e',  'test_eさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:15', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('6',  'test_f',  'test_fさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:16', '2013-08-20 12:34:56');"));
	}

	// 要実装
	// 1件しかデータがない場合、MaxPageは1（Option型、ページ数確認）
	@Test
	public void testGetMaxPageIs1() throws Exception {
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
		Ebean.execute(Ebean.createSqlUpdate(
				"INSERT INTO  `check` (`id`, `name`, `result`, `created`, `modified`) VALUES ('5',  'test_t',  'test_tさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));
		Option<Integer> result = new CheckModelService().getMaxPage();
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get()).isEqualTo(1);

	}

	// 要実装
	// 0件しかデータがない場合、MaxPageは0（Option型、ページ数確認）
	@Test
	public void testGetMaxPageIsZero() throws Exception {
		Option<Integer> result = new CheckModelService().getMaxPage();
		assertThat(result.getClass()).isEqualTo(Some.class);
		assertThat(result.get()).isEqualTo(0);
	}

	// 要実装
	// 6件データがある場合はMaxPageは2（Option型、ページ数確認）
}