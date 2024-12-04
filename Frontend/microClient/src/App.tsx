import React from 'react';
import"./App.css"
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme';
import LoginPage from './pages/Login';
import CharacterMenu from './pages/Charactermenu';
import CharacterForm from './pages/CreateCharacter';
import CharacterSheet from './pages/CharacterSheet';
import Campaign from './pages/Campaign.tsx';
import CampaignForm from './pages/CreateCampaign'

const App: React.FC = () => {
    return (
        <ThemeProvider theme={theme}>
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/character-menu" element={<CharacterMenu />} />
                <Route path="/new-character" element={<CharacterForm />} />
                <Route path="/character/:characterId" element={<CharacterSheet />} />
                <Route path="/campaign" element={<Campaign/>}/>
                <Route path="/new-campaign" element={<CampaignForm/>} />
            </Routes>
        </Router>
        </ThemeProvider>
    );
};

export default App;
