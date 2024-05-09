package com.xxl.job.admin.service;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author wtai
 * @version 2024/5/9
 */
@Service
public class RecordService {

    private static final String TOTAL_COUNT_KEY = "xxl-job:total-count:%s:%s";

    private static final String SUCCESS_COUNT_KEY = "xxl-job:success-count:%s:%s";

    @Resource
    private Redisson redisson;

    public void recordTotalCount(String batchId, int jobId, int taskCount) {
        RAtomicLong atomicLong = redisson.getAtomicLong(String.format(TOTAL_COUNT_KEY, batchId, jobId));
        atomicLong.set(taskCount);
        atomicLong.expire(Duration.ofDays(1));
    }

    public long recordSuccessCount(String batchId, int jobId) {
        RAtomicLong atomicLong = redisson.getAtomicLong(String.format(SUCCESS_COUNT_KEY, batchId, jobId));
        long successCount = atomicLong.incrementAndGet();
        atomicLong.expire(Duration.ofDays(1));
        return successCount;
    }

    public long getTotalCount(String batchId, int jobId) {
        return redisson.getAtomicLong(String.format(TOTAL_COUNT_KEY, batchId, jobId)).get();
    }

    public void clear(String batchId, int jobId) {
        redisson.getAtomicLong(String.format(TOTAL_COUNT_KEY, batchId, jobId)).deleteAsync();
        redisson.getAtomicLong(String.format(SUCCESS_COUNT_KEY, batchId, jobId)).deleteAsync();
    }


}
