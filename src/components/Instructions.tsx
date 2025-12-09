import React from 'react';

const Instructions: React.FC = () => {
  return (
    <aside>
      <h2>Objective</h2>
      <p>Be the first to move any of your animals into the opponent's Den (base).</p>

      <h3>Movement & Capturing (General)</h3>
      <ul>
        <li><strong>Move:</strong> One square orthogonally (no diagonals) to an empty square or one with an enemy piece.</li>
        <li><strong>Capture:</strong> Move onto an enemy piece of equal or lower rank; captured pieces are removed.</li>
        <li><strong>Traps:</strong> An enemy piece on your Trap becomes weak (rank 0) and can be captured by any of your pieces.</li>
        <li><strong>Own Den:</strong> You cannot move your own piece into your own Den.</li>
      </ul>

      <h3>Special Animal Rules</h3>
      <ul>
        <li><strong>Mouse (Rank 1):</strong> Only animal that can enter water squares; cannot capture from water to land (or vice versa); can capture another Mouse in water.</li>
        <li><strong>Elephant (Rank 8):</strong> Cannot capture the Mouse, but the Mouse can capture the Elephant (even on land, but not from water).</li>
        <li><strong>Lion & Tiger:</strong> Can jump horizontally or vertically over water, capturing any lower/equal-ranked piece on the landing square. Jumps are blocked if a Mouse is in the water path.</li>
      </ul>
    </aside>
  );
};

export default Instructions;
