import React, { createContext, useContext, useState, ReactNode } from 'react';
import { GameState } from './types';
import { defaultGameState } from '../utils/json';

interface GameContextProps {
  state: GameState;
  setState: React.Dispatch<React.SetStateAction<GameState>>;
}

const GameContext = createContext<GameContextProps | undefined>(undefined);

export const useGameContext = () => {
  const context = useContext(GameContext);
  if (!context) throw new Error('useGameContext must be used within a GameProvider');
  return context;
};

export const GameProvider = ({ children }: { children: ReactNode }) => {
  const [state, setState] = useState<GameState>(defaultGameState);
  return (
    <GameContext.Provider value={{ state, setState }}>
      {children}
    </GameContext.Provider>
  );
};
