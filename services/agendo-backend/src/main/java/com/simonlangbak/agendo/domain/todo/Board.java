package com.simonlangbak.agendo.domain.todo;

import com.simonlangbak.agendo.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Getter
@Setter
public class Board extends BaseEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotBlank
    private String name;

    @Getter
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private SortedSet<BoardColumn> columns = new TreeSet<>();

    public Board() {
    }

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addColumn(BoardColumn boardColumn) {
        if (boardColumn == null) {
            throw new IllegalArgumentException("BoardColumn cannot be null");
        }
        if (boardColumn.getBoard() != null && !boardColumn.getBoard().equals(this)) {
            throw new IllegalArgumentException("Column is already assigned to another board");
        }

        columns.add(boardColumn);
        boardColumn.setBoard(this);
    }

    public SortedSet<BoardColumn> getColumns() {
        return Collections.unmodifiableSortedSet(columns);
    }

    private void setColumns(SortedSet<BoardColumn> columns) {
        this.columns = columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board)) return false;
        return Objects.equals(id, board.id) && Objects.equals(name, board.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", columns=" + columns +
                '}';
    }
}
