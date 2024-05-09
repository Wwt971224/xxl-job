package com.xxl.job.admin.service;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wtai
 * @version 2024/5/9
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecordServiceTest {

    private static final Logger log = LoggerFactory.getLogger(RecordServiceTest.class);
    @Resource
    private RecordService recordService;

    @Test
    void recordTotalCount() {
        recordService.recordTotalCount("batchId", 1, 10);
    }

    @Test
    void recordSuccessCount() {
        for (int i = 0; i < 10; i++) {
            long successCount = recordService.recordSuccessCount("batchId", 1);
            log.info("成功的数量:{}", successCount);
        }

    }

    @Test
    void getTotalCount() {
        long totalCount = recordService.getTotalCount("batchId", 1);
        log.info("总共的数量:{}", totalCount);
    }
}