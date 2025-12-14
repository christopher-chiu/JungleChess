import { TileType } from '../game/types';
import React, { useState } from 'react';
import styles from './Board.module.css';
import { useGameContext } from '../game/GameContext';
import { Field, Piece, Color } from '../game/types';
import { getAvailableMoves } from '../pieces/animalLogic';
import { canCapture } from '../pieces/rankLogic';

const Board: React.FC = () => {
  const { state, setState } = useGameContext();
  const [selected, setSelected] = useState<{ x: number; y: number } | null>(null);

  // Debug logs for board and pieces
  console.log('Board state:', state.board);
  console.log('Pieces state:', state.pieces);
  console.log('Turn:', state.turn);
  console.log('Winner:', state.winner);

  

  const handleCellClick = (x: number, y: number) => {
    const field = state.board[y][x];
    if (selected) {
      const fromField = state.board[selected.y][selected.x];
      const pieceId = fromField.piece;
      if (!pieceId) {
        setSelected(null);
        return;
      }
      const piece = state.pieces.find(p => p.id === pieceId);
      if (!piece || piece.color !== state.turn) {
        setSelected(null);
        return;
      }
      // Use availableMoves for move validation
      let availableMoves: { x: number; y: number }[] = getAvailableMoves(piece, selected, state);
      const isAvailable = availableMoves.some(m => m.x === x && m.y === y);
        if (isAvailable) {
        // Check capture legality
        let canMove = true;
        if (field.piece) {
          const defender = state.pieces.find(p => p.id === field.piece);
          if (defender && !canCapture(piece.type, defender.type, field.type)) {
            canMove = false;
          }
        }
        if (canMove) {
          // Update board and piece position
          const newBoard = state.board.map(row => row.map(cell => ({ ...cell })));
          newBoard[selected.y][selected.x].piece = null;
          newBoard[y][x].piece = pieceId;
          const newPieces = state.pieces.map(p =>
            p.id === pieceId ? { ...p, position: { x, y } } :
            (field.piece && p.id === field.piece ? { ...p, captured: true } : p)
          );
          // Win detection: only if moved into opponent's DEN
          let winner: Color | null = null;
          if (field.type === TileType.DEN) {
            // Red wins only by entering (3,8), Black wins only by entering (3,0)
            if (state.turn === Color.RED && x === 3 && y === 8) {
              winner = Color.RED;
            }
            if (state.turn === Color.BLACK && x === 3 && y === 0) {
              winner = Color.BLACK;
            }
          }
          setState({
            ...state,
            board: newBoard,
            pieces: newPieces,
            turn: winner ? state.turn : (state.turn === Color.RED ? Color.BLACK : Color.RED),
            winner,
          });
          setSelected(null);
        } else {
          setSelected(null);
        }
      } else {
        setSelected(null);
      }
    } else if (field.piece) {
      const piece = state.pieces.find(p => p.id === field.piece);
      if (piece && piece.color === state.turn && !piece.captured) {
        setSelected({ x, y });
      }
    }
  };

  // Compute available moves for selected piece using encapsulated animal logic
  let availableMoves: { x: number; y: number }[] = [];
  if (selected) {
    const fromField = state.board[selected.y][selected.x];
    const pieceId = fromField.piece;
    const piece = pieceId ? state.pieces.find(p => p.id === pieceId) : undefined;
    if (piece && !piece.captured) {
      availableMoves = getAvailableMoves(piece, selected, state);
    }
  }

  return (
    <>
      <div className={styles.boardContainer}>
        {state.winner ? (
          <div className="winner-banner">
            {state.winner ? String(state.winner).toUpperCase() : ''} wins!
          </div>
        ) : (
          <div
            className={styles.turnIndicator}
            style={{ color: state.turn === Color.RED ? '#c00' : '#222' }}
          >
            {String(state.turn).toUpperCase()}'s Turn
          </div>
        )}
        <div
          className={styles.board}
          style={{
              background: state.turn === Color.RED
                ? 'linear-gradient(135deg, #ffe5e5 0%, #ffcccc 100%)'
                : 'linear-gradient(135deg, #e5e5e5 0%, #d1d1d1 100%)',
            transition: 'background 0.3s',
            border: `6px solid ${state.turn === Color.RED ? '#c00' : '#222'}`,
            borderRadius: '16px',
            boxShadow: '0 4px 24px rgba(0,0,0,0.12)',
            width: 'max-content',
            margin: '0 auto',
          }}
        >
          {state.board.flat().map((field: Field, idx) => {
            const { x, y } = field;
            const isAvailable = availableMoves.some(m => m.x === x && m.y === y);
            // Log each field and its piece
            console.log(`Rendering cell (${x},${y}):`, field);
            let bgFile = `${String(field.type)}.png`;
            if (field.type === TileType.DEN) {
              // Red den is at (3,0), black den is at (3,8)
              if (x === 3 && y === 0) bgFile = 'den-red.png';
              if (x === 3 && y === 8) bgFile = 'den-black.png';
            }

            return (
              <div
                key={idx}
                className={[
                  styles.cell,
                  styles[String(field.type)],
                  selected && selected.x === x && selected.y === y ? styles.selected : '',
                  isAvailable ? styles.availableMove : '',
                ].filter(Boolean).join(' ')}
                onClick={() => handleCellClick(x, y)}
                style={{
                  cursor: isAvailable ? 'pointer' : 'default',
                  backgroundImage: `url(${process.env.PUBLIC_URL}/assets/${bgFile})`,
                  opacity: isAvailable ? 0.85 : 1,
                  boxShadow: isAvailable ? '0 0 8px #00aaff88' : undefined,
                }}
              >
                {field.piece ? (() => {
                  const piece: Piece | undefined = state.pieces.find(p => p.id === field.piece);
                  console.log('Rendering piece:', piece);
                  if (!piece) return null;
                  const imgName = `${piece.type}-${piece.color}.png`;
                  const imgSrc = `${process.env.PUBLIC_URL}/assets/${imgName}`;
                  console.log('Piece image src:', imgSrc);
                  return (
                    <img
                      src={imgSrc}
                      alt={`${piece.type} (${piece.color})`}
                      className={styles.piece}
                    />
                  );
                })() : null}
              </div>
            );
          })}
        </div>
      </div>
    </>
  );
};

export default Board;
