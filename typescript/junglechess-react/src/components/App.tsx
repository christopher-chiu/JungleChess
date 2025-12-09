import React from 'react';
import { GameProvider, useGameContext } from '../game/GameContext';
import Board from './Board';
import Instructions from './Instructions';
import { loadInitialGameState } from '../utils/json';

const InnerApp: React.FC = () => {
  const { setState } = useGameContext();
  const handleRestart = async () => {
    const initialState = await loadInitialGameState();
    setState(initialState);
  };
  return (
    <div>
      <div>
        <h1>Dou Shou Qi (Jungle Chess)</h1>
        <button onClick={handleRestart}>Restart Game</button>
      </div>
      <div>
        <div>
          <Board />
        </div>
      </div>
      <Instructions />
    </div>
  );
};

const App: React.FC = () => {
  return (
    <GameProvider>
      <InnerApp />
    </GameProvider>
  );
};

export default App;
