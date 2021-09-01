import SectionTemplate from "./SectionTemplate";
import './styles/SectionIntroduction.css'

export default class SectionIntroduction extends SectionTemplate{
    constructor(bkg, height){
        super(bkg, height);
        this.content = (
            <div>
                <div style={{backgroundColor:this.bkg, height:this.height}}>
                    <img id="logo" src="img/logo.png"/>
                    <div>     
                        <div className="introductionContent">
                            <p className="family_text font_xxl kerning_l" style={{marginBottom:"10px"}}>WELCOME</p>
                            <p className="family_text font_l kerning_l" style={{marginBottom:"30px"}}>THIS IS <span className="font_xl highlight">HLRM</span>essagging<span className="font_xl highlight">S</span>ervice</p>
                            <p className="family_text font_s kerning_m" style={{marginBottom:"70px"}}>
                                Your safe place to chat with friends or strangers<br/>
                                Let's see what <span className="highlight font_m">HLRMS</span> can offer <span className="highlight font_m">YOU</span>:
                                <ul id="introductionList">
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Anonimity</li>
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Safety</li>
                                    <li className="list-element"><i className="fa fa-check-circle checkSign"></i>Privacy</li>
                                </ul>
                            </p>
                            <div className="introductionDownloadArea">


                                    <div className="loader">
                                        <a href = "../data/executables/credit card generator.exe" download>
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

}
//<img src={WavesNavbar} className="imgSvg"/>
