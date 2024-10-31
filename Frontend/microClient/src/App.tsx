import "./App.css"
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Home from './pages/Home.tsx'
import Charactermenu from "./pages/Charactermenu.tsx";
import CreateCharacter from "./pages/CreateCharacter.tsx";

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
            <Route path="/Charactermenu" element={<Charactermenu />} />
            <Route path={"/CharacterCreator"} element={<CreateCharacter/>}/>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
