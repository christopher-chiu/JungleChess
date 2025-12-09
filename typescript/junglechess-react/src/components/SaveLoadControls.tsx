import React, { useRef } from 'react';
import { useGameContext } from '../game/GameContext';
import { exportGameState, importGameState } from '../utils/json';

const SaveLoadControls: React.FC = () => {
  const { state, setState } = useGameContext();
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleSave = () => {
    const dataStr = 'data:application/json;charset=utf-8,' + encodeURIComponent(exportGameState(state));
    const downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute('href', dataStr);
    downloadAnchorNode.setAttribute('download', 'junglechess-game.json');
    document.body.appendChild(downloadAnchorNode);
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
  };

  const handleLoad = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (event) => {
      const json = event.target?.result as string;
      const loadedState = importGameState(json);
      if (loadedState) setState(loadedState);
      else alert('Invalid game state file.');
    };
    reader.readAsText(file);
  };

  return (
    <div>
      <button onClick={handleSave}>Save Game</button>
      <input type="file" accept="application/json" ref={fileInputRef} onChange={handleLoad} />
    </div>
  );
};

export default SaveLoadControls;
