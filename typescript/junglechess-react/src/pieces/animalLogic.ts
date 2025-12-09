import { Piece, GameState, Color, PieceType, TileType } from '../game/types';

export function getAvailableMoves(piece: Piece, selected: { x: number; y: number }, state: GameState): { x: number; y: number }[] {
  const moves: { x: number; y: number }[] = [];
  const { x, y } = selected;

  // Helper to check if a cell is valid for movement
  function canMoveTo(nx: number, ny: number) {
    if (nx < 0 || nx >= 7 || ny < 0 || ny >= 9) return false;
    const field = state.board[ny][nx];
    // Can't move to own piece
    if (field.piece && state.pieces.find(p => p.id === field.piece)?.color === piece.color) return false;
    // Can't capture between water and land
    const fromField = state.board[y][x];
    if (field.piece && ((fromField.type === TileType.WATER && field.type !== TileType.WATER) || (fromField.type !== TileType.WATER && field.type === TileType.WATER))) return false;
    // Can't enter own den
    if (field.type === TileType.DEN) {
      // Red den is at (3,0), black den is at (3,8)
      if ((piece.color === Color.RED && nx === 3 && ny === 0) || (piece.color === Color.BLACK && nx === 3 && ny === 8)) {
        return false;
      }
    }
    return true;
  }

  // Rats can swim in water
  if (piece.type === PieceType.Rat) {
    [
      { x: x - 1, y },
      { x: x + 1, y },
      { x, y: y - 1 },
      { x, y: y + 1 },
    ].forEach(({ x: nx, y: ny }) => {
      if (canMoveTo(nx, ny)) moves.push({ x: nx, y: ny });
    });
    return moves;
  }

  // Lion/Tiger jump logic
  if (piece.type === PieceType.Lion || piece.type === PieceType.Tiger) {
    // Normal adjacent moves (not into water)
    [
      { x: x - 1, y },
      { x: x + 1, y },
      { x, y: y - 1 },
      { x, y: y + 1 },
    ].forEach(({ x: nx, y: ny }) => {
      if (canMoveTo(nx, ny)) {
        const field = state.board[ny][nx];
        if (field.type !== TileType.WATER) moves.push({ x: nx, y: ny });
      }
    });
    // Horizontal jump over water
    for (const dir of [-1, 1]) {
      let nx = x + dir;
      let ny = y;
      if (nx >= 0 && nx < 7 && state.board[ny][nx].type === TileType.WATER) {
        // Find far bank
        let farX = nx;
        while (farX >= 0 && farX < 7 && state.board[ny][farX].type === TileType.WATER) farX += dir;
        if (farX >= 0 && farX < 7 && state.board[ny][farX].type === TileType.LAND) {
          // Check water path for rats
          let clear = true;
          for (let i = nx; i !== farX; i += dir) {
            const tile = state.board[ny][i];
            if (tile.piece && state.pieces.find(p => p.id === tile.piece)?.type === PieceType.Rat) {
              clear = false;
              break;
            }
          }
          const dest = state.board[ny][farX];
          if (clear && (!dest.piece || state.pieces.find(p => p.id === dest.piece)?.color !== piece.color)) {
            // Prevent entering own den
              if (!((piece.color === Color.RED && farX === 3 && ny === 0) || (piece.color === Color.BLACK && farX === 3 && ny === 8))) {
              moves.push({ x: farX, y: ny });
            }
          }
        }
      }
    }
    // Vertical jump over water
    for (const dir of [-1, 1]) {
      let nx = x;
      let ny = y + dir;
      if (ny >= 0 && ny < 9 && state.board[ny][nx].type === TileType.WATER) {
        // Find far bank
        let farY = ny;
        while (farY >= 0 && farY < 9 && state.board[farY][nx].type === TileType.WATER) farY += dir;
        if (farY >= 0 && farY < 9 && state.board[farY][nx].type === TileType.LAND) {
          // Check water path for rats
          let clear = true;
          for (let i = ny; i !== farY; i += dir) {
            const tile = state.board[i][nx];
            if (tile.piece && state.pieces.find(p => p.id === tile.piece)?.type === PieceType.Rat) {
              clear = false;
              break;
            }
          }
          const dest = state.board[farY][nx];
          if (clear && (!dest.piece || state.pieces.find(p => p.id === dest.piece)?.color !== piece.color)) {
            // Prevent entering own den
            if (!((piece.color === Color.RED && nx === 3 && farY === 0) || (piece.color === Color.BLACK && nx === 3 && farY === 8))) {
              moves.push({ x: nx, y: farY });
            }
          }
        }
      }
    }
    return moves;
  }

  // Other animals: normal adjacent moves, not into water
  [
    { x: x - 1, y },
    { x: x + 1, y },
    { x, y: y - 1 },
    { x, y: y + 1 },
  ].forEach(({ x: nx, y: ny }) => {
    if (canMoveTo(nx, ny)) {
      const field = state.board[ny][nx];
      if (field.type !== TileType.WATER) moves.push({ x: nx, y: ny });
    }
  });
  return moves;
}
