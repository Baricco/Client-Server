import React from 'react';
import ReactDOM from 'react-dom';
import KUTE from 'kute.js'
import './styles/index.css';
import SectionIntroduction from './SectionIntroduction';
import SectionDescription from './SectionDescription';
import SectionPrivacy from './SectionPrivacy';
import SectionEncryption from './SectionEncryption';
import SectionAboutus from './SectionAboutus';
import Footer from './Footer';
import { TriangleSection } from './TriangleSection';
import Header from './Header';
import NavBar from './NavBar';
import { timeout } from 'q';

/*
ReactDOM.render(
    <React.StrictMode >
        <Header />
        <NavBar/>
        {TriangleSection("ciao", 0)}
        {TriangleSection("ciao2", 2)} 
        <Footer />
    </React.StrictMode>,
    document.getElementById('root')
);
*/

var sections = [];

function loadSections(){
    sections.push(NavBar());
    sections.push((new SectionIntroduction("#001220", "800px")).render());
    sections.push((new SectionDescription("#c72433", "600px")).render());
    sections.push((new SectionPrivacy("#001220", "600px")).render());

}

function loadAnimations(){
    let parts = ["Description", "Privacy"];
    for(let p=0;p<parts.length;p++){
        for(let i=0;i<5;i++){
            KUTE.fromTo(
                "#wave"+parts[p]+i+"_0",
                {path:"#wave"+parts[p]+i+"_0"},
                {path:"#wave"+parts[p]+i+"_1"},
                {repeat: 10000, duration: 15000, yoyo:true}
            ).start();
        }    
    }
}

function renderSections(){

    ReactDOM.render(
        <React.StrictMode>
            {sections}
        </React.StrictMode>,
        document.getElementById("root")
    );
}


loadSections();
renderSections();
loadAnimations();
