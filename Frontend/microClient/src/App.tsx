import React from 'react';
import"./App.css"
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme';
import LoginPage from './pages/Login';
import CharacterMenu from './pages/CharacterMenu.tsx';
import CampaignMenu from "./pages/CampaignMenu.tsx";
import CharacterSheet from './pages/CharacterSheet';
import CampaignSheet from "./pages/CampaignSheet.tsx";
import CampaignForm from './pages/CreateCampaign'
import CharacterForm from './pages/CreateCharacter';

const App: React.FC = () => {
    return (
        <ThemeProvider theme={theme}>
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/character-menu" element={<CharacterMenu />} />
                <Route path="/campaign-menu" element={<CampaignMenu/>} />
                <Route path="/new-character" element={<CharacterForm />} />
                <Route path="/character/:characterId" element={<CharacterSheet />} />
                <Route path="/campaign/:campaignId" element={<CampaignSheet/>} />
                <Route path="/new-campaign" element={<CampaignForm/>} />
            </Routes>
        </Router>
        </ThemeProvider>
    );
};

export default App;
