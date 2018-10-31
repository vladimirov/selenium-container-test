import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public abstract class Listener implements ITestListener {

    File recordingDir;


    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        recordingDir = new File("target/PASSED-" + System.currentTimeMillis());
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        recordingDir = new File("target/FAILED-" + System.currentTimeMillis());
        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
    }


}
