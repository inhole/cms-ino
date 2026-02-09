# Docker Compose 환경 설정
## 서비스 구성
### LocalStack
- **포트**: 4566
- **서비스**: S3
- **용도**: AWS 서비스 로컬 테스트 환경

### MySQL
- **포트**: 3306
- **데이터베이스**: mydb
- **사용자**: user
- **비밀번호**: password
- **Root 비밀번호**: root

## 사용 방법

### 명령어
```bash
시작
    docker-compose up -d
중지
    docker-compose down
데이터 완전 삭제
    docker-compose down -v
로그 확인
    docker-compose logs -f