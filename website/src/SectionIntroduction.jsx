import SectionTemplate from "./SectionTemplate";

export default class SectionIntroduction extends SectionTemplate{
    constructor(bkg, height){
        super(bkg, height);
        this.content = (
            <div>
                <div style={{backgroundColor:this.bkg, height:this.height}}>
                    <p className="family_title font_l">Ollare Ollare this is ma website</p>
                </div>
            </div>
        );
    }
}
//<img src={WavesNavbar} className="imgSvg"/>
