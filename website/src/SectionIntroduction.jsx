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
                                    <li><i class="fa fa-check-circle checkSign"></i>jo mama</li>
                                    <li><i class="fa fa-check-circle checkSign"></i>jo papa</li>
                                    <li><i class="fa fa-check-circle checkSign"></i>yolo</li>
                                    <li><i class="fa fa-check-circle checkSign"></i>a booming boomer</li>
                                    <li><i class="fa fa-check-circle checkSign"></i>jo mapa</li>
                                </ul>
                            </p>
                            <div className="introductionDownloadArea">
                                <p className="highlight">Download for:</p>
                                <button className="introductionDownloadBtn font_s">Windows</button>
                                <button className="introductionDownloadBtn font_s">Linux</button>
                                <div></div>
                                <button className="introductionDownloadBtn font_s">Mac</button>
                                <p id="introductionAllVersions">see more versions</p>
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
