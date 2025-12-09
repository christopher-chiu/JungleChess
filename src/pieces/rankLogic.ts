
import { PieceType, TileType } from '../game/types';

const rank: Record<PieceType, number> = {
  [PieceType.Rat]: 1,
  [PieceType.Cat]: 2,
  [PieceType.Dog]: 3,
  [PieceType.Wolf]: 4,
  [PieceType.Leopard]: 5,
  [PieceType.Tiger]: 6,
  [PieceType.Lion]: 7,
  [PieceType.Elephant]: 8,
};

export function canCapture(attacker: PieceType, defender: PieceType, defenderTile?: TileType): boolean {
  // If defender is on a trap tile, it can be captured by any attacker
  if (defenderTile === TileType.TRAP) return true;

  // Special rule: Rat can capture Elephant, Elephant cannot capture Rat
  if (attacker === PieceType.Rat && defender === PieceType.Elephant) return true;
  if (attacker === PieceType.Elephant && defender === PieceType.Rat) return false;
  // Otherwise, compare ranks explicitly (higher or equal rank wins)
  return rank[attacker] >= rank[defender];
}
