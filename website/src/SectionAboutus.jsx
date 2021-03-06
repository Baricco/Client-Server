import SectionTemplate from "./SectionTemplate";
import Waves from './data/waves/layeredWaves_RedBlack0.svg';
import './styles/SectionAboutUs.css';
import Card from "./CardProfile";
import CardProfile from "./CardProfile";

export default class SectionAboutus extends SectionTemplate {
    constructor(bkg, height){
        super(bkg, height);

        this.disclaimerBox = null;

        this.content = (
            <div style={{position:"relative"}}>
                {wave}
                <div style={{backgroundColor:this.bkg, height:this.height, padding:"100px", paddingBottom:"0"}}>
                    <div id = "SectionAboutUsContent">
                        <h3 className="family_title font_l kerning_s AboutUs_Title">ABOUT US</h3>
                        <p className="family_text font_s kerning_s AboutUsIntroductionText">
                            We'd like to create a nice community with our costumers, let us introduce ourselves
                        </p>
    
                        <br />
                        <br />

                        <div className = "profileCards">
                            {new CardProfile("Lorenzo Pedrazzi").render()}
                            {new CardProfile("Simone Panini").render()}
                            {new CardProfile("Filippo Barucci").render()}
                        </div>

                        <p className="family_text font_s kerning_s aboutUsText">
                            if you have any questions or ideas to improve our product
                            you can contact us on our email or social
                        </p>

                        <br />
                        <br />

                        <div className="contactLinks">
                            <a href="mailto:hrms.contact@hrms.space">
                                <img src="img/email-logo.png" className="emailImg"/>
                            </a>
                            <a href="https://www.instagram.com/hrms.app/" target="__blank">
                                <img src="img/instagram-logo.png" className="instaImg"/>
                            </a>
                        </div>
                        <div className="Footer" >
                            <pre className = "about-us-copyright">
                                &copy;2021 Hasta la Revolucion Messaging Service
                            </pre>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
var wave = (
    <svg id="sectionAboutUsSvg" className="separatorWave" preserveAspectRatio="none" viewBox="0 0 960 540" style={{width:"100%", height:"400px"}} xmlns="http://www.w3.org/2000/svg"
        version="1.1">
        <rect x="0" y="0" width="960" height="540" fill="#001220"></rect>
        <g>
            <path id="waveAboutUs0_0" 
                d="M0 126L16 148.3C32 170.7 64 215.3 96 217C128 218.7 160 177.3 192 149.2C224 121 256 106 288 104.2C320 102.3 352 113.7 384 129.8C416 146 448 167 480 180.5C512 194 544 200 576 208.8C608 217.7 640 229.3 672 221.3C704 213.3 736 185.7 768 167.7C800 149.7 832 141.3 864 148.8C896 156.3 928 179.7 944 191.3L960 203L960 541L944 541C928 541 896 541 864 541C832 541 800 541 768 541C736 541 704 541 672 541C640 541 608 541 576 541C544 541 512 541 480 541C448 541 416 541 384 541C352 541 320 541 288 541C256 541 224 541 192 541C160 541 128 541 96 541C64 541 32 541 16 541L0 541Z"
                fill="#fa7268"></path>
            <path id="waveAboutUs1_0"
                d="M0 293L16 280.3C32 267.7 64 242.3 96 235.2C128 228 160 239 192 240C224 241 256 232 288 219.3C320 206.7 352 190.3 384 199.2C416 208 448 242 480 245.8C512 249.7 544 223.3 576 209.3C608 195.3 640 193.7 672 190.7C704 187.7 736 183.3 768 202.7C800 222 832 265 864 265C896 265 928 222 944 200.5L960 179L960 541L944 541C928 541 896 541 864 541C832 541 800 541 768 541C736 541 704 541 672 541C640 541 608 541 576 541C544 541 512 541 480 541C448 541 416 541 384 541C352 541 320 541 288 541C256 541 224 541 192 541C160 541 128 541 96 541C64 541 32 541 16 541L0 541Z"
                fill="#ee615a"></path>
            <path id="waveAboutUs2_0"
                d="M0 339L16 335C32 331 64 323 96 311.2C128 299.3 160 283.7 192 289C224 294.3 256 320.7 288 329.5C320 338.3 352 329.7 384 319.8C416 310 448 299 480 299C512 299 544 310 576 304C608 298 640 275 672 273.8C704 272.7 736 293.3 768 303.7C800 314 832 314 864 316.7C896 319.3 928 324.7 944 327.3L960 330L960 541L944 541C928 541 896 541 864 541C832 541 800 541 768 541C736 541 704 541 672 541C640 541 608 541 576 541C544 541 512 541 480 541C448 541 416 541 384 541C352 541 320 541 288 541C256 541 224 541 192 541C160 541 128 541 96 541C64 541 32 541 16 541L0 541Z"
                fill="#e14f4d"></path>
            <path id="waveAboutUs3_0"
                d="M0 345L16 355.8C32 366.7 64 388.3 96 386C128 383.7 160 357.3 192 346.7C224 336 256 341 288 356.8C320 372.7 352 399.3 384 407.2C416 415 448 404 480 390.7C512 377.3 544 361.7 576 363.8C608 366 640 386 672 383.7C704 381.3 736 356.7 768 361C800 365.3 832 398.7 864 408C896 417.3 928 402.7 944 395.3L960 388L960 541L944 541C928 541 896 541 864 541C832 541 800 541 768 541C736 541 704 541 672 541C640 541 608 541 576 541C544 541 512 541 480 541C448 541 416 541 384 541C352 541 320 541 288 541C256 541 224 541 192 541C160 541 128 541 96 541C64 541 32 541 16 541L0 541Z"
                fill="#d43b40"></path>
            <path id="waveAboutUs4_0"
                d="M0 472L16 463C32 454 64 436 96 431.2C128 426.3 160 434.7 192 440C224 445.3 256 447.7 288 448C320 448.3 352 446.7 384 439.3C416 432 448 419 480 423C512 427 544 448 576 462.3C608 476.7 640 484.3 672 477.8C704 471.3 736 450.7 768 436.2C800 421.7 832 413.3 864 413.8C896 414.3 928 423.7 944 428.3L960 433L960 541L944 541C928 541 896 541 864 541C832 541 800 541 768 541C736 541 704 541 672 541C640 541 608 541 576 541C544 541 512 541 480 541C448 541 416 541 384 541C352 541 320 541 288 541C256 541 224 541 192 541C160 541 128 541 96 541C64 541 32 541 16 541L0 541Z"
                fill="#c72433"></path>
        </g>
        <g visibility="hidden">
            <path id="waveAboutUs0_1"
                d="M0 248L22.8 247.3C45.7 246.7 91.3 245.3 137 241.7C182.7 238 228.3 232 274 215.8C319.7 199.7 365.3 173.3 411.2 165C457 156.7 503 166.3 548.8 187.8C594.7 209.3 640.3 242.7 686 246.7C731.7 250.7 777.3 225.3 823 234.8C868.7 244.3 914.3 288.7 937.2 310.8L960 333L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#fa7268"></path>
            <path id="waveAboutUs1_1"
                d="M0 158L22.8 158.2C45.7 158.3 91.3 158.7 137 199.2C182.7 239.7 228.3 320.3 274 323.7C319.7 327 365.3 253 411.2 232.3C457 211.7 503 244.3 548.8 255.7C594.7 267 640.3 257 686 268.8C731.7 280.7 777.3 314.3 823 323.5C868.7 332.7 914.3 317.3 937.2 309.7L960 302L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#ef5f67"></path>
            <path id="waveAboutUs2_1"
                d="M0 343L22.8 339C45.7 335 91.3 327 137 321.8C182.7 316.7 228.3 314.3 274 301.7C319.7 289 365.3 266 411.2 256.7C457 247.3 503 251.7 548.8 281.8C594.7 312 640.3 368 686 376.7C731.7 385.3 777.3 346.7 823 332.7C868.7 318.7 914.3 329.3 937.2 334.7L960 340L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#e34c67"></path>
            <path id="waveAboutUs3_1"
                d="M0 360L22.8 362.7C45.7 365.3 91.3 370.7 137 367C182.7 363.3 228.3 350.7 274 351.8C319.7 353 365.3 368 411.2 377.8C457 387.7 503 392.3 548.8 400.7C594.7 409 640.3 421 686 415.7C731.7 410.3 777.3 387.7 823 392.2C868.7 396.7 914.3 428.3 937.2 444.2L960 460L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#d53867"></path>
            <path id="waveAboutUs4_1"
                d="M0 504L22.8 499.8C45.7 495.7 91.3 487.3 137 485.2C182.7 483 228.3 487 274 484.5C319.7 482 365.3 473 411.2 464.3C457 455.7 503 447.3 548.8 453C594.7 458.7 640.3 478.3 686 488.5C731.7 498.7 777.3 499.3 823 495.8C868.7 492.3 914.3 484.7 937.2 480.8L960 477L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#c62368"></path>
        </g>
    </svg>


);
//<img src={Waves} className="imgSvg" width="100%" height="200px"/>