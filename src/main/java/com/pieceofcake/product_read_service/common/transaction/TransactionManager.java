package com.pieceofcake.product_read_service.common.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionManager {

    /**
     * Saga 패턴 기반 트랜잭션 실행
     * @param action 실행할 액션
     * @param compensation 보상 액션
     * @param <T> 반환 타입
     * @return 실행 결과
     */
    public <T> T executeWithCompensation(Supplier<T> action, Runnable compensation) {
        try {
            T result = action.get();
            log.info("Transaction executed successfully");
            return result;
        } catch (Exception e) {
            log.error("Transaction failed, executing compensation", e);
            try {
                compensation.run();
                log.info("Compensation executed successfully");
            } catch (Exception compEx) {
                log.error("Compensation failed", compEx);
                // 보상 실패 시 알림 발송
                sendCompensationFailureAlert(e, compEx);
            }
            throw e;
        }
    }

    /**
     * 멱등성을 보장하는 트랜잭션 실행
     * @param idempotencyKey 멱등성 키
     * @param action 실행할 액션
     * @param <T> 반환 타입
     * @return 실행 결과
     */
    @Transactional
    public <T> T executeWithIdempotency(String idempotencyKey, Supplier<T> action) {
        // 이미 처리된 요청인지 확인
        if (isAlreadyProcessed(idempotencyKey)) {
            log.info("Request already processed: {}", idempotencyKey);
            return getCachedResult(idempotencyKey);
        }

        try {
            T result = action.get();
            // 성공 시 결과 캐싱
            cacheResult(idempotencyKey, result);
            log.info("Idempotent transaction executed successfully: {}", idempotencyKey);
            return result;
        } catch (Exception e) {
            log.error("Idempotent transaction failed: {}", idempotencyKey, e);
            throw e;
        }
    }

    /**
     * 분산 트랜잭션 실행 (2PC 패턴)
     * @param participants 참여자들
     * @return 실행 결과
     */
    public boolean executeDistributedTransaction(TransactionParticipant... participants) {
        // Phase 1: Prepare
        try {
            for (TransactionParticipant participant : participants) {
                if (!participant.prepare()) {
                    log.error("Prepare phase failed for participant: {}", participant.getName());
                    return false;
                }
            }
            log.info("Prepare phase completed successfully");

            // Phase 2: Commit
            for (TransactionParticipant participant : participants) {
                participant.commit();
            }
            log.info("Distributed transaction committed successfully");
            return true;

        } catch (Exception e) {
            log.error("Distributed transaction failed, rolling back", e);
            // Rollback
            for (TransactionParticipant participant : participants) {
                try {
                    participant.rollback();
                } catch (Exception rollbackEx) {
                    log.error("Rollback failed for participant: {}", participant.getName(), rollbackEx);
                }
            }
            return false;
        }
    }

    private boolean isAlreadyProcessed(String idempotencyKey) {
        // Redis를 사용한 멱등성 체크
        // TODO: Redis 구현
        return false;
    }

    private <T> T getCachedResult(String idempotencyKey) {
        // Redis에서 캐시된 결과 조회
        // TODO: Redis 구현
        return null;
    }

    private <T> void cacheResult(String idempotencyKey, T result) {
        // Redis에 결과 캐싱
        // TODO: Redis 구현
    }

    private void sendCompensationFailureAlert(Exception originalEx, Exception compensationEx) {
        // Slack, Email 등으로 알림 발송
        log.error("COMPENSATION FAILURE ALERT - Original: {}, Compensation: {}", 
                originalEx.getMessage(), compensationEx.getMessage());
    }
} 