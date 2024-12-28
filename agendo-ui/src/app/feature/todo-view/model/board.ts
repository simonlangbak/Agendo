/* Board related DTOs*/

export interface BoardCreationDTO {
    name: string;
    description: string;
}

export interface BoardDTO {
    id: number;
    name: string;
    description: string;
    columns: BoardColumnDTO[];
}

/* Board column related DTOs */

export interface BoardColumnDTO {
    id: number;
    boardId: number;
    name: string;
    description: string;
}

/* Task related DTOs */

export interface TaskCreationDTO {
    name: string
}

export interface TaskDTO {
    id: number,
    name: string,
    boardId: number,
    boardColumnId: number
}