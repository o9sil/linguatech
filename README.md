이 프로젝트는 Docker Compose를 사용하여 로컬 환경에서 즉시 실행 가능하도록 구성하였습니다.

[데이터베이스 설계]
1. service_plan: 서비스 요금제
2. company: 기업
3. feature: 기능
4. service_plan_feature: 서비스 요금제와 기능 연결
5. feature_usage: 기능 사용 이력

[프로젝트 실행]
1. Docker 설지: https://docs.docker.com/compose/install/
2. Docker 실행: docker-compose up --build

[API 테스트]
API는 Swagger UI를 통해 확인 가능합니다. 경로는 아래에서 확인 가능합니다.
http://localhost:8080/swagger-ui/index.html

1. 서비스 요금제 생성
URL: POST http://localhost:8080/swagger-ui/index.html#/service-plan-controller/createServicePlan
Body 예시
{
  "name": "샘플 요금제",
  "description": "AI 번역, AI 뉘앙스 조절을 포함한 요금제입니다.",
  "featureIds": [
    1, 3
  ]
}

설명: featureIds로 2개의 기능(AI 번역, AI 뉘앙스 조절)을 조합하여 서비스 요금제를 생성합니다.

2. 서비스 요금제 확인
URL: GET http://localhost:8080/swagger-ui/index.html#/service-plan-controller/getServicePlan
Parameter 예시
    id: 1

설명: 위에서 생성된 서비스 요금제를 확인할 수 있습니다.

3. 기업에 서비스 요금제 연결
URL: PUT http://localhost:8080/swagger-ui/index.html#/company-controller/updateServicePlanForCompany
Body 예시
{
  "companyId": 1,
  "servicePlanId": 1
}

설명: 생성된 서비스 요금제를 특정 기업에 연결합니다. 기업은 초기 데이터를 활용하였습니다.

4. 기업-서비스 요금제 연결 확인
URL: GET http://localhost:8080/swagger-ui/index.html#/company-controller/getServicePlanForCompany
Parameter 예시
    companyId: 1

설명: 기업과 서비스 요금제의 연결 상태를 확인할 수 있습니다.

5. 기능 사용
URL: POST http://localhost:8080/swagger-ui/index.html#/feature-usage-controller/saveFeatureUsage
Body 예시
{
  "companyId": 1,
  "featureId": 1,
  "inputText": "안녕하세요."
}

설명: 기업이 서비스 사용을 위한 API입니다. 검증 및 크레딧 차감 처리를 수행합니다. 예시로는 "안녕하세요" 텍스트를 입력받아 AI번역을 시도하는 과정입니다.

6. 기능별 사용 내역 및 크레딧 사용량 조회
URL: GET http://localhost:8080/swagger-ui/index.html#/feature-usage-controller/getFeatureUsageSummary
Parameter 예시
    featureId: 1
    startDate: 2025-04-01
    endDate: 2025-12-31

설명: 해당 기간 동안 기능별 사용 내역 및 크레딧 사용량을 조회할 수 있습니다.