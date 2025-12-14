import { GameState } from '../game/types';
import initialGameStateRaw from '../assets/initialGameState.json';
import { Color } from '../game/types';

const initialAsGameState = initialGameStateRaw as unknown as GameState;

export const defaultGameState: GameState = {
  ...initialAsGameState,
  turn: initialAsGameState.turn === 'red' ? Color.RED : Color.BLACK,
};

export async function loadInitialGameState(): Promise<GameState> {
  return defaultGameState;
}

export function exportGameState(state: GameState): string {
  return JSON.stringify(state, null, 2);
}

export function importGameState(json: string): GameState | null {
  try {
    const parsed = JSON.parse(json);
    // Add validation logic here if needed
    return parsed as GameState;
  } catch (e) {
    return null;
  }
}
