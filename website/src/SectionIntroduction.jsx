import SectionTemplate from "./SectionTemplate";
import './styles/SectionIntroduction.css'

export default class SectionIntroduction extends SectionTemplate{
    constructor(bkg, height){
        super(bkg, height);

        this.policyForm = null;

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
                        <div id="policyForm"></div>
                        <div className="floatBreaker"></div>
                    </div>
                </div>

            </div>

        );

    }

    appearForm() {
        alert("cacca culo");
        if (this.policyForm === null) {
            this.policyForm = document.getElementById("policyForm");
            //this.policyForm.style = "";

            var policyTitle = document.createElement("h1");
            policyTitle.className = "family_title font_l kerning_s policyFormTitle";
            policyTitle.innerText = "ACCEPT THE FOLLOWING CONDITIONS TO DOWNLOAD OUR PROGRAM";

            var policyTextDiv = document.createElement("div");
            policyTextDiv.className = "policyFormTextDiv";

            var policyText = document.createElement("p");
            policyText.className = "family_text font_m kerning_l policyFormText";
            policyText.innerText = "I agree on not doing anything illegal with this platform\nI agree on not sharing this program with anyone";

            var policyCheckBox = document.createElement("checkBox");
            policyCheckBox.setAttribute("type", "checkbox");

            var policyCheckBox = document.createElement("p");
            policyText.className = "family_text font_m kerning_s policyFormCheckBoxText";
            policyCheckBox.innerText = "I Agree";

            var policyDownloadButton = document.createElement("a");
            policyDownloadButton.className = "policyDownloadButton";
            policyDownloadButton.innerText = "Download";
            policyDownloadButton.href = "../executables/hrms.exe"
            
        }
        /*
        if(this.disclaimerBox === null) {
            this.disclaimerBox = document.createElement("div");
            this.disclaimerBox.id = "aboutUsDisclaimerBoxVisible";
            
            var h1InBox = document.createElement("h1");
            h1InBox.className = "family_title font_l kerning_s aboutUsDisclaimerTitle";
            h1InBox.innerText = "HRMS POLICY";

            

            var pInBox = document.createElement("p");
            pInBox.className = "family_text font_m kerning_l aboutUsDisclaimerText";
            pInBox.innerText = "Hasta la Revolucion Messaging Service doesn't want to push anyone to commit any type of illegal or malevolent act, we dissociate ourselves from any type of misuse of this platform and don't want suffer any legal issue";
            
            this.disclaimerBox.appendChild(h1InBox);
            this.disclaimerBox.appendChild(pInBox);
            

            document.getElementById("DivDisclaimerBox").appendChild(this.disclaimerBox);
        }
        else {
            this.disclaimerBox.id = "aboutUsDisclaimerBoxHidden"; 
            await sleep(200);
            document.getElementById("DivDisclaimerBox").removeChild(this.disclaimerBox);
            this.disclaimerBox = null;
        }
        */
    }

}