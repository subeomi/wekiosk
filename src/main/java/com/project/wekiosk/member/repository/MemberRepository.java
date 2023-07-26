package com.project.wekiosk.member.repository;

import com.project.wekiosk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
