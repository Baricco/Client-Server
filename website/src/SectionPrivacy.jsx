import SectionTemplate from "./SectionTemplate";
import Waves from './data/waves/layeredWaves_RedBlack0.svg'
import './styles/SectionPrivacy.css'
import FAQElement from "./FAQElement";

export default class SectionPrivacy extends SectionTemplate{
    constructor(bkg, height){
        super(bkg, height);
        this.content = (
            <div>
                {wave}
                <div style={{backgroundColor:this.bkg, height:this.height, padding:"100px"}}>
                    <div id = "SectionPrivacyContent">
                        <h3 className="family_title font_l kerning_s FAQ_Title">FAQ</h3>
                        <ul className="FAQ-list">
                            {
                                new FAQElement(
                                    "Can I Trust that my Identity will be hidden?",
                                    "Yes, Your Identity is always hidden, in our app there's no access, no accounts, no password and no risk of been hacked, your indentity, your Ip Address and your location is always hidden from other users and even from us"
                                ).render()
                            }

                            {
                                new FAQElement(
                                    "How can I trust that you can't see all the sent messages?",
                                    ["All the Messages sent with our app are encrypted with end-to-end encryption, it means that the only ones who can access the message are the sender and the recipient, the method by our messages are encrypted is secret and it's been invented by our team, here's an example of an encrypted message:", <br />, "\"This is a Test Message\", encrypted becomes: 횱Od겯1狾f⋜q⫺➿i㗆t᥀\"髜d褦$ykz|鵏)낈w䰣p賯㫅ླྀo꼋{尒vኻ"]
                                ).render()
                            }  
                            
                            {
                                new FAQElement(
                                        "Will you ever release any updates?",
                                        "Yes, we will release updates on this website, whenever an update is released you will have to download the new version to be sure that you always have the most efficient and secure version of the app, if you like it, you could also email us if you have any ideas to improve our up and we could consider to implement them in the next versions"
                                ).render()
                            } 

                            {
                                new FAQElement(
                                        "How can I be sure that my messages can't be tracked?",
                                        "Your messages can't be traced, our server doesn't store any of your personal information, including your messages or your logging details, on top of that, the messaging history will be automatically deleted when you close the app"
                                    ).render()
                            }
                            
                            {
                                new FAQElement(
                                        "Do I have to install this app?",
                                        "No, This application is portable, this means that you just to download it on this website, and double clicking on it, it will open, you can also put the executable in a USB drive to use it on other PCs"
                                    ).render()
                            }   
                                    

                        </ul>
                    </div>
                </div>
            </div>
        );
    }
}

/*
                            <li>
                                <h4 className="family_title font_m kerning_s question">Can I Trust that my Identity is hidden?</h4>
                                <p className="family_text font_m kerning_s">
                                    Yes, Your Identity is always hidden, in our app there's no access, no accounts, no password 
                                    and no risk of been hacked, your indentity, your Ip Address and your location is always hidden
                                    from other users and even from us
                                </p>
                            </li>


*/

var wave = (
    <svg id="sectionPrivacySvg" className="separatorWave" preserveAspectRatio="none" viewBox="0 0 960 540" style={{width:"100%", height:"400px"}} xmlns="http://www.w3.org/2000/svg"
        version="1.1">
        <rect x="0" y="0" width="960" height="540" fill="#c72433"></rect>
        <g>
            <path id="wavePrivacy0_0"
                d="M0 160L20 158.7C40 157.3 80 154.7 120 166.3C160 178 200 204 240 216.8C280 229.7 320 229.3 360 224.8C400 220.3 440 211.7 480 194.3C520 177 560 151 600 159.7C640 168.3 680 211.7 720 212.5C760 213.3 800 171.7 840 160.8C880 150 920 170 940 180L960 190L960 541L940 541C920 541 880 541 840 541C800 541 760 541 720 541C680 541 640 541 600 541C560 541 520 541 480 541C440 541 400 541 360 541C320 541 280 541 240 541C200 541 160 541 120 541C80 541 40 541 20 541L0 541Z"
                fill="#ffffff"></path>
            <path id="wavePrivacy1_0"
                d="M0 310L20 297.2C40 284.3 80 258.7 120 253.5C160 248.3 200 263.7 240 254.8C280 246 320 213 360 195.5C400 178 440 176 480 175.5C520 175 560 176 600 192.8C640 209.7 680 242.3 720 252.2C760 262 800 249 840 245.3C880 241.7 920 247.3 940 250.2L960 253L960 541L940 541C920 541 880 541 840 541C800 541 760 541 720 541C680 541 640 541 600 541C560 541 520 541 480 541C440 541 400 541 360 541C320 541 280 541 240 541C200 541 160 541 120 541C80 541 40 541 20 541L0 541Z"
                fill="#b9bcc2"></path>
            <path id="wavePrivacy2_0"
                d="M0 331L20 324.8C40 318.7 80 306.3 120 310.2C160 314 200 334 240 337.7C280 341.3 320 328.7 360 319.7C400 310.7 440 305.3 480 296.2C520 287 560 274 600 280.3C640 286.7 680 312.3 720 314.2C760 316 800 294 840 284.3C880 274.7 920 277.3 940 278.7L960 280L960 541L940 541C920 541 880 541 840 541C800 541 760 541 720 541C680 541 640 541 600 541C560 541 520 541 480 541C440 541 400 541 360 541C320 541 280 541 240 541C200 541 160 541 120 541C80 541 40 541 20 541L0 541Z"
                fill="#777d88"></path>
            <path id="wavePrivacy3_0"
                d="M0 398L20 395.5C40 393 80 388 120 383.7C160 379.3 200 375.7 240 380.7C280 385.7 320 399.3 360 406.3C400 413.3 440 413.7 480 415.2C520 416.7 560 419.3 600 408.3C640 397.3 680 372.7 720 361.3C760 350 800 352 840 362.8C880 373.7 920 393.3 940 403.2L960 413L960 541L940 541C920 541 880 541 840 541C800 541 760 541 720 541C680 541 640 541 600 541C560 541 520 541 480 541C440 541 400 541 360 541C320 541 280 541 240 541C200 541 160 541 120 541C80 541 40 541 20 541L0 541Z"
                fill="#3a4452"></path>
            <path id="wavePrivacy4_0"
                d="M0 476L20 478.7C40 481.3 80 486.7 120 483.3C160 480 200 468 240 468.2C280 468.3 320 480.7 360 485C400 489.3 440 485.7 480 476.5C520 467.3 560 452.7 600 441.7C640 430.7 680 423.3 720 422.3C760 421.3 800 426.7 840 438.3C880 450 920 468 940 477L960 486L960 541L940 541C920 541 880 541 840 541C800 541 760 541 720 541C680 541 640 541 600 541C560 541 520 541 480 541C440 541 400 541 360 541C320 541 280 541 240 541C200 541 160 541 120 541C80 541 40 541 20 541L0 541Z"
                fill="#001122"></path>
        </g>
        <g visibility="hidden">
            <path id="wavePrivacy0_1"
                d="M0 220L22.8 200.7C45.7 181.3 91.3 142.7 137 140C182.7 137.3 228.3 170.7 274 171.5C319.7 172.3 365.3 140.7 411.2 131.5C457 122.3 503 135.7 548.8 157.3C594.7 179 640.3 209 686 205.2C731.7 201.3 777.3 163.7 823 153.5C868.7 143.3 914.3 160.7 937.2 169.3L960 178L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#ffffff"></path>
            <path id="wavePrivacy1_1"
                d="M0 169L22.8 175.2C45.7 181.3 91.3 193.7 137 214.3C182.7 235 228.3 264 274 281.7C319.7 299.3 365.3 305.7 411.2 292.5C457 279.3 503 246.7 548.8 237.5C594.7 228.3 640.3 242.7 686 238.2C731.7 233.7 777.3 210.3 823 204.3C868.7 198.3 914.3 209.7 937.2 215.3L960 221L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#b9bcc1"></path>
            <path id="wavePrivacy2_1"
                d="M0 344L22.8 338C45.7 332 91.3 320 137 313.2C182.7 306.3 228.3 304.7 274 301.3C319.7 298 365.3 293 411.2 290.5C457 288 503 288 548.8 284C594.7 280 640.3 272 686 272C731.7 272 777.3 280 823 297.5C868.7 315 914.3 342 937.2 355.5L960 369L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#777e87"></path>
            <path id="wavePrivacy3_1"
                d="M0 338L22.8 349.5C45.7 361 91.3 384 137 393.7C182.7 403.3 228.3 399.7 274 395C319.7 390.3 365.3 384.7 411.2 376.5C457 368.3 503 357.7 548.8 352.7C594.7 347.7 640.3 348.3 686 353.7C731.7 359 777.3 369 823 380.7C868.7 392.3 914.3 405.7 937.2 412.3L960 419L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#3a4551"></path>
            <path id="wavePrivacy4_1"
                d="M0 448L22.8 443C45.7 438 91.3 428 137 433.2C182.7 438.3 228.3 458.7 274 466.3C319.7 474 365.3 469 411.2 456.7C457 444.3 503 424.7 548.8 427.5C594.7 430.3 640.3 455.7 686 464.2C731.7 472.7 777.3 464.3 823 459.2C868.7 454 914.3 452 937.2 451L960 450L960 541L937.2 541C914.3 541 868.7 541 823 541C777.3 541 731.7 541 686 541C640.3 541 594.7 541 548.8 541C503 541 457 541 411.2 541C365.3 541 319.7 541 274 541C228.3 541 182.7 541 137 541C91.3 541 45.7 541 22.8 541L0 541Z"
                fill="#001220"></path>
        </g>
    </svg>
);
//<img src={Waves} className="imgSvg" width="100%" height="200px"/>