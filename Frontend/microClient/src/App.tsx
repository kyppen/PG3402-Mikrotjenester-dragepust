import React from 'react';
import"./App.css"
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import LoginPage from './pages/Login';
import CharacterMenu from './pages/Charactermenu';
import CharacterForm from './pages/CreateCharacter';
import CharacterSheet from './pages/CharacterSheet';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/character-menu" element={<CharacterMenu />} />
                <Route path="/new-character" element={<CharacterForm />} />
                <Route path="/character/:characterId" element={<CharacterSheet />} />
            </Routes>
        </Router>
    );
};

export default App;
