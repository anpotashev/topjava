package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimeTestRule extends Stopwatch {
//    private static MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
    private static List<String> results = new LinkedList<>();

    private static void logResult(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info("Test result:");
        printCaption();
        addAndLogResults(testName, status, nanos);
    }

    private static void addAndLogResults(String testName, String status, long nanos) {
        String testResult = String.format("   %-20s %-20s %d", testName, status, TimeUnit.NANOSECONDS.toMillis(nanos));
        results.add(testResult);
        log.info(testResult);
    }

    private static void printCaption() {
        String result = String.format("%-20s %-20s %12s", "method name", "result", "duration (ms)");
        log.info(result);
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logResult(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logResult(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logResult(description, "skipped", nanos);
    }

//    @Override
//    protected void finished(long nanos, Description description) {
////        logResult(description, "finished", nanos);
//    }

    public static void testsResults() {
        log.info("");
        log.info("Tests results:");
        printCaption();
        results.forEach(log::info);

    }

    public static void clearResults() {
        results.clear();
    }
}
