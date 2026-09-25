package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//ログインページを表示
		goTo("http://localhost:8080/lms");

		//タイトルが正しいか検証
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA02");

		// パスワードを入力
		webDriver.findElement(By.id("password")).sendKeys("Asdfg12345");

		// ログインボタンを押下
		webDriver.findElement(By.cssSelector("input[type='submit'][value='ログイン']")).click();

		// タイトルが「コース詳細 | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("コース詳細 | LMS"));

		//タイトルが正しいか検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」をクリック
		webDriver.findElement(By.linkText("機能")).click();

		// 「ヘルプ」リンクをクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// タイトルが正しいか検証
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 現在のタブを取得
		String originalWindow = webDriver.getWindowHandle();

		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// 新しいタブに切り替え
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}

		// タイトルが正しいか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 「研修関係」カテゴリをクリック
		webDriver.findElement(By.xpath("//a[contains(normalize-space(.),'研修関係')]")).click();

		// 検索結果の先頭までスクロール
		WebElement result = webDriver.findElement(By.cssSelector("dt.mb10"));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", result);

		// 検索結果を取得
		List<WebElement> searchResultElements = webDriver.findElements(By.xpath("//dt[contains(@class,'mb10')]"));

		// 「キャンセル料・途中退校について」の検索結果を検証
		assertEquals("Q.キャンセル料・途中退校について",
				webDriver.findElement(By.xpath("//dt[contains(normalize-space(.),'キャンセル料・途中退校について')]"))
						.getText());

		// 「研修の申し込みはどのようにすれば良いですか？」の検索結果を検証
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？",
				webDriver.findElement(By.xpath("//dt[contains(normalize-space(.),'研修の申し込みはどのようにすれば良いですか？')]"))
						.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 「キャンセル料・途中退校について」の質問をクリック
		webDriver.findElement(By.xpath("//dt[contains(normalize-space(.),'キャンセル料・途中退校について')]")).click();

		// 回答を取得
		WebElement answer = webDriver.findElement(
				By.xpath("//dd[.//span[contains(text(),'受講者の退職や解雇等')]]"));

		// 回答が正しいことを検証
		assertTrue(answer.getText().contains(
				"A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
