package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.todo.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
