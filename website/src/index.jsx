import React from 'react';
import ReactDOM from 'react-dom';
import KUTE from 'kute.js'
import './styles/index.css';
import SectionIntroduction from './SectionIntroduction';
import sectionDescription from './SectionDescription';
import SectionPrivacy from './SectionPrivacy';
import SectionEncryption from './SectionEncryption';
import SectionAboutus from './SectionAboutus';
import NavBar from './NavBar';
import CountDown from './CountDown';

var sections = [];

function renderComponent(component){
    ReactDOM.render(
        <React.StrictMode>
            {component}
        </React.StrictMode>,
        document.getElementById("root")
    );
}

function loadSections(){
    sections.push(NavBar());
    sections.push((new SectionIntroduction("#001220", "auto")).render());
    sections.push((new sectionDescription("#c72433", "auto")).render());
    sections.push((new SectionPrivacy("#001220", "auto")).render());
    sections.push((new SectionAboutus("#c72433", "1200px")).render());


}

function loadAnimations(){
    let parts = ["Description", "Privacy"];
    for(let p=0;p<parts.length;p++){
        for(let i=0;i<5;i++){
            KUTE.fromTo(
                "#wave"+parts[p]+i+"_0",
                {path:"#wave"+parts[p]+i+"_0"},
                {path:"#wave"+parts[p]+i+"_1"},
                {repeat: Infinity, duration: 15000, yoyo:true}
            ).start();
        }    
    }
}

function renderSections(){
    renderComponent(sections);    
}

function launch(){
    loadSections();
    renderSections();
    loadAnimations();
}

var countDown = new CountDown();
if(!countDown.isExpired()){
    launch();
}
else{
    renderComponent(countDown.render());
    KUTE.fromTo(
        "#morph0",
        {path:"#morph0"},
        {path:"#morph1"},
        {repeat: Infinity, duration: 10000, yoyo:true}
    ).start();
    countDown.startCountDown();
}
