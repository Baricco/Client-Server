import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import Footer from './Footer';
import { TriangleSection } from './TriangleSection'
import Header from './Header';
import NavBar from './NavBar';

ReactDOM.render(
    <React.StrictMode>
    <Header/>
    <NavBar/> { TriangleSection("ciao") } 
    <Footer/>
    </React.StrictMode>,
    document.getElementById('root')
);