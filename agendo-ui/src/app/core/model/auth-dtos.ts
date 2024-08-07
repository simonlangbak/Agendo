export interface LoginRequestDTO {
    username: string;
    password: string;
}

export interface JwtTokenDTO {
    accessToken: string;
    refreshToken: string;
    expiresIn: number;
}

export interface RefreshTokenDTO {
    refreshToken: string;
}