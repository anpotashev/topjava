package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TimeTestRule extends Stopwatch {
    private static MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();

    private static void logResult(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        addValues(testName, status, nanos);
        log.info("Test result:");
        printCaption();
        logValues(testName, status, nanos);
    }

    private static void addValues(String testName, String status, long nanos) {
        multiValueMap.add(testName, status);
        multiValueMap.add(testName, nanos);
    }

    private static void printCaption() {
        String result = String.format("%-20s %-20s %12s", "method name", "result", "duration (ms)");
        log.info(result);
    }

    private static void logValues(String testName, String result, long nanos) {
        String line = String.format("   %-20s %-20s %d", testName, result, TimeUnit.NANOSECONDS.toMicros(nanos));
        log.info(line);
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
        multiValueMap.forEach((s, objects) -> logValues(s, (String) (multiValueMap.getFirst(s)), (Long) (multiValueMap.get(s).get(1))));

    }

    public static void clearMap() {
        multiValueMap.clear();
    }
}
