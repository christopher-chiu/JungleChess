import { GameState } from '../game/types';
import initialGameStateRaw from '../assets/initialGameState.json';
import { Color } from '../game/types';

export const defaultGameState: GameState = {
  ...initialGameStateRaw,
  turn: initialGameStateRaw.turn === 'red' ? Color.RED : Color.BLACK,
};

export async function loadInitialGameState(): Promise<GameState> {
  const response = await fetch(process.env.PUBLIC_URL + '/assets/initialGameState.json');
  const data = await response.json();
  return data as GameState;
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
