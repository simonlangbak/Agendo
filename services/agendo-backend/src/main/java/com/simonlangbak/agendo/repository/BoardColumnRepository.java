package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.todo.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
}
