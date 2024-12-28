package com.simonlangbak.agendo.web.dto;

import com.simonlangbak.agendo.domain.todo.Board;

import java.util.List;

public record BoardDTO(Long id, String name, String description, List<BoardColumnDTO> columns) {

    public static BoardDTO of(Board entity) {
        List<BoardColumnDTO> columns = entity.getColumns().stream().map(BoardColumnDTO::of).toList();
        return new BoardDTO(entity.getId(), entity.getName(), entity.getDescription(), columns);
    }
}
