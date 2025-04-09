-- 서비스 요금제
CREATE TABLE service_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 기업
CREATE TABLE company (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    credit INT NOT NULL,
    service_plan_id BIGINT,
    created_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (service_plan_id) REFERENCES service_plan(id)
);

-- 기능
CREATE TABLE feature (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    credit INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 서비스 요금제와 기능 연결
CREATE TABLE service_plan_feature (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_plan_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    FOREIGN KEY (service_plan_id) REFERENCES service_plan(id),
    FOREIGN KEY (feature_id) REFERENCES feature(id),
    CONSTRAINT unique_service_plan_feature UNIQUE (service_plan_id, feature_id)
);

-- 기능 사용 이력
CREATE TABLE feature_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    use_credit INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (feature_id) REFERENCES feature(id)
);

-- 초기 데이터 : 기업
INSERT INTO company (name, credit) VALUES
('Acompany', 5000),
('Bcompany', 10000),
('Ccompany', 10000);

-- 초기 데이터 : 기능 + 제한 방식별 요금
INSERT INTO feature (code, name, type, credit) VALUES
('TRANSLATION', 'AI 번역', 'CHAR_PER_CALL', 2000),
('PROOFREAD', 'AI 교정', 'CHAR_PER_CALL', 1000),
('NUANCE', 'AI 뉘앙스 조절', 'CHAR_PER_CALL', 1500),
('DRAFT', 'AI 초안 작성', 'COUNT_PER_MONTH', 200);