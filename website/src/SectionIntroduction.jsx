import SectionTemplate from "./SectionTemplate";
import './styles/SectionIntroduction.css'
import { renderComponent } from "./index";
import { termsEConditions } from "./LegalDocuments";

export default class SectionIntroduction extends SectionTemplate{
    constructor(bkg, height){
        super(bkg, height);

        this.content = (
            <div id = "sectionIntroduction">
                <div style={{backgroundColor:this.bkg, height:this.height}}>
                    <img id="logo" src="img/logo.png"/>
                    <div>     
                        <div className="introductionContent">
                            <p className="family_text font_xxl kerning_l" style={{marginBottom:"10px"}}>WELCOME</p>
                            <p className="family_text font_l kerning_l" style={{marginBottom:"30px"}}>THIS IS <span className="font_xl highlight">HRM</span>essagging<span className="font_xl highlight">S</span>ervice</p>
                            <p className="family_text font_s kerning_m" style={{marginBottom:"70px"}}>
                                Your safe place to chat with friends or strangers<br/>
                                Let's see what <span className="highlight font_m">HRMS</span> can offer <span className="highlight font_m">YOU</span>:
                                <ul id="introductionList">
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Anonimity</li>
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Safety</li>
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Privacy</li>
                                </ul>
                            </p>
                            <div className="introductionDownloadArea">


                                    <div className="loader">
                                        <a onClick={() => {this.appearForm();}} className="downloadButton">
                                            <div className="loader-bg">
                                                <span className="family_text kerning_s">DOWNLOAD</span>
                                                <div className="drops">
                                                    <div className="drop1"></div>
                                                    <div className="drop2"></div>

                                                    <div className="drop3"></div>
                                                    <div className="drop4"></div>

                                                    <div className="drop5"></div>
                                                    <div className="drop6"></div>

                                                    <div className="drop7"></div>
                                                    <div className="drop8"></div>

                                                    <div className="drop9"></div>
                                                    <div className="drop10"></div>

                                                    <div className="drop11"></div>
                                                    <div className="drop12"></div>

                                                    <div className="drop13"></div>
                                                    <div className="drop14"></div>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                    <svg xmlns="http://www.w3.org/2000/svg" version="1.1">
                                        <defs>
                                            <filter id="liquid">
                                            <feGaussianBlur in="SourceGraphic" stdDeviation="10" result="blur" />
                                            <feColorMatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 18 -7" result="liquid" />
                                            </filter>
                                        </defs>
                                    </svg>

                            </div>
                        </div>
                        <div className="introductionImage">
                            <img className="introductionPreview" src="img/program_preview.png"/>
                        </div>
                        <div className="floatBreaker"></div>
                    </div>
                </div>

            </div>

        );

    }

    appearForm() {
        renderComponent(this.createForm(), "popup");
        let sections = document.querySelectorAll(".section");
        let navbarPaths = document.querySelectorAll(".svgClickable");
        let navbar = document.getElementById("navbar");
        let body = document.getElementById("body");

        body.style.overflow="hidden";
        navbar.style.filter="blur(6px)"; 
        for(let i=0; i<navbarPaths.length; i++){
            navbarPaths[i].style.pointerEvents="none";
        }
        for(let i=0; i<sections.length; i++){
            sections[i].style.filter = "blur(6px)";
        }        
        
    }

    disappearForm() {
        renderComponent(null, "popup");
        let sections = document.querySelectorAll(".section");
        let navbarPaths = document.querySelectorAll(".svgClickable");
        let navbar = document.getElementById("navbar");
        let body = document.getElementById("body");

        body.style.overflow="scroll";
        navbar.style.filter="none"; 
        for(let i=0; i<navbarPaths.length; i++){
            navbarPaths[i].style.pointerEvents="fill";
        }
        for(let i=0; i<sections.length; i++){
            sections[i].style.filter = "none";
        }        
        
    }

    createForm() {
        return (
            <div id = "policyForm">
                <h1 className = "family_title font_l kerning_s policyFormTitle">ACCEPT THE FOLLOWING CONDITIONS TO DOWNLOAD OUR PROGRAM</h1>
                <button onClick={()=>{this.disappearForm();}} className = "font_l family_title" id = "closePolicyFormButton">&#10005;</button>
                <div>
                    <p className = "family_text font_m kerning_s policyFormText">
                        {termsEConditions}
                    </p>
                </div>

                <div className="buttonsDiv">
                    <div id="container">
                        <ul className="ks-cboxtags">
                            <li>
                                <input type="checkbox" id="checkboxOne" value="False" />
                                <label className = "font_xs kerning_s family_text"htmlFor="checkboxOne">I Agree</label>
                            </li>
                        </ul>
                    </div>
                    <button onClick={()=>{this.downloadExecutable();}} id="policyFormBtn" className="font_s">CONTINUE</button>
                </div>
            </div>
        );
    }

    downloadExecutable() {
        if (document.getElementById('checkboxOne').checked) {
            let link = document.createElement("a");
            link.href = "executables/hrms.exe";
            link.download = "hrms.exe";
            link.click();
            this.disappearForm();
        }
        else {
            let checkBox = document.getElementById("container");
            checkBox.className = "AnimatedUl";
            setTimeout(() => {  checkBox.className = "" }, 850); 
        }
        
    }
        
}