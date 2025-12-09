export enum TileType {
  LAND = 'land',
  WATER = 'water',
  TRAP = 'trap',
  DEN = 'den',
}

export enum PieceType {
  Elephant = 'elephant',
  Lion = 'lion',
  Tiger = 'tiger',
  Leopard = 'leopard',
  Dog = 'dog',
  Wolf = 'wolf',
  Cat = 'cat',
  Rat = 'rat',
}

export enum Color {
  RED = 'red',
  BLACK = 'black',
}

export interface Field {
  x: number;
  y: number;
  type: TileType;
  piece: string | null; // piece id or null
}

export interface Piece {
  id: string;
  type: PieceType;
  color: Color;
  position: { x: number; y: number };
  captured: boolean;
}

export interface GameState {
  board: Field[][];
  pieces: Piece[];
  turn: Color;
  winner: Color | null;
}
