package com.sparta.newspeed.repository;

import com.sparta.newspeed.dto.PeedResponseDto;
import com.sparta.newspeed.entity.Peed;
import com.sparta.newspeed.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeedRepository extends JpaRepository<Peed, Long> {

    Page<PeedResponseDto> findByUser(User user, Pageable pageable);
}
