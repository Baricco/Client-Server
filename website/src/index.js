import React from 'react';
import ReactDOM from 'react-dom';
import './styles/index.css';
import Footer from './Footer';
import Header from './Header';
import NavBar from './NavBar';
import TriangleSection from './TriangleSection';

ReactDOM.render(
  <React.StrictMode>
    <Header/>
    <NavBar/>
    <TriangleSection/>
    <Footer/>
  </React.StrictMode>,
  document.getElementById('root')
);