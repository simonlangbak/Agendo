package com.simonlangbak.agendo.web.dto;

import com.simonlangbak.agendo.domain.todo.BoardColumn;

public record BoardColumnDTO(Long id, Long boardId, String name, String description) {

    public static BoardColumnDTO of(BoardColumn entity) {
        return new BoardColumnDTO(entity.getId(), entity.getBoard().getId(), entity.getName(), entity.getDescription());
    }
}
