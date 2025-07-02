# 🍰 0078-ProductReadService

> **Piece of Cake** - 상품 및 공모 Read Service  
> 마이크로서비스 아키텍처 기반의 상품 조회 전용 서비스

## 📋 목차

- [프로젝트 개요](#-프로젝트-개요)
- [아키텍처](#-아키텍처)
- [기술 스택](#-기술-스택)
- [주요 기능](#-주요-기능)
- [설치 및 실행](#-설치-및-실행)
- [API 문서](#-api-문서)
- [개발 가이드](#-개발-가이드)
- [모니터링](#-모니터링)
- [배포](#-배포)

## 🎯 프로젝트 개요

### 서비스 목적
- 상품 정보 조회 전용 서비스 (CQRS 패턴)
- 공모 정보 조회 전용 서비스
- 조각 상품 정보 조회 전용 서비스
- Kafka 이벤트 기반 데이터 동기화
- 고성능 캐싱 및 검색 최적화

### 핵심 특징
- ✅ **Clean Architecture** 적용
- ✅ **CQRS 패턴** (Command Query Responsibility Segregation)
- ✅ **이벤트 기반 비동기 통신**
- ✅ **Redis 캐싱** 레이어
- ✅ **Elasticsearch** 전문 검색
- ✅ **트랜잭션 보상 로직** (Saga 패턴)
- ✅ **멱등성 보장**
- ✅ **데드레터 큐** 처리
- ✅ **Health Check** 엔드포인트
- ✅ **보안 강화** (AWS Secrets Manager)

## 🏗️ 아키텍처

### 전체 아키텍처
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │   Load Balancer │    │   Eureka Server │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │ Product Read    │
                    │ Service         │
                    └─────────────────┘
                                 │
         ┌───────────────────────┼───────────────────────┐
         │                       │                       │
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   MongoDB       │    │   Redis Cache   │    │   Elasticsearch │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │   Kafka         │
                    │   (Event Bus)   │
                    └─────────────────┘
```

### 레이어 구조
```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │ ProductController│  │ KafkaConsumer   │  │ HealthCheck  │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    Application Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │ ProductService  │  │ TransactionMgr  │  │ CacheService │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                  Infrastructure Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │ MongoRepository │  │ RedisTemplate   │  │ ElasticSearch│ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## 🛠️ 기술 스택

### Backend
- **Java 17** - LTS 버전
- **Spring Boot 3.5.0** - 최신 버전
- **Spring Cloud** - 마이크로서비스 지원
- **Spring Data MongoDB** - NoSQL 데이터베이스
- **Spring Data Redis** - 캐싱
- **Spring Data Elasticsearch** - 전문 검색
- **Spring Kafka** - 메시지 큐
- **Spring Boot Actuator** - 모니터링

### Database & Cache
- **MongoDB** - 메인 데이터베이스
- **Redis** - 캐싱 레이어
- **Elasticsearch** - 전문 검색 엔진

### Message Queue
- **Apache Kafka** - 이벤트 스트리밍

### Service Discovery
- **Netflix Eureka** - 서비스 디스커버리

### Documentation
- **Swagger/OpenAPI 3** - API 문서화

### DevOps
- **Docker** - 컨테이너화
- **Gradle** - 빌드 도구
- **AWS Secrets Manager** - 보안 관리

## 🚀 주요 기능

### 1. 상품 관리
- ✅ 상품 목록 조회 (페이징, 필터링)
- ✅ 상품 상세 조회
- ✅ 상품 상태 관리
- ✅ 카테고리별 상품 조회

### 2. 공모 관리
- ✅ 공모 정보 조회
- ✅ 공모 상태 관리
- ✅ 남은 조각 수 업데이트

### 3. 조각 상품 관리
- ✅ 조각 상품 정보 조회
- ✅ 조각 상품 상태 관리 (VOTE, AUCTION, NONE)
- ✅ 배치 처리

### 4. 검색 기능
- ✅ Elasticsearch 전문 검색
- ✅ 다중 필드 검색
- ✅ 유사도 검색
- ✅ 오타 허용 검색

### 5. 캐싱
- ✅ Redis 캐싱
- ✅ 캐시 무효화 전략
- ✅ 성능 최적화

### 6. 트랜잭션 관리
- ✅ Saga 패턴 기반 보상 로직
- ✅ 멱등성 보장
- ✅ 분산 트랜잭션 지원

## 📦 설치 및 실행

### Prerequisites
- Java 17+
- Docker & Docker Compose
- MongoDB
- Redis
- Kafka
- Elasticsearch

### 1. 저장소 클론
```bash
git clone https://github.com/spharos-0078/0078-productReadService.git
cd 0078-productReadService
```

### 2. 환경 변수 설정
```bash
# .env 파일 생성
cp .env.example .env

# 환경 변수 설정
EC2_MONGOID=your_mongodb_username
EC2_MONGOPW=your_mongodb_password
EC2_HOST=your_eureka_host
EC2_HOST2=your_kafka_host
```

### 3. 애플리케이션 실행

#### 로컬 실행
```bash
# Gradle로 실행
./gradlew bootRun

# 또는 JAR 파일로 실행
./gradlew build
java -jar build/libs/product-read-service-0.0.1-SNAPSHOT.jar
```

#### Docker 실행
```bash
# Docker 이미지 빌드
docker build -t product-read-service .

# Docker Compose로 실행
docker-compose up -d
```

### 4. 서비스 확인
```bash
# Health Check
curl http://localhost:8090/actuator/health

# Swagger UI
http://localhost:8090/swagger-ui/index.html
```

## 📚 API 문서

### Swagger UI
- **URL**: http://localhost:8090/swagger-ui/index.html
- **API Docs**: http://localhost:8090/v3/api-docs

### 주요 API 엔드포인트

#### 상품 조회 API
```http
GET /api/v1/product/list
GET /api/v1/product/list/{productUuid}
```

#### 필터링 옵션
- `page`: 페이지 번호 (기본값: 0)
- `size`: 페이지 크기 (기본값: 10, 최대: 100)
- `main`: 메인 카테고리 ID
- `sub`: 서브 카테고리 ID
- `name`: 상품명 검색
- `status`: 상품 상태

#### 예시 요청
```bash
# 상품 목록 조회
curl "http://localhost:8090/api/v1/product/list?page=0&size=10&status=STORED"

# 상품 상세 조회
curl "http://localhost:8090/api/v1/product/list/123e4567-e89b-12d3-a456-426614174000"
```

## 🛠️ 개발 가이드

### 프로젝트 구조
```
src/
├── main/
│   ├── java/
│   │   └── com/pieceofcake/product_read_service/
│   │       ├── common/           # 공통 컴포넌트
│   │       │   ├── config/       # 설정
│   │       │   ├── entity/       # 공통 엔티티
│   │       │   ├── exception/    # 예외 처리
│   │       │   └── transaction/  # 트랜잭션 관리
│   │       ├── product/          # 상품 도메인
│   │       │   ├── application/  # 애플리케이션 서비스
│   │       │   ├── dto/          # 데이터 전송 객체
│   │       │   ├── entity/       # 엔티티
│   │       │   ├── infrastructure/ # 인프라스트럭처
│   │       │   └── presentation/ # 프레젠테이션
│   │       ├── funding/          # 공모 도메인
│   │       ├── piece/            # 조각 상품 도메인
│   │       └── kafka/            # Kafka 이벤트
│   └── resources/
│       ├── application.yml       # 기본 설정
│       ├── application-dev.yml   # 개발 환경 설정
│       └── elasticsearch-settings.json # ES 설정
└── test/                         # 테스트 코드
```

### 코드 컨벤션
- **Java**: Google Java Style Guide
- **Package**: com.pieceofcake.product_read_service
- **Naming**: camelCase (변수, 메서드), PascalCase (클래스)
- **Annotation**: @Override, @NonNull 등 적극 활용

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 테스트
./gradlew test --tests ProductReadServiceImplTest

# 테스트 커버리지
./gradlew jacocoTestReport
```

## 📊 모니터링

### Health Check
```bash
# 전체 상태 확인
curl http://localhost:8090/actuator/health

# 상세 정보
curl http://localhost:8090/actuator/health/details
```

### 메트릭
```bash
# 애플리케이션 메트릭
curl http://localhost:8090/actuator/metrics

# 특정 메트릭
curl http://localhost:8090/actuator/metrics/http.server.requests
```

### 로그
```bash
# 애플리케이션 로그 확인
docker logs product-read-service

# 실시간 로그
docker logs -f product-read-service
```

## 🚀 배포

### Docker 배포
```bash
# 이미지 빌드
docker build -t product-read-service:latest .

# 컨테이너 실행
docker run -d \
  --name product-read-service \
  -p 8090:8090 \
  --env-file .env \
  product-read-service:latest
```

### Docker Compose 배포
```bash
# 전체 스택 실행
docker-compose up -d

# 특정 서비스만 실행
docker-compose up -d product-read-service
```

### AWS 배포
```bash
# ECR에 푸시
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com
docker tag product-read-service:latest $AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/product-read-service:latest
docker push $AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/product-read-service:latest
```

## 🔧 설정

### MongoDB 설정
```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://${EC2_MONGOID}:${EC2_MONGOPW}@spharos.uxnx7ao.mongodb.net/piece_of_cake
      auto-index-creation: true
      max-connection-pool-size: 100
      min-connection-pool-size: 5
```

### Redis 설정
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      timeout: 2000ms
```

### Kafka 설정
```yaml
spring:
  kafka:
    bootstrap-servers: ${EC2_HOST2}:10000,${EC2_HOST2}:10001,${EC2_HOST2}:10002
    consumer:
      group-id: product-read-group
      auto-offset-reset: earliest
```

### Elasticsearch 설정
```yaml
spring:
  data:
    elasticsearch:
      uris: ${ELASTICSEARCH_URI}
      username: ${ELASTICSEARCH_USERNAME}
      password: ${ELASTICSEARCH_PASSWORD}
```

## 📈 성능 최적화

### 캐싱 전략
- **L1 Cache**: Redis (애플리케이션 레벨)
- **L2 Cache**: MongoDB (데이터베이스 레벨)
- **TTL**: 30분 (기본값)

### 인덱스 최적화
- **복합 인덱스**: 카테고리 + 상태 + 생성일
- **텍스트 인덱스**: 상품명, 설명
- **TTL 인덱스**: 자동 삭제

### 쿼리 최적화
- **페이징**: 페이지 단위 조회
- **프로젝션**: 필요한 필드만 조회
- **인덱스 힌트**: 쿼리 최적화

## 🔒 보안

### 인증/인가
- JWT 토큰 기반 인증
- Role 기반 접근 제어
- API Rate Limiting

### 데이터 보안
- AWS Secrets Manager 사용
- 환경 변수 암호화
- SSL/TLS 통신

### 입력 검증
- Bean Validation
- XSS 방지
- SQL Injection 방지

## 🤝 기여 가이드

### 브랜치 전략
- `main`: 프로덕션 브랜치
- `develop`: 개발 브랜치
- `feature/*`: 기능 개발
- `fix/*`: 버그 수정
- `hotfix/*`: 긴급 수정

### Pull Request
1. 브랜치 생성
2. 기능 개발
3. 테스트 작성
4. PR 생성
5. 코드 리뷰
6. 머지

## 📞 문의

### 개발팀
- **팀장**: Jason
- **이메일**: jason@pieceofcake.com
- **슬랙**: #product-read-service

### 이슈 리포트
- GitHub Issues: [이슈 등록](https://github.com/spharos-0078/0078-productReadService/issues)
- 버그 리포트: [버그 리포트](https://github.com/spharos-0078/0078-productReadService/issues/new?template=bug_report.md)

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

---

**Made with ❤️ by Piece of Cake Team**

