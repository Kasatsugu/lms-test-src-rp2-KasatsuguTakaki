package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// コース詳細画面で2025年7月10日(木)の「詳細」ボタンを押下
		WebElement detailButton = webDriver.findElement(
				By.xpath("//tr[td[contains(text(),'2025年7月10日')]]//input[@value='詳細']"));
		detailButton.click();

		// 「セクション詳細 | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));

		// 選択した日付の画面に遷移しているかタイトルと見出しを取得して検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		WebElement headline = webDriver.findElement(By.tagName("h2"));
		assertEquals("2025年7月10日",
				headline.findElement(By.tagName("small")).getText());

		// スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「日報を提出する」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(1).click();

		// 「レポート登録 | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("レポート登録 | LMS"));

		//タイトルが正しいか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//テキストボックスに日報の内容を入力
		String report = "日報テスト";
		webDriver.findElement(By.className("form-control")).sendKeys(report);

		//「日報を提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// 「セクション詳細 | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//「日報を提出する」ボタンが
		//「提出済み日報を確認する」ボタンに更新されているか検証
		String btnText = webDriver.findElements(By.cssSelector(".btn.btn-default")).get(1).getAttribute("value");
		assertEquals("提出済み日報【デモ】を確認する", btnText);

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
