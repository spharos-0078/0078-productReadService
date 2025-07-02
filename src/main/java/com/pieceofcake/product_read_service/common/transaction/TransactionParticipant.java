package com.pieceofcake.product_read_service.common.transaction;

/**
 * 분산 트랜잭션 참여자 인터페이스
 */
public interface TransactionParticipant {
    
    /**
     * 참여자 이름
     */
    String getName();
    
    /**
     * Prepare 단계 - 트랜잭션 준비
     * @return 준비 성공 여부
     */
    boolean prepare();
    
    /**
     * Commit 단계 - 트랜잭션 커밋
     */
    void commit();
    
    /**
     * Rollback 단계 - 트랜잭션 롤백
     */
    void rollback();
} 