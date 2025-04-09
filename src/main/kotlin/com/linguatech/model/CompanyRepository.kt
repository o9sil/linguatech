package com.linguatech.model

import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository : JpaRepository<Company, Long> {
}