package Demo3;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.android.AndroidDriver;

public class ScrollChromeW3C {

    public static void main(String[] args) {
        AndroidDriver driver = null;
        try {
            // Sử dụng method riêng để lấy capabilities
            DesiredCapabilities caps = getChromeCapabilities();

            // 2. Kết nối Appium Server
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
            Thread.sleep(3000); // Chờ app load

            System.out.println("Chrome Started");

            // 3. Lướt xuống 4 lần
            for (int i = 1; i <= 4; i++) {
                scrollDown(driver);
                Thread.sleep(1500);
            }

            // 4. Lướt lên 3 lần
            for (int i = 1; i <= 3; i++) {
                scrollUp(driver);
                Thread.sleep(1500);
            }

            System.out.println("Scroll Actions Completed");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) driver.quit();
        }
    }

    // Method tách riêng cấu hình
    private static DesiredCapabilities getChromeCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", "emulator-5554");
        caps.setCapability("appium:automationName", "uiautomator2");
        caps.setCapability("appium:platformVersion", "15");
        caps.setCapability("appium:appPackage", "com.android.chrome");
        caps.setCapability("appium:appActivity", "com.google.android.apps.chrome.Main");
        caps.setCapability("noReset", true);
        return caps;
    }

    // Scroll Down
    public static void scrollDown(AndroidDriver driver) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        swipe(driver, width / 2, height * 3 / 4, width / 2, height / 4);
        System.out.println("Scrolled Down");
    }

    // Scroll Up
    public static void scrollUp(AndroidDriver driver) {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        swipe(driver, width / 2, height / 4, width / 2, height * 3 / 4);
        System.out.println("Scrolled Up");
    }

    // W3C Swipe Action
    public static void swipe(AndroidDriver driver, int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
    }
}
