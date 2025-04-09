package com.linguatech.service

import com.linguatech.model.Company
import com.linguatech.model.CompanyRepository
import com.linguatech.model.ServicePlanRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val servicePlanRepository: ServicePlanRepository
) {

    // 기업에 서비스 요금제 등록
    fun updateServicePlanForCompany(companyId: Long, servicePlanId: Long): Company {
        // 기업 존재 여부 체크
        val company = companyRepository.findById(companyId).orElseThrow {
            RuntimeException("$companyId 기업이 존재하지 않습니다.")
        }

        // 서비스 요금제 존재 여부 체크
        val servicePlan = servicePlanRepository.findById(servicePlanId).orElseThrow {
            RuntimeException("$servicePlanId 서비스 요금제가 존재하지 않습니다.")
        }

        // 기업에 서비스 요금제 연결
        company.servicePlan = servicePlan

        // 변경된 기업 정보 저장
        return companyRepository.save(company)
    }

    fun getCompanyWithServicePlan(companyId: Long): Company? {
        // 기업의 서비스 요금제 조회
        return companyRepository.findById(companyId).orElse(null)
    }
}