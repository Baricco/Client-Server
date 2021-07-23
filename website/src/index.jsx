import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import Footer from './Footer';
import { TriangleSection } from './TriangleSection'
import Header from './Header';
import NavBar from './NavBar';

<<<<<<< HEAD
ReactDOM.render( <
    React.StrictMode >
    <
    Header / >
    <
    NavBar / > { TriangleSection("ciao", 0) }

    { TriangleSection("ciao2", 2) } <
    Footer / >
    <
    /React.StrictMode>,
=======
ReactDOM.render(
    <React.StrictMode>
    <Header/>
    <NavBar/> { TriangleSection("ciao") } 
    <Footer/>
    </React.StrictMode>,
>>>>>>> a5a0304b7bb24d8587859e3c61c37046a379a5ec
    document.getElementById('root')
);