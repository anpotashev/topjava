package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TimeTestRule extends TestWatcher {
    private static Map<String, Long> durationMap = new HashMap<>();
    private long startTime;

    @Override
    protected void starting(Description description) {
        startTime = System.currentTimeMillis();
    }

    @Override
    protected void finished(Description description) {
        long duration = System.currentTimeMillis() - startTime;
        durationMap.put(description.getMethodName(), duration);
        StringBuilder builder = new StringBuilder("Time result of test:")
                .append(String.format("\n%-20s %12s", "method name", "duration (ms)"))
                .append(String.format("\n%-20s %06d", description.getMethodName(), duration));
        log.info(builder.toString());
    }

    public static void testsResults() {
        StringBuilder builder = new StringBuilder("Time results of tests:")
                .append(String.format("\n%-20s %12s", "method name", "duration (ms)"));
        durationMap.forEach((methodName, duration) -> builder.append(String.format("\n%-20s %06d", methodName, duration)));
        log.info(builder.toString());

    }

    public static Map<String, Long> getDurationMap() {
        return durationMap;
    }

    public static void clearMap() {
        durationMap.clear();
    }
}
