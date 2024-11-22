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

export interface BoardColumnDTO {
    id: number;
    boardId: number;
    name: string;
    description: string;
}